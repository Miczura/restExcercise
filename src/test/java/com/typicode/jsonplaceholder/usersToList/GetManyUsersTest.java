package com.typicode.jsonplaceholder.usersToList;

import io.qameta.allure.Description;
import utils.Endpoint;
import utils.StatusCodes;
import com.typicode.jsonplaceholder.users.Users;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static utils.DeserializerForListsGeneric.deserializeToList;
import static io.restassured.RestAssured.given;


public class GetManyUsersTest {
    private static Response response;
    @BeforeClass
    public static void getUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS)
                .when().get(Endpoint.USERS_ENDPOINT);
    }
    @Test
    @Description("Verify number of users in response")
    public void getUsersCountUsersInListTest(){
        Assert.assertEquals(10,countUsersInResponse());

    }
    @Test
    @Description("Verify number of latitude attributes in response")
    public void checkCountOfLatitudeAttributes(){
        Assert.assertEquals(10,countLatitudeAttributes());
    }

    private List<Users> getObjectsFromResponse(){
        List<Users> usersInResponseList = deserializeToList(response,Users.class);
        return usersInResponseList;
    }
    private int countUsersInResponse(){

        return getObjectsFromResponse().size();
    }
    private int countLatitudeAttributes(){
        List<String> latitudeList = getObjectsFromResponse().stream().map(latitude->latitude.getAddress().getGeo().getLat()).collect(Collectors.toList());
        return latitudeList.size();
    }
}
