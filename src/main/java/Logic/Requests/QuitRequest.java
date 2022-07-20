package Logic.Requests;

import Logic.Database.UserDatabase;
import Logic.Models.Collage;
import Logic.Models.MojavesTahsili;
import Logic.Models.Student;

public class QuitRequest extends Request{


    public QuitRequest(Student student) {
        super(student);
        this.requestSC=RequestSC.quite;
        this.stateOfRequest=StateOfRequest.registered;
        this.professorId=student.getCollage().getEducationViceChairId();
        log.info("quit request");
    }

    @Override
    public void acceptRequest() {
        super.acceptRequest();
        Student student = getStudent();
        String username=student.getUsername();
        String password=student.getPassword();
        UserDatabase.usernamePassword.remove(username,password);
        student.setVasiatTahsili(MojavesTahsili.no);
    }


}
