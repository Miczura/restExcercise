package com.typicode.jsonplaceholder.comments;



import io.qameta.allure.Description;
import utils.Endpoint;
import utils.StatusCodes;
import io.restassured.response.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static utils.DeserializerForListsGeneric.*;
import static com.typicode.jsonplaceholder.comments.FilterForComments.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;


/**
 * Unit test for simple App.
 */
public class GetCommentTest{
    private static Response response;

    @BeforeClass
    public static void getCommentsEndpoint(){
        response = given().expect().statusCode(StatusCodes.SUCCESS).
                when().get(Endpoint.COMMENTS_ENDPOINT);
    }
    @Test
    @Description("Verify if response size is not empty and body contains email Jayne_Kuhic@sydney.com")
    public void getCommentsCheckResponseLengthAndEmailConditions(){
        response.then().assertThat().body("",hasSize(greaterThan(0))).
                and().body("email",hasItem("Jayne_Kuhic@sydney.com"));

    }
    @Test
    @Description("Verify if response contains such comments with postId = 1 and containing word 'non' in body ")
    public void getCommentsVerifyFiltering(){
        List<Comments> deserializedResponse = deserializeToList(response,Comments.class);
        List<Comments> filteredComments= filterCommentsAccordingToCondition(deserializedResponse,POSTID_1_AND_NON_IN_BODY);
        assertTrue("One of the body in response does not contain \"non\"",filteredComments.stream().allMatch(comments->comments.getBody().contains("non")));
        assertTrue("One of the postId isn't equal to 1", filteredComments.stream().allMatch(comments -> comments.getPostId()==1));

    }


}
