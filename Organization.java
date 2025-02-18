import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Organization {
    public static MyHashMap<String, User> allUser=new MyHashMap<>();
    public static MyHashMap<String, Post> allPost=new MyHashMap<>();

    public static String createUser(String userId){
        StringBuilder output=new StringBuilder(); //to design result
        if(!allUser.containsKey(userId)){ //check if user already exists or not
            User newUser=new User(userId);
            allUser.put(userId, newUser);
            output.append("Created user with Id ").append(userId).append(".");
        }
        else {
            output.append("Some error occurred in create_user.");
        }

        return output.toString(); //return string result
    }

    public static String followUser(String userId1, String userId2){
        StringBuilder output=new StringBuilder(); //to design result
        if(!allUser.containsKey(userId1)  || !allUser.containsKey(userId2)){ //check if user! and user2 exists or not
            output.append("Some error occurred in follow_user.");
            return output.toString();
        }
        if(Objects.equals(userId1, userId2)){
            output.append("Some error occurred in follow_user."); //to avoid from that the user follows itself
            return output.toString();
        }

        User user=allUser.get(userId1); //get user1 from allUser hashmap
        User follow=allUser.get(userId2); //get user2 from allUser hashmap

        if(!user.followerSet.contains(follow)){ //check if user1 already follows user2
            user.followerSet.add(follow);
            user.followerList.add(follow);
            output.append(userId1).append(" followed ").append(userId2).append(".");
            return output.toString();
        }
        else {
            output.append("Some error occurred in follow_user.");
            return output.toString();
        }

    }

    public static String unfollowUser(String userId1, String userId2){
        StringBuilder output=new StringBuilder(); //to design result
        if(!allUser.containsKey(userId1)  || !allUser.containsKey(userId2)){ //check if user! and user2 exists or not
            output.append("Some error occurred in unfollow_user.");
            return output.toString();
        }

        User user=allUser.get(userId1); //get user1 from allUser hashmap
        User follow=allUser.get(userId2); //get user2 from allUser hashmap

        if(user.followerSet.contains(follow)){ //check if user1 follows user2
            user.followerSet.remove(follow);
            user.followerList.remove(follow);
            output.append(userId1).append(" unfollowed ").append(userId2).append(".");
            return output.toString();
        }
        else {
            output.append("Some error occurred in unfollow_user.");
            return output.toString();
        }
    }

    public static String createPost(String userId, String postId, String content){
        StringBuilder output=new StringBuilder(); //to design result
        if(!allUser.containsKey(userId)){ //check if user exists or not
            output.append("Some error occurred in create_post.");
            return output.toString();
        }

        if(allPost.containsKey(postId)){ //check if the post exists or not
            output.append("Some error occurred in create_post.");
            return output.toString();
        }

        Post newPost=new Post(userId, postId, content); //create post
        User user=allUser.get(userId); //get user from allUser hashmap

        allPost.put(postId, newPost); //add the new post into the all post set
        user.userPosts.add(newPost);  //add the new post into the user's post list
        output.append(userId).append(" created a post with Id ").append(postId).append(".");
        return output.toString();  //return string result
    }

    public static String seePost(String userId, String postId){
        StringBuilder output=new StringBuilder();  //to design string result
        if(!allUser.containsKey(userId) || !allPost.containsKey(postId)){  //check if user and post exist or not
            output.append("Some error occurred in see_post.");
            return output.toString();
        }

        User user=allUser.get(userId);  //get user from allUser hashmap
        Post post=allPost.get(postId);  //get post from allPost hashmap

        user.seenPosts.add(post);  //add the post into the user's seen posts set
        output.append(userId).append(" saw ").append(postId).append(".");
        return output.toString();  //return string result
    }

    public static String seeAllPost(String viewerId, String viewedId){
        StringBuilder output=new StringBuilder();  //to design string result
        if(!allUser.containsKey(viewerId) || !allUser.containsKey(viewedId)){ //check if user1 and user2 exist or not
            output.append("Some error occurred in see_all_posts_from_user.");
            return output.toString();
        }

        User viewer=allUser.get(viewerId);  //get user1 from allUser hashmap
        User viewed=allUser.get(viewedId);  //get user2 from allUser hashmap

        for(Post post:viewed.userPosts){  //adding all not seen post of user2 into the user1's seen post set
            if(post!=null){
                viewer.seenPosts.add(post);
            }
        }
        output.append(viewerId).append(" saw all posts of ").append(viewedId).append(".");
        return output.toString();  //return string result
    }

    public static String pressLike(String userId, String postId){
        StringBuilder output=new StringBuilder();  //to design string result
        if(!allUser.containsKey(userId) || !allPost.containsKey(postId)){  //check if user and post exist or not
            output.append("Some error occurred in toggle_like.");
            return output.toString();
        }

        User user=allUser.get(userId);  //get user from allUser hashmap
        Post post=allPost.get(postId);  //get post from allPosts hashmap

        if(user.likedPosts.contains(post)){  //convert liked post to the unliked post
            user.likedPosts.remove(post);
            user.seenPosts.add(post);
            output.append(userId).append(" unliked ").append(postId).append(".");
            post.LikeCount-=1;
        }
        else {  //convert the unliked post to the liked post
            user.likedPosts.add(post);
            user.seenPosts.add(post);
            output.append(userId).append(" liked ").append(postId).append(".");
            post.LikeCount+=1;
        }
        return output.toString();  //return string result
    }

    public static String generateFeed(String userId, int num) {
        StringBuilder output = new StringBuilder();  //to design string result

        if (!allUser.containsKey(userId)) { //check if user exists or not
            output.append("Some error occurred in generate_feed.");
            return output.toString();
        }

        User user = allUser.get(userId);  //get user from allUser hashmap
        ArrayList<Post> feedPosts = new ArrayList<>();

        // Collect posts from followers
        for (User follower : user.followerList) {
            feedPosts.addAll(follower.userPosts);
        }

        // Remove seen posts early
        feedPosts.removeIf(post -> user.seenPosts.contains(post));

        // Sort posts by likes and lexicographical order
        feedPosts.sort((a, b) -> {
            if (b.LikeCount != a.LikeCount) {
                return Integer.compare(b.LikeCount, a.LikeCount);
            }
            return b.postID.compareTo(a.postID);
        });

        // Build feed output
        output.append("Feed for ").append(userId).append(":");
        int count = 0;

        for (Post post : feedPosts) {
            if (count >= num) break;
            output.append("\nPost ID: ").append(post.postID)
                    .append(", Author: ").append(post.authorID)
                    .append(", Likes: ").append(post.LikeCount);
            count++;
        }

        // If not enough posts are available
        if (count < num) {
            output.append("\nNo more posts available for ").append(userId).append(".");
        }

        return output.toString();  //return string result
    }


    public static String scrollThroughFeed(String userId, int num, int[] action) {
        StringBuilder output = new StringBuilder();  //to design string result

        if (!allUser.containsKey(userId)) {  //check if user exists or not
            output.append("Some error occurred in scroll_through_feed.");
            return output.toString();
        }

        User user = allUser.get(userId);  //get user from allUser hashmap
        ArrayList<Post> feedPosts = new ArrayList<>();

        // Collect posts from followers
        for (User follower : user.followerList) {
            feedPosts.addAll(follower.userPosts);
        }

        // Remove seen posts early
        feedPosts.removeIf(post -> user.seenPosts.contains(post));

        // Sort posts by likes and lexicographical order
        feedPosts.sort((a, b) -> {
            if (b.LikeCount != a.LikeCount) {
                return Integer.compare(b.LikeCount, a.LikeCount);
            }
            return b.postID.compareTo(a.postID);
        });

        // Process feed scrolling actions
        output.append(userId).append(" is scrolling through feed:");
        int count = 0;

        for (Post post : feedPosts) {
            if (count >= num) break;

            if (action[count] == 1) {
                // User likes/unlikes the post
                user.seenPosts.add(post);
                if (user.likedPosts.contains(post)) {
                    user.likedPosts.remove(post);
                    post.LikeCount--;
                } else {
                    user.likedPosts.add(post);
                    post.LikeCount++;
                }
                output.append("\n").append(userId).append(" saw ").append(post.postID)
                        .append(" while scrolling and clicked the like button.");
            } else {
                // User only views the post
                user.seenPosts.add(post);
                output.append("\n").append(userId).append(" saw ").append(post.postID)
                        .append(" while scrolling.");
            }

            count++;
        }

        // If not enough posts are available
        if (count < num) {
            output.append("\nNo more posts in feed.");
        }

        return output.toString();  //return string result
    }


    public static String sortPosts(String userId) {
        StringBuilder output = new StringBuilder();

        // Check if the user exists
        if (!allUser.containsKey(userId)) {
            output.append("Some error occurred in sort_posts.");
            return output.toString();
        }

        User user = allUser.get(userId);

        // Check if the user has posts
        if (user.userPosts.isEmpty()) {
            output.append("No posts from ").append(userId);
            return output.toString();
        }

        // Header for the output
        output.append("Sorting ").append(userId).append("'s posts:");

        // Convert userPosts to an array
        Post[] userPostArray = user.userPosts.toArray(new Post[0]);

        // Initialize or use a temporary heap for sorting
        MyHeapMax<Post> postHeap = new MyHeapMax<>(userPostArray);

        // Retrieve posts in sorted order without modifying original list
        while (!postHeap.isEmpty()) {
            Post post = postHeap.deleteMax(); // Extract posts in descending order
            output.append("\n").append(post.postID).append(", Likes: ").append(post.LikeCount);
        }

        return output.toString();
    }



}
