package com.soogreyhounds.soogreyhoundsmobile;

public class Photo {
    private int id;
    private String mPerson;
    public String getPerson() {
        return mPerson;
    }
    public void setPerson(String person) {
        mPerson = person;
    }
    public String getPhotoFilename() {
        return "IMG_" + getUuid() + ".jpg";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String uuid;
    private String title;
    private String url;
    private String note;
}
