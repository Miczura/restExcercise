package com.typicode.jsonplaceholder.posts;


import io.qameta.allure.Description;
import org.junit.After;
import utils.Endpoint;
import utils.SaveResponse;
import utils.StatusCodes;
import org.junit.Assert;
import org.junit.BeforeClass;
import io.restassured.response.Response;
import org.junit.Test;


import static utils.DeserializerForSingleObjectGeneric.deserializeToAnyObject;
import static io.restassured.RestAssured.*;

public class PostEmployeeTest {
    private static Response response;
    private static Employee employee;

    @BeforeClass
    public static void setup(){
        employee=new Employee("Dariusz","Nowak",20,222);
        response = given().contentType("application/json").body(employee).expect().statusCode(StatusCodes.CREATED)
                .when().post(Endpoint.POSTS_ENDPOINT);
    }

    @Test
    @Description("Verify name of created user")
    public void checkNameOfCreatedEmployee(){

        Assert.assertTrue(deserialize().getName().equals("Dariusz"));
    }

    @Test
    @Description("Verify surname of created user")
    public void checkSurnameOfCreatedEmployee(){
        Assert.assertEquals("Surname is not equal to expected","Nowak",deserialize().getSurname());
    }

    @Test
    @Description("Verify userId of created user")
    public void checkEpmloyeeUserId(){
        Assert.assertEquals("Employee has different UserId than expected",20,deserialize().getUserId());
    }
    @After
    public void onTeardown(){
        SaveResponse.saveResponse(response.getBody().asString(),response.getStatusCode(),response.getHeaders().asList());
    }
    private static Employee deserialize(){
        return deserializeToAnyObject(response,Employee.class);
    }
}
