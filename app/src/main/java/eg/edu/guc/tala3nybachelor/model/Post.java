package eg.edu.guc.tala3nybachelor.model;

import java.util.ArrayList;

/**
 * Created by TarekElBeih on 28/11/15.
 */

public class Post {
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String body;
    private int timestamp;
    private int commentCount;
    private int likes;
    private String created_at;
    private String updated_at;
    private ArrayList<Comment> comments;

    public Post(String body, int timestamp, int comments, int likes) {
        this.body = body;
        this.timestamp = timestamp;
        this.commentCount = comments;
        this.likes = likes;
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

    public ArrayList<Comment> getCommentsArray() {
        return comments;
    }



    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
