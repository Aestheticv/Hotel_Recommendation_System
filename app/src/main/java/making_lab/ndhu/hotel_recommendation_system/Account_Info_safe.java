package making_lab.ndhu.hotel_recommendation_system;

/**
 * Created by a1008 on 2018/1/9.
 */

public class Account_Info {
    private static String url = "jdbc:mysql://[Server IP]:3306/[Table]";
    private static String user = "[MySQL user]";
    private static String passwd = "[MySQL passwd]";

    public String url(){
        return url;
    }

    public String user(){
        return user;
    }

    public String passwd(){
        return  passwd;
    }
}