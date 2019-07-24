package com.typicode.jsonplaceholder.usersToList;

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
    public void getUsersCountUsersInListTest(){
        Assert.assertEquals(10,countUsersInResponse());

    }
    @Test
    public void checkCountOfLattitudeAttibutes(){
        Assert.assertEquals(10,countLattitudeAttributes());
    }

    private List<Users> getObjectsFromResponse(){
        List<Users> usersInResponseList = deserializeToList(response,Users.class);
        return usersInResponseList;
    }
    private int countUsersInResponse(){

        return getObjectsFromResponse().size();
    }
    private int countLattitudeAttributes(){
        List<String> lattitudeList = getObjectsFromResponse().stream().map(lattitude->lattitude.getAddress().getGeo().getLat()).collect(Collectors.toList());
        return lattitudeList.size();
    }
}
