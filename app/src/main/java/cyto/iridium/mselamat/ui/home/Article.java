package cyto.iridium.mselamat.ui.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    private Date convertedDate;

    public Article(String title, String description, String url, String urlToImage, String publishedAt, String content) throws ParseException {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.convertedDate = convertDate(publishedAt);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate(){
        return convertDate2(this.convertedDate);
    }

    public Date convertDate(String publishedAt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.parse(publishedAt);
    }

    public String convertDate2(Date convertedDate){
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        return sdf2.format(convertedDate);
    }
}
