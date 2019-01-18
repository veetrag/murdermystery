package com.javapapers.android.aichatbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.Map;

public class ChatBotVijayActivity extends AppCompatActivity {

    private static final String TAG = "ChatBotVijayActivity";
    private final String IBM_USERNAME = "apikey";
    private final String API_KEY = "DLlTMNs11SNfA2aBHOQhVt3f7fTqYhEDJZ4_kW9TcWfR";
    private final String IBM_WORKSPACE_ID = "2e78a876-6e33-4996-9b2b-9638c39f1eac";

    Map<String, Object> context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_vijay);

        final ConversationService myConversationService =
                new ConversationService(
                        "2017-05-26",
                        IBM_USERNAME,
                        API_KEY
                );

        final TextView conversation = (TextView)findViewById(R.id.conversation);

        final EditText userInput = (EditText)findViewById(R.id.user_input);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE) {

                    final String inputText = userInput.getText().toString();
                    conversation.append(
                            Html.fromHtml("<p><b>You:</b> " + inputText + "</p>")
                    );

                    // Clear edittext
                    userInput.setText("");


                    MessageRequest request = new MessageRequest.Builder()
                            .inputText(inputText).context(context)
                            .build();

                    myConversationService
                            .message(IBM_WORKSPACE_ID, request)
                            .enqueue(new ServiceCallback<MessageResponse>() {
                                @Override
                                public void onResponse(MessageResponse response) {

                                    context = response.getContext();

                                 //   Log.i("ChatBot Test",response.getText().get(0));

                                    final String outputText = response.getText().get(0);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            conversation.append(
                                                    Html.fromHtml("<p><b>Bot:</b> " +
                                                            outputText + "</p>")
                                            );
                                        }
                                    });


                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d(TAG, e.getMessage());
                                }
                            });
                }
                return false;
            }
        });
    }

}
