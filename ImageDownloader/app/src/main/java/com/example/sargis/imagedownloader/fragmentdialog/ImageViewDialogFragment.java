package com.example.sargis.imagedownloader.fragmentdialog;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sargis.imagedownloader.R;
import com.example.sargis.imagedownloader.provider.ImageProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageViewDialogFragment extends DialogFragment {
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_df, container, false);
        image = view.findViewById(R.id.image_fragment);
        downloadImage();
        return view;
    }

    private void downloadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String path = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    File file = new File(path + "/" +
                            ImageProvider.getImagesByPosition().getImageTitle() + ".jpg");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    final Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    fileInputStream.close();
                    image.post(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
