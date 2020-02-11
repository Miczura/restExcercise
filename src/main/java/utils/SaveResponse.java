package utils;

import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.List;

public class SaveResponse {

    @Attachment(value = "{response body,status code,headers}", type = "text/plain")
    public static String saveResponse(String body, int statusCode, List<Header> headers) {

        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Status code : "+statusCode);
        result.append("\n");
        result.append("Response headers : ");
        result.append("\n");
        headers.stream().forEach(header-> result.append(header.getName()+" : "+header.getValue()+"\n"));
        result.append("Response body : ");
        result.append("\n");
        result.append(body);
        return result.toString();
    }
}
