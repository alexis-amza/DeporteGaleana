package com.amza.deportegaleana.models;

import android.net.Uri;

public class UsuariosFacebook {

    private String name;
    private String email;
    private Uri photoUrl;
    private String uid;

    public UsuariosFacebook() {
    }

    public UsuariosFacebook(String name, String email, Uri photoUrl, String uid) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
