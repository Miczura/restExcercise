package in.regres.listUsers;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Endpoint;
import utils.StatusCodes;


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


    private int getRegresUsersCount() {
        RegresUsers responseObject = deserialiseToAnyObject(response, RegresUsers.class);
        responseObject.getData().size();
        return responseObject.getData().size();
    }
}
