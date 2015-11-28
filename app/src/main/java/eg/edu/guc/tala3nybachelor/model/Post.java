package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by TarekElBeih on 28/11/15.
 */

public class Post {
    private String body;
    private int timestamp;
    private int comments;
    private int likes;

    public Post(String body, int timestamp, int comments, int likes) {
        this.body = body;
        this.timestamp = timestamp;
        this.comments = comments;
        this.likes = likes;
    }

    public String getBody() {
        return body;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }
}
