package com.example.cqupt.pleasantsafe;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cqupt.pleasantsafe.utils.PackageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "wenming";
    private String url = "http://182.254.212.207:8080/update.json";
    private static final String savePath = "mnt/sdcard/pleasantsafe.apk";
    private TextView mVersion;
    private String mVersionName;
    private String mDownloadUrl;
    private String mVersionDes;
    private int mVersionCode;
    private Uri fileUri;

    private String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
    List<String> mDeniedPermissList = new ArrayList<>();
    private ProgressDialog downloadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initview();

    }

    private void verifyPermissions() {

        mDeniedPermissList.clear();
        for (String permisson : permissions) {

            if (ContextCompat.checkSelfPermission(this, permisson) != PackageManager.PERMISSION_GRANTED) {
                mDeniedPermissList.add(permisson);
            }
        }

        if (mDeniedPermissList.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            }, 1000);
        } else {
            ActivityCompat.requestPermissions(this, mDeniedPermissList.toArray(new String[mDeniedPermissList.size()]), 1);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:

                for (int i = 0; i < grantResults.length; i++) {

                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "权限获取失败，应用无法启用", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                }, 1000);

        }
    }

    private void initview() {

        mVersion = (TextView) findViewById(R.id.splash_tv_version);
        mVersion.setText("版本：" + PackageUtil.getVersionName(this));
        verifyPermissions();
    }

    private void update() {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    enterHome();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    Log.e(TAG, Thread.currentThread().getName());
                    processJson(response.body().string());
                }
            });


        } catch (Exception e) {


        }


    }

    private void processJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            mVersionCode = jsonObject.getInt("versionCode");
            mVersionName = jsonObject.getString("versionName");
            mDownloadUrl = jsonObject.getString("downloadUrl");
            mVersionDes = jsonObject.getString("versionDes");

            if (mVersionCode == PackageUtil.getVersionCode(this)) {
                enterHome();
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showUpdateDialog();
                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showUpdateDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更新提示");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(mVersionDes);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

                dialogInterface.dismiss();
                enterHome();
            }
        });

        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();
                downloadAPK();


            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();

            }
        });

        builder.show();

    }

    private void downloadAPK() {
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();

        showProgressDialog();


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(mDownloadUrl).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        downloadDialog.dismiss();
                        enterHome();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        InputStream is = null;
                        FileOutputStream fos = null;
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        int progress = 0;
                        downloadDialog.setMax((int) total);

                        if (is != null) {

                            File file = new File(savePath);
                            fos = new FileOutputStream(file);
                            byte[] buf = new byte[2048];
                            int len = -1;
                            while ((len = is.read(buf)) > 0) {
                                fos.write(buf, 0, len);
                                progress += len;
                                downloadDialog.setProgress(progress);
                            }
                        }

                        fos.flush();
                        if (fos != null) {
                            fos.close();
                        }
                        downloadDialog.dismiss();
                        installApk();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void installApk() {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File file = new File(savePath);
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(this, "com.example.cqupt.pleasantsafe.fileprovider", file);
        } else {

            fileUri = Uri.fromFile(file);
        }

        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void showProgressDialog() {

        downloadDialog = new ProgressDialog(this);
        downloadDialog.setCancelable(false);
        downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downloadDialog.show();


    }

    private void enterHome() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }


}
