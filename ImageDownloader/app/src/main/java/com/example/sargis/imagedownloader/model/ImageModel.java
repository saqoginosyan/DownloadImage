package com.example.sargis.imagedownloader.model;

public class ImageModel {
    private String imageTitle;
    private String imageUrl;
    private boolean downloaded;

    public ImageModel(String imageTitle, String imageUrl, boolean downloaded) {
        this.imageTitle = imageTitle;
        this.imageUrl = imageUrl;
        this.downloaded = downloaded;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
