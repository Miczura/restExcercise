package com.typicode.jsonplaceholder.comments;


import com.typicode.jsonplaceholder.Endpoint;
import com.typicode.jsonplaceholder.StatusCodes;
import io.restassured.response.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.typicode.jsonplaceholder.comments.DeserializerForComments.*;
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
    public void getCommentsCheckResponseLengthAndEmailConditions(){
        response.then().assertThat().body("",hasSize(greaterThan(0))).
                and().body("email",hasItem("Jayne_Kuhic@sydney.com"));

    }
    @Test
    public void getCommentsVerifyFiltering(){
        List<Comments> deserializedResponse = deserializeToList(response,Comments.class);
//        deserializedResponse.forEach(item-> System.out.println("Item postId "+item.getPostId()
//        +" Item id "+item.getId()+" item name "+item.getName()+" item email "+item.getEmail()
//        +" Item body "+item.getBody()+"------"));
        List<Comments> filteredComments= filterCommentsAccordingToCondition(deserializedResponse,POSTID_1_AND_NON_IN_BODY);
        filteredComments.forEach(item-> System.out.println("Item postId "+item.getPostId()
                +" Item id "+item.getId()+"body "+item.getBody()+"------"));

        assertTrue("One of the body in response does not contain \"non\"",filteredComments.stream().allMatch(comments->comments.getBody().contains("non")));
        assertTrue("One of the postId isn't equal to 1", filteredComments.stream().allMatch(comments -> comments.getPostId()==1));
        System.out.println("done");
    }


}
