package eg.edu.guc.tala3nybachelor.controller;

import eg.edu.guc.tala3nybachelor.model.Post;
import eg.edu.guc.tala3nybachelor.model.SetData;
import eg.edu.guc.tala3nybachelor.model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by salmaali on 12/15/15.
 */
public class Controller {

    public interface getUser {

        @GET("/api/users/{id}")
        void get_user(@Path("id") Integer id, Callback <User> callback);
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



    }
}
