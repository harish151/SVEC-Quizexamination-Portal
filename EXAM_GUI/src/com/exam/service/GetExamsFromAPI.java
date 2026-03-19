package src.com.exam.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class GetExamsFromAPI {

    public HashMap<String, String> getExamsFromAPI(String host, String token, String branch, String semester) {

        try {
            // 📅 today date
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            String queryParams =
                    "branch=" + URLEncoder.encode(branch, StandardCharsets.UTF_8) +
                    "&semester=" + URLEncoder.encode(semester, StandardCharsets.UTF_8) +
                    "&date=" + URLEncoder.encode(date, StandardCharsets.UTF_8);

            String url = host + "student/getexams?" + queryParams;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", token)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            // Convert JSON string to List<HashMap<String,String>>
            List<HashMap<String, String>> exams = mapper.readValue(
                    response.body(),
                    new TypeReference<List<HashMap<String, String>>>() {}
            );

            // ✅ Return first exam if exists, else empty map
            if (exams != null && !exams.isEmpty()) {
                return exams.get(0);
            } else {
                return new HashMap<>();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    // public static void main(String[] args) {
    //     GetExamsFromAPI api = new GetExamsFromAPI();
    //     String host = "http://localhost:8080/";
    //     String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTVFUwNU83Iiwicm9sZSI6IlNUVURFTlQiLCJpYXQiOjE3NzIxOTIwMTUsImV4cCI6MTc3MjE5OTIxNX0.kC94RUOeNIds_HABvKftn19vi80QVn77zyPh7pedJhA";
    //     String branch = "CSE";
    //     String semester = "I";
    //     HashMap<String, String> exams = api.GetExamsFromAPI(host, token, branch, semester);
    //     System.out.println(exams);
    // }
}