package utils;

import io.restassured.response.Response;


public class DeserialiserForSingleObjectGeneric {

//    public static Users deserialiseToUsersObject(Response response){
//        return response.body().jsonPath().getObject("", Users.class);
//    }
    public static <T> T deserialiseToAnyObject(Response response, Class<T> Name){
        return response.body().jsonPath().getObject("", Name);
    }
}
