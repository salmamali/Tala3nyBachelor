package eg.edu.guc.tala3nybachelor.model;

/**
 * Created by salmaali on 12/3/15.
 */
public class Friend {

    private String friend;
    private boolean accepted;
    private String imageUrl;

    public Friend(boolean accepted, String friend) {
        this.accepted = accepted;
        this.friend = friend;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getFriend() {
        return friend;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
