public class Post implements Comparable<Post> {
    public String postID;
    public int LikeCount;
    public String authorID;
    public String content;

    public Post(String userId, String postID, String content){
        authorID=userId;
        this.postID=postID;
        this.content=content;
    }

    @Override
    public int compareTo(Post other) {
        // Compare by like count in descending order
        if (this.LikeCount != other.LikeCount) {
            return Integer.compare(this.LikeCount, other.LikeCount);
        }
        // If like counts are the same, compare by post ID in descending lexicographical order
        return this.postID.compareTo(other.postID);
    }
}
