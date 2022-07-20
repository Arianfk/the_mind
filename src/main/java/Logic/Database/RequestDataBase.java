package Logic.Database;

import GUI.Controller.QuitAccepting;
import Logic.Models.Professor;
import Logic.Requests.Request;
import Logic.Requests.RequestSC;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class RequestDataBase {


    public static ArrayList<Request> requests=new ArrayList<>();

    public static Logger log= LogManager.getLogger(RequestDataBase.class);


    public static ArrayList<Request> getRequests() {
        if (requests==null){
            requests=new ArrayList<>();
        }
        return requests;
    }

    public static Request searchByRequestId(String requestId){
        for (Request request: getRequests()){
            String id=request.getRequestId();
            if(id.equals(requestId)){
                return request;
            }
        }
        return null;
    }

    public static ArrayList<Request> convertToRequestArraylist(ArrayList<String> requestIds){
        ArrayList<Request> requestArrayList=new ArrayList<>();
        if(requestIds==null){
            return requestArrayList;
        }
        for(Request request : getRequests()){
            String id=request.getRequestId();
            if(requestIds.contains(id)){
                requestArrayList.add(request);
            }
        }
        return requestArrayList;
    }

    public static ArrayList<Request> filterRequestsByType(ArrayList<Request> request , RequestSC requestSC){
        ArrayList<Request> requestArrayList=new ArrayList<>();
        if(request==null){
            return requestArrayList;
        }
        for(Request r : request){
            RequestSC sc=r.getRequestSC();
            if(sc.equals(requestSC)){
                requestArrayList.add(r);
            }
        }
        return requestArrayList;
    }

    public static ArrayList<Request> professorsRequests(Professor professor){
        ArrayList<Request> requestArrayList=new ArrayList<>();
        if (professor==null){
            return requestArrayList;
        }
        for(Request request : getRequests()) {
            log.info("searching in requests data base");
            boolean isFirstProfessor=request.getProfessor() !=null && professor.equals(request.getProfessor()) ;
            boolean isSecondProfessor =  request.getSecondProfessor() != null && professor.equals(request.getSecondProfessor());
            if(isFirstProfessor || isSecondProfessor ){//|| isSecondProfessor
                log.info("is for the professor we wanted");
                requestArrayList.add(request);
            }
            else{
                log.info("is not for this professor");
            }
        }


        return requestArrayList;
    }






}
