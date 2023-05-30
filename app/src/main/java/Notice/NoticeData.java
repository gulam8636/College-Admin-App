package Notice;

public class NoticeData {
    String image;
    String date;
    String time;
    String uniqueKey;

    public NoticeData() {
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public NoticeData(String title,String image, String date, String time,String uniqueKey) {
        this.image = image;
        this.date = date;
        this.time = time;
        this.uniqueKey=uniqueKey;
        this.title=title;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
