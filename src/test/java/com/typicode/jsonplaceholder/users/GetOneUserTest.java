package com.typicode.jsonplaceholder.users;

import utils.Endpoint;
import utils.StatusCodes;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static utils.DeserialiserForSingleObjectGeneric.deserialiseToAnyObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GetOneUserTest {
    private static Response response;
    @BeforeClass
    public static void getUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS)
                .when().get(Endpoint.USERS_1_ENDPOINT);
    }
    @Test
    public void getUsersCheckSizeOfJsonResponseBody(){
        response.then().assertThat().body("size()",is(8));
    }

    @Test
    public void getUsersCheckSizeOfAddressAttributeInResponseBody(){
        response.then().assertThat().body("address.size()",is(5));
    }

    @Test
    public void getUsersCheckLongnitudeInResponeBody(){
        response.then().assertThat().body("address.geo.lng",equalTo("81.1496"));
        System.out.println("Test Passed");
    }
    @Test
    public void getUsersDeserialisationToObjectTest(){
        Users deserialisedUsersResponseObject = deserialiseToAnyObject(response,Users.class);
        Assert.assertTrue("getBs do not contain \"e-market\"",deserialisedUsersResponseObject.getCompany().getBs().contains("e-markets"));
        assertThat(deserialisedUsersResponseObject.getCompany().getBs(),is(equalTo("harness real-time e-markets")));
    }
}
