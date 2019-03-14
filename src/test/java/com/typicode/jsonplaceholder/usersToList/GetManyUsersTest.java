package com.typicode.jsonplaceholder.usersToList;

import com.typicode.jsonplaceholder.utils.Endpoint;
import com.typicode.jsonplaceholder.utils.StatusCodes;
import com.typicode.jsonplaceholder.users.Users;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.typicode.jsonplaceholder.utils.DeserializerForListsGeneric.deserializeToList;
import static io.restassured.RestAssured.given;


public class GetManyUsersTest {
    private static Response response;
    @BeforeClass
    public static void getUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS)
                .when().get(Endpoint.USERS_ENDPOINT);
    }
    @Test
    public void getUsersCountUsersInListTest(){
        Assert.assertEquals(10,counUsersInResponse());

    }


    private int counUsersInResponse(){
        List<Users> usersInResponseList = deserializeToList(response,Users.class);
        return usersInResponseList.size();
    }

}
