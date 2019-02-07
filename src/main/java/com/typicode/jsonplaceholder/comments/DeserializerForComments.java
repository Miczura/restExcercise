package com.typicode.jsonplaceholder.comments;

import io.restassured.response.Response;

import java.util.List;

public class DeserializerForComments {
    public static <T> List<T> deserializeToList (Response response, Class<T> className){
        return response.body().jsonPath().getList("", className);
    }

}
