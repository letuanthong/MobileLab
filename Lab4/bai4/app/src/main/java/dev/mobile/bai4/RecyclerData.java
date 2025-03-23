package dev.mobile.bai4;

public class RecyclerData {
    private String title;
    private Boolean on;
    private int imgid;

    public RecyclerData(String title, int imgid, Boolean on) {
        this.title = title;
        this.imgid = imgid;
        this.on = on;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getImgid() {
        return this.imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

}
