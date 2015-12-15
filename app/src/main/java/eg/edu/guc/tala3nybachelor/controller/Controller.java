package eg.edu.guc.tala3nybachelor.controller;

import java.util.ArrayList;


import eg.edu.guc.tala3nybachelor.model.FollowerResponse;
import eg.edu.guc.tala3nybachelor.model.LoginData;
import eg.edu.guc.tala3nybachelor.model.Post;
import eg.edu.guc.tala3nybachelor.model.Session;
import eg.edu.guc.tala3nybachelor.model.SetData;
import eg.edu.guc.tala3nybachelor.model.User;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by salmaali on 12/15/15.
 */
public class Controller {

    public interface getUser {
        @GET("/api/users/{id}")
        void get_user(@Header("Authorization")String user, @Path("id") Integer id, Callback <User> callback);
    }

    public interface Login {
        @POST("/api/sessions/")
        void login(@Body LoginData user, Callback<Session> callback);
    }

    public interface getPost {
        @GET("/api/posts/{id}")
        void get_post(@Path("id") Integer id, Callback <Post> callback);
    }

//    public interface  addPost {
//
//        @POST("/api/posts")
//        void add_post(SetData data, )
//
//    }

    public interface addComment {

        @POST("/api/comments")
        void add_comment(@Header("Authorization") String token, @Body SetData data, Callback<Post> callback);

    }

    public interface addPost {

        @POST("/api/posts")
        void add_post(@Header("Authorization") String token, @Body SetData data, Callback<Post> callback);

    }

    public interface getProfilePosts {

        @GET("/api/posts")
        void get_profile(@Query("destination") Integer destination, Callback<ArrayList<Post>> callback);

    }
    public interface getPosts {
        @GET("/api/posts/")
        void get_posts(Callback <ArrayList<Post>> callback);
    }

    public interface  getFollowings {
        @GET("/api/followings/")
        void get_followings(@Header("Authorization")String user, Callback<ArrayList<FollowerResponse>> callback);
    }
}
