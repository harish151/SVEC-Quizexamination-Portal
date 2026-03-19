package com.exam.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class UpdateQueProgress {

    public UpdateQueProgress(Map<String,Object> questionData,
                             String optText,
                             String host,String token){

        try {
            List<String> options = (List<String>) questionData.get("options");
            List<String> optImgs = (List<String>) questionData.get("optimgurl");

            String json =
            "{"
            + "\"id\":\""+safe(questionData.get("id"))+"\","  
            + "\"username\":\""+safe(questionData.get("username"))+"\","
            + "\"batch\":\""+safe(questionData.get("batch"))+"\","
            + "\"exam_type\":\""+safe(questionData.get("exam_type"))+"\","
            + "\"branch\":\""+safe(questionData.get("branch"))+"\","
            + "\"semester\":\""+safe(questionData.get("semester"))+"\","
            + "\"coursecode\":\""+safe(questionData.get("coursecode"))+"\","
            + "\"question_no\":\""+safe(questionData.get("question_no"))+"\","
            + "\"questionurl\":\""+safe(questionData.get("questionurl"))+"\","
            + "\"question\":\""+safe(questionData.get("question"))+"\","
            + "\"options\":"+listToJsonArray(options)+","
            + "\"answer\":\""+safe(questionData.get("answer"))+"\","
            + "\"optimgurl\":"+listToJsonArray(optImgs)+","
            + "\"selectedopt\":\""+safe(optText)+"\""
            + "}";

           // System.out.println("Sending JSON:");
           //System.out.println(json);
            //System.out.println(token);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(host + "student/updateprogress"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            //HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println("Response : " + response.body());

        } catch (Exception e) {
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