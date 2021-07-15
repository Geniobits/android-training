package com.geniobits.firebaseauthdemo2.Home.Model;

public class image {
    public String id;
    public String download_url;

    public image(){}

    public image(String id, String download_url) {
        this.id = id;
        this.download_url = download_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
