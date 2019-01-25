package com.javapapers.android.aichatbot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.Map;

public class ChatBotVijayActivity extends AppCompatActivity implements View.OnClickListener {

    private final int intent_constant = 99;

    private static final String TAG = "ChatBotVijayActivity";
    private final String IBM_USERNAME = "apikey";
    private final String API_KEY = "DLlTMNs11SNfA2aBHOQhVt3f7fTqYhEDJZ4_kW9TcWfR";
    private final String IBM_WORKSPACE_ID = "2e78a876-6e33-4996-9b2b-9638c39f1eac";

    Map<String, Object> context = null;
    Button hint1, hint2, send_reply;
    ScrollView chatScrollLayout;
    LinearLayout chatLayout;
    GradientDrawable border;
    EditText userInput;
    ConversationService myConversationService;

    LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            0.3f
    );
    LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            0.7f
    );

    LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_vijay);

        myConversationService =
                new ConversationService(
                        "2017-05-26",
                        IBM_USERNAME,
                        API_KEY
                );
        hint1 = findViewById(R.id.v_hint1);
        hint2 = findViewById(R.id.v_hint2);
        send_reply = findViewById(R.id.send_reply);
        chatScrollLayout = findViewById(R.id.chatScrollLayout);
        chatLayout = findViewById(R.id.chatLayout);
        rowParams.setMargins(0, 5, 0, 5);

        border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(1, 0xFF000000); //black border with full opacity


        hint1.setOnClickListener(this);

//        final TextView conversation = (TextView)findViewById(R.id.conversation);

        userInput = (EditText)findViewById(R.id.user_input);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE) {
                    onReplySend();
                }
                return false;
            }
        });
        send_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReplySend();
            }
        });
    }


    void onReplySend() {
        final String inputText = userInput.getText().toString();

        LinearLayout ll_row = new LinearLayout(ChatBotVijayActivity.this);
        ll_row.setOrientation(LinearLayout.HORIZONTAL);
        ll_row.setLayoutParams(rowParams);



        View v = new View(ChatBotVijayActivity.this);
        v.setLayoutParams(param1);

        LinearLayout ll = new LinearLayout(ChatBotVijayActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(getResources().getColor(R.color.white));
        ll.setLayoutParams(param2);
        ll.setPadding(10, 10, 10, 10);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            ll.setBackgroundDrawable(border);
        } else {
            ll.setBackground(border);
        }

        TextView ctv1 = new TextView(ChatBotVijayActivity.this);
        ctv1.setText("You:");
        ctv1.setTypeface(null, Typeface.BOLD);
        TextView ctv2 = new TextView(ChatBotVijayActivity.this);
        ctv2.setText(inputText);
        ll.addView(ctv1);
        ll.addView(ctv2);

        ll_row.addView(v);
        ll_row.addView(ll);

        chatLayout.addView(ll_row);

        // Scroll to the bottom automatically
        chatScrollLayout.fullScroll(View.FOCUS_DOWN);

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

                                LinearLayout ll_row = new LinearLayout(ChatBotVijayActivity.this);
                                ll_row.setOrientation(LinearLayout.HORIZONTAL);
                                ll_row.setLayoutParams(rowParams);

                                View v = new View(ChatBotVijayActivity.this);
                                v.setLayoutParams(param1);

                                LinearLayout ll = new LinearLayout(ChatBotVijayActivity.this);
                                ll.setOrientation(LinearLayout.VERTICAL);
                                ll.setBackgroundColor(getResources().getColor(R.color.white));
                                ll.setLayoutParams(param2);
                                ll.setPadding(10, 10, 10, 10);
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    ll.setBackgroundDrawable(border);
                                } else {
                                    ll.setBackground(border);
                                }

                                TextView ctv1 = new TextView(ChatBotVijayActivity.this);
                                ctv1.setText("Bot:");
                                ctv1.setTypeface(null, Typeface.BOLD);
                                ctv1.setTextColor(getResources().getColor(R.color.colorAccent));
                                TextView ctv2 = new TextView(ChatBotVijayActivity.this);
                                ctv2.setText(outputText);
                                ll.addView(ctv1);
                                ll.addView(ctv2);

                                ll_row.addView(ll);
                                ll_row.addView(v);

                                chatLayout.addView(ll_row);

                                // Scroll to the bottom automatically
                                chatScrollLayout.fullScroll(View.FOCUS_DOWN);
                            }
                        });


                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                });
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.v_hint1:
                intent = new Intent(ChatBotVijayActivity.this, TestActivity.class);
                startActivityForResult(intent, intent_constant);
                break;
            case R.id.v_hint2:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (intent_constant) : {
                if (resultCode == Activity.RESULT_OK) {
                    Boolean collected = data.getBooleanExtra(TestActivity.ON_COLLECT, false);
                    if (collected) {
                        showClueDialog();
                    }
                }
                break;
            }
        }
    }

    void showClueDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Clue")
                .setMessage("Here is a small clue for you to continue...")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

