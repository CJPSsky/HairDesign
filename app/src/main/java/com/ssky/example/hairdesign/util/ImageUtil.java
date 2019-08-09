package com.ssky.example.hairdesign.util;

public class ImageUtil {
    private String imageIntro;
    private int imgId;

    public ImageUtil(String imageIntro, int imgId) {
        this.imageIntro = imageIntro;
        this.imgId = imgId;
    }

    public String getImageIntro() {
        return imageIntro;
    }

    public void setImageIntro(String imageIntro) {
        this.imageIntro = imageIntro;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
