package com.example.sargis.imagedownloader.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sargis.imagedownloader.provider.ImageProvider;
import com.example.sargis.imagedownloader.R;
import com.example.sargis.imagedownloader.adapter.ImageAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final int CODE = 1001;
    private Button downloadButton;
    private TextView urlText;
    private ProgressBar loadingProgress;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.set_url_txt);
        downloadButton = findViewById(R.id.download_button);
        loadingProgress = findViewById(R.id.loading_progress);
        loadingText = findViewById(R.id.loading_txt);
        permission();
        initialisation(urlText);
    }

    private void initialisation(TextView textView) {
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ImageAdapter imgAdapter = new ImageAdapter(this, ImageProvider.getImagesList(this), textView);
        RecyclerView recyclerView = findViewById(R.id.image_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imgAdapter);
    }

    private void imageDownloader(final Button button, final TextView textView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String path = Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                loadingProgress.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            httpLoader(textView, path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            loadingProgress.setVisibility(View.GONE);
                            loadingText.setVisibility(View.GONE);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingProgress.setVisibility(View.GONE);
                                loadingText.setVisibility(View.GONE);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void httpLoader(TextView textView, String path) throws IOException {
        InputStream inputStream;
        String stringUrl = textView.getText().toString();
        URL url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        inputStream = connection.getInputStream();
        final Bitmap bitmapImage = BitmapFactory.decodeStream(inputStream);
        File file = new File(path + "/" +
                ImageProvider.getImagesByPosition().getImageTitle() + ".jpg");
        ImageProvider.getImagesByPosition().setDownloaded(true);
        FileOutputStream f = new FileOutputStream(file);
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, f);
        byte[] buffer = new byte[1024];
        int n;
        while (-1 != (n = inputStream.read(buffer.clone()))) {
            f.write(buffer, 0, n);
        }
        f.close();
        inputStream.close();
    }

    private void permission() {
        ActivityCompat.requestPermissions(
                this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageDownloader(downloadButton, urlText);
                } else {
                    permission();
                }
        }
    }
}
