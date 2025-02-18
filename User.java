import java.util.ArrayList;

public class User {
    public String userID;
    public MyHashSet<User> followerSet=new MyHashSet<>();  //user's following set
    public ArrayList<User> followerList=new ArrayList<>();  //user's following arrayList
    public MyHashSet<Post> likedPosts=new MyHashSet<>();  //user's liked posts set
    public MyHashSet<Post> seenPosts=new MyHashSet<>();  //user's seen posts set
    public ArrayList<Post> userPosts=new ArrayList<>();  //user's posts arrayList


    public User(String userID){
        this.userID=userID;

    }


}
