import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        long starttime=System.currentTimeMillis();

        String inputFile=args[0];
        String outputFile=args[1];

        BufferedReader reader=new BufferedReader(new FileReader(inputFile)); //Reading file
        BufferedWriter writer=new BufferedWriter(new FileWriter(outputFile)); //Writing in a file

        String line;
        while ((line= reader.readLine()) !=null){
            String[] list=line.split(" ");
            String command=list[0];  //Command name

            switch (command){

                case "create_user":{ //create_user command process
                    String  userId=list[1];
                    String output=Organization.createUser(userId);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "follow_user":{ //follow_user command process
                    String userId1=list[1];
                    String userId2=list[2];
                    String output=Organization.followUser(userId1, userId2);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "unfollow_user":{ //unfollow_user command process
                    String userId1=list[1];
                    String userId2=list[2];
                    String output=Organization.unfollowUser(userId1, userId2);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "create_post":{  //create_post command process
                    String userId=list[1];
                    String postId=list[2];
                    String content=list[3];
                    String output=Organization.createPost(userId, postId, content);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "see_post":{  //see_post command process
                    String userId=list[1];
                    String postId=list[2];
                    String output=Organization.seePost(userId, postId);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "see_all_posts_from_user":{  //see_all_posts_from_user command porcess
                    String viewerId=list[1];
                    String viewedId=list[2];
                    String output=Organization.seeAllPost(viewerId,viewedId);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "toggle_like":{  //toggle_like command process
                    String userId=list[1];
                    String postId=list[2];
                    String output=Organization.pressLike(userId, postId);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "generate_feed":{  //generate_feed command process
                    String userId=list[1];
                    int num=Integer.parseInt(list[2]);
                    String output=Organization.generateFeed(userId, num);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "scroll_through_feed":{  //scroll_through command process
                    String userId=list[1];
                    int num=Integer.parseInt(list[2]);
                    String[] tempArray=new String[num];
                    System.arraycopy(list,3,tempArray,0,num);
                    int[] action=new int[num];
                    for (int i=0; i<num; i++){
                        action[i]=Integer.parseInt(tempArray[i]);
                    }
                    String output=Organization.scrollThroughFeed(userId, num, action);
                    writer.write(output);
                    writer.newLine();
                    break;
                }

                case "sort_posts": {  //sort_posts command process
                    String userId = list[1];
                    String output = Organization.sortPosts(userId);
                    writer.write(output);
                    writer.newLine();
                    break;
                }
            }
        }
        reader.close();
        writer.close();

        long endtime=System.currentTimeMillis();
        long time=endtime-starttime;  //calculating process time

    }
}
