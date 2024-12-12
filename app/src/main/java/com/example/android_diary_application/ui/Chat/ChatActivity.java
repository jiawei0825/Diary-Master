package com.example.android_diary_application.ui.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_diary_application.R;
import com.example.android_diary_application.adapter.MessageAdapter;
import com.example.android_diary_application.ui.HomePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    Button back;
    List<Message> messageList;
    MessageAdapter messageadapter;

    public static final String API_KEY = "ipeK4SYlCpNen8xc5iHSOL4m";
    public static final String SECRET_KEY = "gkmMdNndpwlTEKnTgsEoQo2irhLstu4N";

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS) // Connect time out
            .readTimeout(3000, TimeUnit.SECONDS)    // Read time out
            .writeTimeout(3000, TimeUnit.SECONDS)   // Write time out
            .build();

    //static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstancesState){

        super.onCreate(savedInstancesState);
        setContentView(R.layout.chat_main);

        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        back = findViewById(R.id.back_button);

        //setup recycle view
        messageadapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageadapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->{
            String text = messageEditText.getText().toString().trim();
            addToChat(text,Message.SENT_BY_ME);
            messageEditText.setText("");
            //callGPTAPI(question);

            String question =String.format( "{\"messages\": [\n" +
                    "   {\"role\": \"user\",\"content\":\"%s\"}\n" +
                    "   ]}",text);

            callWXAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageadapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageadapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    // Use CompletableFuture to acquire Access Token asynchronously
    private CompletableFuture<String> getAccessTokenAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try (Response response = HTTP_CLIENT.newCall(new Request.Builder()
                    .url("https://aip.baidubce.com/oauth/2.0/token")
                    .method("POST", RequestBody.create(
                            MediaType.parse("application/x-www-form-urlencoded"),
                            "grant_type=client_credentials&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY))
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build()).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                JSONObject jsonObject = new JSONObject(response.body().string());
                return jsonObject.getString("access_token");
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void callWXAPI(String question) {
        // Show typing...
        addToChat("Typing...", Message.SENT_BY_BOT);

        // Require Access Token asynchronously
        getAccessTokenAsync().thenAccept(accessToken -> {
            // Use fetch Access Token to call WX API
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, question);
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + accessToken)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            HTTP_CLIENT.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    addResponse("Failed to load response due to " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    String responseBody = response.body().string();
                    // Print the full response for debugging

                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            // Check if "result" exists
                            if (jsonObject.has("result")) {
                                //JSONArray jsonArray = jsonObject.getJSONArray("result");
                                String result = jsonObject.getString("result");
                                addResponse(result.trim());
                            } else {
                                // Handle missing "result"
                                addResponse("Response does not contain 'result' field.");
                            }
                        } catch (JSONException e) {
                            // Catch JSON parsing exceptions and print error messages
                            System.err.println("JSON parsing error: " + e.getMessage());
                            addResponse("Failed to parse response: " + e.getMessage());
                        }
                    } else {
                        addResponse("Failed to load response due to " + response.message());
                    }
                }
            });
        }).exceptionally(ex -> {
            // Handle the failure of acquiring Access Token
            addResponse("Failed to get access token: " + ex.getMessage());
            return null;
        });
    }
}
