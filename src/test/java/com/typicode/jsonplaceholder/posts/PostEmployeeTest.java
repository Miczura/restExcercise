package com.typicode.jsonplaceholder.posts;


import com.typicode.jsonplaceholder.utils.Endpoint;
import com.typicode.jsonplaceholder.utils.StatusCodes;
import org.junit.Assert;
import org.junit.BeforeClass;
import io.restassured.response.Response;
import org.junit.Test;


import static com.typicode.jsonplaceholder.utils.DeserialiserForSingleObjectGeneric.deserialiseToAnyObject;
import static io.restassured.RestAssured.*;

public class PostEmployeeTest {
    private static Response response;
    private static Employee employee;

    @BeforeClass
    public static void setup(){
        employee=new Employee("Test","AAAa",20,222);
        response = given().contentType("application/json").body(employee).expect().statusCode(StatusCodes.CREATED)
                .when().post(Endpoint.POSTS_ENDPOINT);
    }

    @Test
    public void checkNameInResponse(){

        Assert.assertTrue(deserialize().getName().equals("Test"));
    }
    @Test
    public void checkSurnameInResponze(){
        Assert.assertEquals("Surname is not equal to expected","AAAa",deserialize().getSurname());
    }

    private static Employee deserialize(){
        return deserialiseToAnyObject(response,Employee.class);
    }
}
