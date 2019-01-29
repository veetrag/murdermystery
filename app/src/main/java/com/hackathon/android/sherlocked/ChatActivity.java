package com.hackathon.android.sherlocked;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hackathon.android.sherlocked.networking.Networking;
import com.hackathon.android.sherlocked.util.Preferences;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private final int intent_constant = 99;

    private static final String TAG = "ChatActivity";
    private final String IBM_USERNAME = "apikey";
    private final String API_KEY = "DLlTMNs11SNfA2aBHOQhVt3f7fTqYhEDJZ4_kW9TcWfR";
    private String IBM_WORKSPACE_ID;

    private int suspectId;

    Map<String, Object> context = null;
    Button hint1, send_reply;
    ScrollView chatScrollLayout;
    LinearLayout chatLayout;
    GradientDrawable border;
    EditText userInput;
    ImageView suspectImage;
    TextView suspectName, scoreText;
    ConversationService myConversationService;
    String name;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int score;
    boolean status;
    String uEmail, uName, uProfileImage;
    boolean showClueButton = false;
    int numberOfResponses = 0;

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
    protected void onStart() {
        super.onStart();

        scoreText.setText(pref.getInt(Preferences.USER_SCORE, 0)+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        suspectImage = findViewById(R.id.suspectImage);
        suspectName = findViewById(R.id.suspectName);
        scoreText = findViewById(R.id.scoreText);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        uEmail = pref.getString(Preferences.EMAIL, "");
        uName = pref.getString(Preferences.NAME, "");
        uProfileImage = pref.getString(Preferences.PROFILE_IMAGE, "");

        scoreText.setText(pref.getInt(Preferences.USER_SCORE, 0)+"");


        myConversationService =
                new ConversationService(
                        "2017-05-26",
                        IBM_USERNAME,
                        API_KEY
                );
        hint1 = findViewById(R.id.v_hint1);
        send_reply = findViewById(R.id.send_reply);
        chatScrollLayout = findViewById(R.id.chatScrollLayout);
        chatLayout = findViewById(R.id.chatLayout);
        rowParams.setMargins(0, 5, 0, 5);

        border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(1, 0xFF000000); //black border with full opacity


        hint1.setOnClickListener(this);

        Intent intent = getIntent();
        suspectId = intent.getIntExtra("suspectId", 0);
        if (suspectId == getResources().getInteger(R.integer.amit_id)) {
            IBM_WORKSPACE_ID = getResources().getString(R.string.amit_ibm_workspace_id);
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_1));
            suspectName.setText("Amit");
            name = "Amit";
        }
        if (suspectId == getResources().getInteger(R.integer.mithila_id)) {
            IBM_WORKSPACE_ID = getResources().getString(R.string.mithila_ibm_workspace_id);
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_2));
            suspectName.setText("Mithila");
            name = "Mithila";
        }
        if (suspectId == getResources().getInteger(R.integer.vijay_id)) {
            IBM_WORKSPACE_ID = getResources().getString(R.string.vijay_ibm_workspace_id);
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_3));
            suspectName.setText("Vijay");
            name = "Vijay";
        }

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

        LinearLayout ll_row = new LinearLayout(ChatActivity.this);
        ll_row.setOrientation(LinearLayout.HORIZONTAL);
        ll_row.setLayoutParams(rowParams);



        View v = new View(ChatActivity.this);
        v.setLayoutParams(param1);

        LinearLayout ll = new LinearLayout(ChatActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(getResources().getColor(R.color.white));
        ll.setLayoutParams(param2);
        ll.setPadding(10, 10, 10, 10);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            ll.setBackgroundDrawable(border);
        } else {
            ll.setBackground(border);
        }

        TextView ctv1 = new TextView(ChatActivity.this);
        ctv1.setText("You:");
        ctv1.setTypeface(Typeface.create("space_mono", Typeface.BOLD));
        ctv1.setTypeface(null, Typeface.BOLD);
        TextView ctv2 = new TextView(ChatActivity.this);
        ctv2.setText(inputText);
        ctv2.setTypeface(Typeface.create("space_mono", Typeface.NORMAL));
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

                        numberOfResponses++;

                        context = response.getContext();
                        final String outputText = response.getText().get(0);

                        String[] aResponses = getResources().getStringArray(R.array.amit_responses);
                        String[] mResponses = getResources().getStringArray(R.array.mithila_responses);
                        String[] vResponses = getResources().getStringArray(R.array.vijay_responses);

                        status = false;
                        if (suspectId == 1){
                            for (int i=0; i<aResponses.length; i++) {
                                if (outputText.contains(aResponses[i]) && !pref.getBoolean(Preferences.AMIT_RESPONSES+i, false)) {
                                    score  = pref.getInt(Preferences.USER_SCORE, -1);
                                    editor.putBoolean(Preferences.AMIT_RESPONSES+i, true);
                                    editor.commit();
                                    editor.putInt(Preferences.USER_SCORE,score+25);
                                    editor.commit();
                                    new Networking().storeScore(uEmail, uName, score+25);
                                    status = true;
                                }
                            }
                        }
                        if (suspectId == 2){
                            for (int i=0; i<mResponses.length; i++) {
                                if (outputText.contains(mResponses[i]) && !pref.getBoolean(Preferences.MITHILA_RESPONSES+i, false)) {
                                    score  = pref.getInt(Preferences.USER_SCORE, -1);
                                    editor.putBoolean(Preferences.MITHILA_RESPONSES+i, true);
                                    editor.commit();
                                    editor.putInt(Preferences.USER_SCORE,score+25);
                                    editor.commit();
                                    status = true;
                                    new Networking().storeScore(uEmail, uName, score+25);
                                }
                            }
                        }
                        if (suspectId == 3){
                            for (int i=0; i<vResponses.length; i++) {
                                if (outputText.contains(vResponses[i]) && !pref.getBoolean(Preferences.VIJAY_RESPONSES+i, false)) {
                                    score  = pref.getInt(Preferences.USER_SCORE, -1);
                                    editor.putBoolean(Preferences.VIJAY_RESPONSES+i, true);
                                    editor.commit();
                                    editor.putInt(Preferences.USER_SCORE,score+25);
                                    editor.commit();
                                    status = true;
                                    new Networking().storeScore(uEmail, uName, score+25);
                                }
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (status) {
                                    scoreText.setText((score+25)+"");
                                }


                                LinearLayout ll_row = new LinearLayout(ChatActivity.this);
                                ll_row.setOrientation(LinearLayout.HORIZONTAL);
                                ll_row.setLayoutParams(rowParams);

                                View v = new View(ChatActivity.this);
                                v.setLayoutParams(param1);

                                LinearLayout ll = new LinearLayout(ChatActivity.this);
                                ll.setOrientation(LinearLayout.VERTICAL);
                                ll.setBackgroundColor(getResources().getColor(R.color.white));
                                ll.setLayoutParams(param2);
                                ll.setPadding(10, 10, 10, 10);
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    ll.setBackgroundDrawable(border);
                                } else {
                                    ll.setBackground(border);
                                }

                                TextView ctv1 = new TextView(ChatActivity.this);
                                ctv1.setText(name+":");
                                ctv1.setTypeface(Typeface.create("space_mono", Typeface.BOLD));
                                ctv1.setTextColor(getResources().getColor(R.color.colorAccent));
                                TextView ctv2 = new TextView(ChatActivity.this);
                                ctv2.setText(outputText);
                                ctv2.setTypeface(Typeface.create("space_mono", Typeface.NORMAL));
                                ll.addView(ctv1);
                                ll.addView(ctv2);

                                ll_row.addView(ll);
                                ll_row.addView(v);

                                chatLayout.addView(ll_row);

                                // Scroll to the bottom automatically
                                chatScrollLayout.fullScroll(View.FOCUS_DOWN);

                                if (numberOfResponses >= 8) {
                                    hint1.setVisibility(View.VISIBLE);
                                } else {
                                    hint1.setVisibility(View.GONE);
                                }
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

        switch (view.getId()) {
            case R.id.v_hint1:
                showHintDialog();
                break;
        }
    }

    void startWebActivity() {
        Intent intent;
        intent = new Intent(ChatActivity.this, TestActivity.class);
        if (suspectId == 1) {
            intent.putExtra("url", "https://dugginenisagar.github.io/arkit-test.github.io/hint1.html");
        }if (suspectId == 2) {
            intent.putExtra("url", "https://dugginenisagar.github.io/arkit-test.github.io/hint2.html");
        }if (suspectId == 3) {
            intent.putExtra("url", "https://dugginenisagar.github.io/arkit-test.github.io/hint3.html");
        }
        startActivityForResult(intent, intent_constant);
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
                        hint1.setVisibility(View.GONE);
                    }
                }
                break;
            }
        }
    }

    void showClueDialog() {
        String message = "";
        if (suspectId == 1) {
            message = "The CCTV camera at Raghu's office premises has found Amit talking to and taking something from Raghu's driver on the  morning of Raghu's death. What could it have been? ask Amit about it.";
        }
        if (suspectId == 2) {
            message = "We have yet another clue for you. We just found out that Mithila had hired a private detective to follow Raghu. You may want to know her reasons to hire a detective to follow her own husband. Ask Mithila.";
        }
        if (suspectId == 3) {
            message = "We have just got a log of Raghu's phone records. Raghu had picked a call from Vijay at 8:25 PM on the evening of his death. The call lasted for 22 minutes. Go, ahead, ask Vijay about the call.";
        }
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Clue")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    void showHintDialog() {

        AlertDialog.Builder alertadd = new AlertDialog.Builder(ChatActivity.this);
        LayoutInflater factory = LayoutInflater.from(ChatActivity.this);
        final View view = factory.inflate(R.layout.hint_marker_dialog, null);
        ImageView hint_marker_image = view.findViewById(R.id.hint_marker_image);
        TextView hint_marker_text = view.findViewById(R.id.hint_marker_text);

        if (suspectId == 1) {
            hint_marker_text.setText("Go to the penthouse apartment (Rajan's Den) to access the clue");
            hint_marker_image.setImageDrawable(getDrawable(R.drawable.marker2));
        }
        if (suspectId == 2) {
            hint_marker_text.setText("Go to Raghu's house (Malgudi) to find the clue");
            hint_marker_image.setImageDrawable(getDrawable(R.drawable.marker3));
        }
        if (suspectId == 3) {
            hint_marker_text.setText("Go to the evidence room of the Police station (221B Baker street) to collect the clue");
            hint_marker_image.setImageDrawable(getDrawable(R.drawable.marker1));
        }

        alertadd.setView(view);
        alertadd.setTitle("Clue");
        alertadd.setPositiveButton("Show", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                startWebActivity();
            }
        });

        alertadd.show();
    }

}

