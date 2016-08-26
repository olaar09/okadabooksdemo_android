package co.okadabooksdemo.object;

/**
 * Created by olaar on 8/23/16.
 */
public class User {
    public String getuser_firstname() {
        return user_firstname;
    }

    public void setuser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getuser_lastname() {
        return user_lastname;
    }

    public void setuser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public User(String user_firstname, String id, String user_lastname) {

        this.user_firstname = user_firstname;
        this.id = id;
        this.user_lastname = user_lastname;
    }

    public User() {

    }

    public String user_firstname,user_lastname;
    public String id;
}
