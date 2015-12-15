package eg.edu.guc.tala3nybachelor.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TarekElBeih on 28/11/15.
 */

public class Post {
    private String body;
    private int timestamp;
    private int commentCount;
    private int likes;
    private String created_at;
    private String updated_at;
    private ArrayList<Comment> comments;

    public Post(String body, String created_at, int comments, int likes) {

        this.body = body;
        this.created_at = created_at;
        this.commentCount = comments;
        this.likes = likes;

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss z");

        try {
            Date now = new Date();
            String strDate = simpleDateFormat.format(now);
            Date date1 = simpleDateFormat.parse(strDate);
            Date date2 = simpleDateFormat.parse(created_at);

            int secondsInMilli = 1000;
            int minutesInMilli = secondsInMilli * 60;
            int different = (int)(date2.getTime() - date1.getTime());
            timestamp = different / minutesInMilli;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        return body;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getComments() {
        return commentCount;
    }

    public int getLikes() {
        return likes;
    }
}
