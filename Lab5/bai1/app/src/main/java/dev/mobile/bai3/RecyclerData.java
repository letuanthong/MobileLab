package dev.mobile.bai3;

public class RecyclerData {
    private String title;
    private boolean checked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RecyclerData(String title, boolean checked) {
        this.title = title;
        this.checked = checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }
    public boolean getChecked() {

        return checked;
    }
}
