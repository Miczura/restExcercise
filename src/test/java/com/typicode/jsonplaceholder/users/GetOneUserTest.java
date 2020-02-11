package com.typicode.jsonplaceholder.users;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import org.junit.After;
import utils.Endpoint;
import utils.SaveResponse;
import utils.StatusCodes;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static utils.DeserializerForSingleObjectGeneric.deserializeToAnyObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GetOneUserTest {
    private static Response response;
    private static Users deserialisedUsersResponseObject;
    @BeforeClass
    public static void getUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS)
                .when().get(Endpoint.USERS_1_ENDPOINT);
        deserialisedUsersResponseObject = deserializeToAnyObject(response,Users.class);
    }

    @Test
    @Description("Verify response size")
    public void checkSizeOfResponseBody(){
        response.then().assertThat().body("size()",is(8));
    }

    @Test
    @Description("Verify address attribute size")
    public void checkSizeOfAddressAttributeInResponseBody(){
        response.then().assertThat().body("address.size()",is(5));
    }

    @Test
    @Description("Verify longitude attribute value")
    public void checkLongitudeInResponseBody(){
        response.then().assertThat().body("address.geo.lng",equalTo("81.1496"));
    }

    @Test
    @Description("Verify getBs attribute value ")
    public void checkGetBsAttributeValueTest(){

        Assert.assertTrue("getBs attribute do not contain \"e-market\"",deserialisedUsersResponseObject.getCompany().getBs().contains("e-markets"));
        assertThat(deserialisedUsersResponseObject.getCompany().getBs(),is(equalTo("harness real-time e-markets")));
    }

    @Test
    @Description("Verify getId attribute value")
    public void checkUserIdObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getId(),is(1));
    }

    @Test
    @Description("Verify name attribute value")
    public void checkUserNameObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getName(),is(equalTo("Leanne Graham")));
    }

    @Test
    @Description("Verify username attribute value")
    public void checkNameObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getUsername(),is(equalTo("Bret")));
    }

    @After
    public void onTeardown(){
        SaveResponse.saveResponse(response.getBody().asString(),response.getStatusCode(),response.getHeaders().asList());
    }

    private Users deserializeResponseToObject(Response response)  {
        ObjectMapper mapper = new ObjectMapper();
        String body = response.getBody().asString();
        Users userObject=null;
        try {
            userObject = mapper.readValue(body, Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userObject;
    }
}
