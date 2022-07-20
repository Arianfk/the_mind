package Logic.Models;

import Logic.Database.CollageDataBase;
import Logic.Database.Functions;
import Logic.Database.SubjectDataBase;
import Logic.Database.UserDatabase;
import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class User {


    public static Logger log= LogManager.getLogger(User.class);


    protected String fullName;
    protected String codeMelli;
    protected String PhoneNumber;
    protected String email;
    public String usersCollageId;
    protected String username;
    protected String password;
    public String lastLog;
    public String imageUrl;

    //getters&setters


    //last log:

    public LocalDateTime getLastLogLocalDateTime() {
        return Functions.toLocalDateTimeDate(lastLog);
    }

    public void setTimeOfLastEntrance(LocalDateTime timeOfLastEntrance) {
        this.lastLog= Functions.toStringDate(timeOfLastEntrance);
    }

    public String getLastLog() {
        return lastLog;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
    }

    //collage:

    public Collage getCollage() {
        return CollageDataBase.searchById(usersCollageId);
    }

    public String getUsersCollageId() {
        return usersCollageId;
    }

    public void setUsersCollageId(String usersCollageId) {
        this.usersCollageId = usersCollageId;
    }

    //personal info :


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodeMelli(String codeMelli) {
        this.codeMelli = codeMelli;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCodeMelli() {
        return codeMelli;
    }

    //username password:

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // image :


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image getPhoto() {
        Image image=new Image(imageUrl);
        return image;
    }


    //constructors


    public User(){

    }

    public ArrayList<Subject> getCurrentTermSubjects(){
        return null;
    }

    public ArrayList<String> getProfile(){
        ArrayList<String> data=new ArrayList<>();
        try{
            String name="name: "+this.getFullName();
            String codeMelli="national code: "+this.getCodeMelli();
            String PhoneNumber="phone number: "+this.getPhoneNumber();
            String email="email: "+this.getEmail();
            String collage="collage: "+this.getCollage().getName();
            data.add(name);
            data.add(codeMelli);
            data.add(PhoneNumber);
            data.add(email);
            data.add(collage);
            log.info("profile is built");
        }catch (Exception e){
            log.error("can't build the profile");
        }
        return data;
    }
}
