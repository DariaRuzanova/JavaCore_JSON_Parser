import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "new_data.json";
        String json = readString(fileName);
        List<Employee> list = jsonToList(json);
        list.forEach(System.out::println);
    }

    private static String readString(String fileName) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fileReader)) {
            String line = br.readLine();
            while (line != null) {
                resultStringBuilder.append(line).append("\n");
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }

    private static List<Employee> jsonToList(String json) {
        List<Employee> employees = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(json);
            for (Object entry : jsonArray) {
                JSONObject obj = (JSONObject) entry;
                Employee employee = gson.fromJson(String.valueOf(obj), Employee.class);
                employees.add(employee);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

}
