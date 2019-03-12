package com.typicode.jsonplaceholder.users;

import io.restassured.response.Response;


public class DeserialiserForUsers {

    public static Users deserialiseToUsersObject(Response response){
        return response.body().jsonPath().getObject("", Users.class);
    }
}
