package dev.mobile.bai5;

public class RecyclerData {
    private String title;
    private String mail;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public RecyclerData(String title) {
        this.title = title;
    }

}
