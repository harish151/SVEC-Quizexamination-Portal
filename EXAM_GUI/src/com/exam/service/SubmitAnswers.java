package src.com.exam.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import src.com.exam.model.CurrentExam;
import src.com.exam.model.Student;

public class SubmitAnswers {
    public SubmitAnswers(String host ,String token, Student student, CurrentExam currentexam, List<String> originalans,List<String> answers ){
        //System.out.println(host +" "+ token +" "+ student +" "+ currentexam +" "+originalans+" "+attemptedans);
        try{
            String json =
            "{" 
            + "\"section\":\""+safe(student.getSection())+"\","
            + "\"batch\":\""+safe(student.getBatch())+"\","
            + "\"examType\":\""+safe(currentexam.getExamtype())+"\","
            + "\"branch\":\""+safe(student.getBranch())+"\","
            + "\"semester\":\""+safe(student.getSemester())+"\","
            + "\"coursecode\":\""+safe(currentexam.getCoursecode())+"\","
            + "\"username\":\""+safe(student.getUsername())+"\","
            + "\"originalans\":"+listToJsonArray(originalans)+","
            + "\"status\":\""+"submitted"+"\","
            + "\"attemptedans\":"+listToJsonArray(answers)+""
            + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(host + "common/uploadresults"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            //HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println("Response : " + response.body());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

 /* ================= SAFE VALUE ================= */

    private String safe(Object obj){
        if(obj == null) return "";
        return obj.toString().replace("\"","\\\"");
    }


    /* ================= LIST TO JSON ARRAY ================= */

    private String listToJsonArray(List<String> list){

        if(list == null) return "[]";

        StringBuilder sb = new StringBuilder("[");
        
        for(int i=0;i<list.size();i++){

            String val = list.get(i);

            if(val == null) val = "";

            sb.append("\"").append(val.replace("\"","\\\"")).append("\"");

            if(i < list.size()-1){
                sb.append(",");
            }
        }

        sb.append("]");

        return sb.toString();
    }
}

