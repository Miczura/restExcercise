package com.typicode.jsonplaceholder.users;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import utils.Endpoint;
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
    @Description("Verify response body size")
    public void checkSizeOfResponseBody(){
        response.then().assertThat().body("size()",is(8));
    }

    @Test
    @Description("Verify size od address attribute in response")
    public void checkSizeOfAddressAttributeInResponseBody(){
        response.then().assertThat().body("address.size()",is(5));
    }

    @Test
    @Description("Verify longiture attribute value")
    public void checkLongitudeInResponseBody(){
        response.then().assertThat().body("address.geo.lng",equalTo("81.1496"));
    }
    @Test
    @Description("Verify if getBs attribute contains 'e-market' and its whole value")
    public void checkGetBsAttributeValueTest(){

        Assert.assertTrue("getBs attribute do not contain \"e-market\"",deserialisedUsersResponseObject.getCompany().getBs().contains("e-markets"));
        assertThat(deserialisedUsersResponseObject.getCompany().getBs(),is(equalTo("harness real-time e-markets")));
    }

    @Test
    @Description("Verify userId attribute value in response using object mapper")
    public void checkUserIdObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getId(),is(1));
    }

    @Test
    @Description("Verify username attribute value with object mapper")
    public void checkUserNameObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getName(),is(equalTo("Leanne Graham")));
    }

    @Test
    @Description("Verify name attribute using object mapper")
    public void checkNameObjectMapperTest(){
        assertThat(deserializeResponseToObject(response).getUsername(),is(equalTo("Bret")));
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
