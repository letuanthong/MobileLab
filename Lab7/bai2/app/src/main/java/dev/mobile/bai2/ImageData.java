package dev.mobile.bai2;

public class ImageData {
    private long id;
    private String path;
    private boolean isVideo;
    private String mimeType;
    private long dateAdded;
    private String duration;

    public ImageData(long id, String path, boolean isVideo, String mimeType, long dateAdded, String duration) {
        this.id = id;
        this.path = path;
        this.isVideo = isVideo;
        this.mimeType = mimeType;
        this.dateAdded = dateAdded;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getDateAdded() {
        return dateAdded;
    }



    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", isVideo=" + isVideo +
                ", mimeType='" + mimeType + '\'' +
                ", dateAdded=" + dateAdded +
                ", duration='" + duration + '\'' +
                '}';
    }
}
