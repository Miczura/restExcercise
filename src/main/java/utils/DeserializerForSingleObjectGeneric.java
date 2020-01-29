package utils;

import io.restassured.response.Response;


public class DeserializerForSingleObjectGeneric {

//    public static Users deserialiseToUsersObject(Response response){
//        return response.body().jsonPath().getObject("", Users.class);
//    }
    public static <T> T deserializeToAnyObject(Response response, Class<T> Name){
        return response.body().jsonPath().getObject("", Name);
    }
}
