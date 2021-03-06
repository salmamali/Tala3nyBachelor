package eg.edu.guc.tala3nybachelor.model;

import java.util.ArrayList;

/**
 * Created by salmaali on 11/28/15.
 */
public class User {

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;
    private String name;
    private String gender;
    private String id;
    private ArrayList<Post> posts;
    private String city;
    private String country;
    private String avatar;
    private String date_of_birth;
    private String email;

    public String getAvatar() {
        return avatar;
    }


    public ArrayList<Post> getPosts() {
        return posts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return country;
    }
}
