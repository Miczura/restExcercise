package com.typicode.jsonplaceholder.utils;

import io.restassured.response.Response;

import java.util.List;

public class DeserializerForListsGeneric {
    public static <T> List<T> deserializeToList (Response response, Class<T> className){
        return response.body().jsonPath().getList("", className);
    }

}
