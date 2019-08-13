package in.regres.listUsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Endpoint;
import utils.StatusCodes;


import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.DeserialiserForSingleObjectGeneric.deserialiseToAnyObject;


public class RegresUsersTest {
    private static Response response;

    @BeforeClass
    public static void getRegresUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS).
                when().get(Endpoint.IN_REGRES_GET_USERS_ENDPOINT);
    }
    @Test
    public void getInRegresListUsersCount(){
        Assert.assertEquals("Number of regresUsers is not valid",3,getRegresUsersCount());
    }

    @Test
    public void getInRegresListUsersCountSecondWay(){
        Assert.assertEquals("Number of regresUsers is not valid",3,secondMethodTest(response));
    }

    private int getRegresUsersCount() {
        RegresUsers responseObject = deserialiseToAnyObject(response, RegresUsers.class);
        responseObject.getData().size();
        return responseObject.getData().size();
    }
    private int secondMethodTest(Response response){
        String body =response.getBody().asString();
        int size =0;
        try {
            RegresUsers response2 = new ObjectMapper().readValue(body, RegresUsers.class);
            size =response2.getData().size();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
}
