package com.typicode.jsonplaceholder.posts;


import utils.Endpoint;
import utils.StatusCodes;
import org.junit.Assert;
import org.junit.BeforeClass;
import io.restassured.response.Response;
import org.junit.Test;


import static utils.DeserialiserForSingleObjectGeneric.deserialiseToAnyObject;
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
    public void checkNameOfCreatedEmployee(){

        Assert.assertTrue(deserialize().getName().equals("Dariusz"));
    }
    @Test
    public void checkSurnameOfCreatedEmployee(){
        Assert.assertEquals("Surname is not equal to expected","Nowak",deserialize().getSurname());
    }

    @Test
    public void checkEpmloyeeUserId(){
        Assert.assertEquals("Employee has different UserId than expected",20,deserialize().getUserId());
    }

    private static Employee deserialize(){
        return deserialiseToAnyObject(response,Employee.class);
    }
}
