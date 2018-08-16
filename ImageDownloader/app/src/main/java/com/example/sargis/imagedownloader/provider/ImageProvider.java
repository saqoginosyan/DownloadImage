package com.example.sargis.imagedownloader.provider;

import android.content.Context;

import com.example.sargis.imagedownloader.R;
import com.example.sargis.imagedownloader.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class ImageProvider {
    private static List<ImageModel> imagesList = new ArrayList<>();
    public static int position;

    public static List<ImageModel> getImagesList(Context context) {

        if (!imagesList.isEmpty()) {
            imagesList.clear();
        }
        for (int i = 0; i < context.getResources().getStringArray(R.array.imageUrl).length; i++) {
            imagesList.add(new ImageModel(context.getResources().getStringArray(R.array.imageTitle)[i],
                    context.getResources().getStringArray(R.array.imageUrl)[i], false));
        }
        return imagesList;
    }

    public static ImageModel getImagesByPosition() {
        return imagesList.get(position);
    }
}
