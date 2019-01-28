package com.hackathon.android.sherlocked;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class TestActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks{

    public static String ON_COLLECT = "ON_COLLECT";

    WebView myWebView;
    Button collectObject;

    private String TAG = "TEST";
    private PermissionRequest mPermissionRequest;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String[] PERM_CAMERA =
            {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);


        collectObject = new Button(this);

        myWebView  = new WebView(this);
//        myWebView  = findViewById(R.id.ar_view);

        WebSettings webSettings = myWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        // Add the interface to record javascript events
        myWebView.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void performClick()
            {
                Log.i("Logging", "Button clicked");
                Intent resultIntent = new Intent();
                resultIntent.putExtra(ON_COLLECT, true);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        }, "collect");


        myWebView.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.i(TAG, "onPermissionRequest");
                mPermissionRequest = request;
                final String[] requestedResources = request.getResources();
                for (String r : requestedResources) {
                    if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        // In this sample, we only accept video capture request.
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this)
                                .setTitle("Allow Permission to camera")
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mPermissionRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                                        Log.d(TAG,"Granted");
                                    }
                                })
                                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        mPermissionRequest.deny();
                                        Log.d(TAG,"Denied");
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        break;
                    }
                }
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
                Toast.makeText(TestActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if(hasCameraPermission()){
            myWebView.loadUrl(url);
            setContentView(myWebView );
        }else{
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your camera so you can take pictures.",
                    REQUEST_CAMERA_PERMISSION,
                    PERM_CAMERA);
        }


    }





    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(TestActivity.this, PERM_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}