package Logic.Database;

//import BuildData.University;
import BuildData.ReadDefualtData;
import GUI.View.LoginView;
import Logic.Models.*;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static Logic.Models.MojavesTahsili.saderShode;
import static Logic.Models.VasietSabtName.mojazBeSabtNam;

public class UserDatabase {

    public static HashMap<String,String> usernamePassword=new HashMap<>();
    public static  ArrayList<User> users=new ArrayList<>();



    public static String hash(String t) throws NoSuchAlgorithmException {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(t.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException ignored) {

        }

        return hash.toString();
    }


    public static void setUsers(ArrayList<User> users) {
        UserDatabase.users = users;
    }

    public static ArrayList<User> getUsers() {
        if (users==null){
            users=new ArrayList<>();
        }
        return users;
    }

    public static void setUsernamePassword(HashMap<String, String> usernamePassword) {
        UserDatabase.usernamePassword = usernamePassword;
    }

    public HashMap<String, String> getUsernamePassword() {
        return usernamePassword;
    }

    public boolean searchByUsernamePassword(String username, String password){
        Set<String> usernames=usernamePassword.keySet();
        for(String x: usernames){
            if(x.equals(username)){
                String ThePassword =usernamePassword.get(x);
                if(ThePassword.equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public static User searchUserByUsernamePassword(String username, String password) throws FileNotFoundException, NoSuchAlgorithmException {
        if(users==null){
            users=new ArrayList<>();
            return null;
        }
        if(usernamePassword.containsKey(username) && usernamePassword.get(username).equals(hash(password))){
            for(User x:users){
                boolean usernameCheck=x.getUsername().equals(username);
                if(usernameCheck){
                    //todo
                    return x;
                }
            }
        }

        return null;
    }
    public boolean searchByUsername(String username){
        Set<String> usernames=usernamePassword.keySet();
        for(String x: usernames){
            if(x.equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean addStudentWithUsernamePassword(String username,String password){
        if(searchByUsername(username)){
            return false;
        }
        Student student=new Student(username,password);
        StudentDataBase.students.add(student);
        return true;
    }
}
