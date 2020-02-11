package in.regres.listUsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Endpoint;
import utils.SaveResponse;
import utils.StatusCodes;


import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static utils.DeserializerForSingleObjectGeneric.deserializeToAnyObject;


public class RegresUsersTest {
    private static Response response;
    private static Headers headers;

    @BeforeClass
    public static void getRegresUsersEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS).
                when().get(Endpoint.IN_REGRES_GET_USERS_ENDPOINT);
        headers=response.getHeaders();
        List<Header> list = headers.asList();
        list.stream().forEach(element-> System.out.println(element.getName()+" AAAAA "+element.getValue()));
    }

    @Test
    @Description("Verify number of user accounts")
    public void checkInRegresListUsersSize(){
        Assert.assertEquals("Number of regresUsers is not valid",6,getRegresUsersSize());
    }

    @Test
    @Description("Verify number of user accounts second way")
    public void checkInRegresListUsersSizeWithObjectMapper(){
        Assert.assertEquals("Number of regresUsers is not valid",6,getRegresUsersCountSecond(response));
    }

    @Test
    @Description("Verify number of user accounts using soft assertions")
    public void checkInRegresListUsersSizeWithSoftAssertions(){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getRegresUsersCountSecond(response)).isEqualTo(6);
        softly.assertThat(deserializeResponseUsingObjectMapper(response).getPage()).isEqualTo(2);
        softly.assertAll();
    }

    @After
    public void onTeardown(){
        SaveResponse.saveResponse(response.getBody().asString(),response.getStatusCode(),response.getHeaders().asList());
    }

    private int getRegresUsersSize() {
        RegresUsers responseObject = deserializeToAnyObject(response, RegresUsers.class);
        responseObject.getData().size();
        return responseObject.getData().size();
    }
    private RegresUsers deserializeResponseUsingObjectMapper(Response response){
        String body =response.getBody().asString();
        RegresUsers responseObject = null;
        try {
            responseObject = new ObjectMapper().readValue(body, RegresUsers.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseObject;
    }
    private int getRegresUsersCountSecond(Response response){
        return deserializeResponseUsingObjectMapper(response).getData().size();
    }
}
