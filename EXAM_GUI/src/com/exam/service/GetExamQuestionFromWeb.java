package com.exam.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GetExamQuestionFromWeb {

    public List<HashMap<String,Object>> getExamQuestions(String host, String token, String branch, String username, String coursecode, String examtype, String batch) {
        //System.out.println(host + " "+ token +" "+ branch + " " + username + " " + coursecode + " " + examtype + " " + batch);
        try {

            String queryParams =
                    "branch=" + URLEncoder.encode(branch, StandardCharsets.UTF_8) +
                    "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8) +
                    "&coursecode=" + URLEncoder.encode(coursecode, StandardCharsets.UTF_8) +
                    "&examtype=" + URLEncoder.encode(examtype, StandardCharsets.UTF_8) +
                    "&batch=" + URLEncoder.encode(batch, StandardCharsets.UTF_8);
                    
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(host + "student/examquestions?" + queryParams))
                    .header("Authorization", token)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            // ✅ JSON Array → List<ExamQuestion>
            return mapper.readValue(
                    response.body(),
                    new TypeReference<List<HashMap<String,Object>>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // empty list
        }
    }

//     public static void main(String[] args) {
//          GetExamQuestionFromWeb service = new GetExamQuestionFromWeb();
//          List<HashMap<String,Object>> questions = service.getExamQuestions(
//                  "http://localhost:8080/",
//                  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTVFUwNU83Iiwicm9sZSI6IlNUVURFTlQiLCJpYXQiOjE3NzI0MzgwMTgsImV4cCI6MTc3MjQ3NDAxOH0.T3LCHmyUCUKDhBpGmZPGvSNS-6hgp_Tibt1t7rx8ZRk",
//                  "CSE",
//                  "STU05O7",
//                  "V20MAT01",
//                  "MID-1",
//                  "2022"
//          );
         
//          System.out.println(questions);
//      }
}