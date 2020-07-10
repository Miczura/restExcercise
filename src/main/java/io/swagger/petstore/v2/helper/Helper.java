package io.swagger.petstore.v2.helper;

import io.restassured.response.Response;
import io.swagger.petstore.v2.pet.Pet;
import utils.Endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Helper {

    public static <T> Response post(String endpoint,T itemToCreate){

        return given()
                    .contentType("application/json")
                    .body(itemToCreate)
                .when()
                    .post(endpoint)
                .then()
                    .extract()
                    .response();
    }

    public static <T> T post(String endpoint,T itemToCreate,Class<T> responseType){
        return given()
                    .contentType("application/json")
                    .body(itemToCreate)
                .when()
                    .post(endpoint)
                .then()
                    .extract()
                    .response()
                    .as(responseType);
    }

    public static void updatePetNameAndStatus(long petId,String name,String status,String endpoint){
                given()
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .pathParam("petId",+petId)
                    .formParam("name",name)
                    .formParam("status",status)
                    .expect()
                    .statusCode(200)
                .when()
                    .post(endpoint+"/{petId}");

    }

    public static Response uploadAnImage(long petId, String additionalMetadata, String fileUrl, String endpoint){
        return given()
                    .header("Content-Type","multipart/form-data")
                    .pathParam("petId",petId)
                    .multiPart("additionalMetadata",additionalMetadata)
                    .multiPart("file",fileUrl)
               .when()
                    .post(endpoint+"/{petId}"+"/uploadImage")
               .then()
                    .extract()
                    .response();
    }

    public static <T> T get(long itemId, String endpoint, Class<T> returnType){
        return given()
                    .contentType("application/json")
                    .pathParam("itemId",itemId)
                .when()
                    .get(endpoint+"/{itemId}")
                .then()
                    .extract()
                    .response()
                    .as(returnType);
    }

    public static Response getAsResponse(long itemId, String endpoint){
        return given()
                    .contentType("application/json")
                    .pathParam("itemId",itemId)
                .when()
                    .get(endpoint+"/{itemId}")
                .then()
                    .extract()
                    .response();

    }

    public static Response getPetsWithDesiredStatus(String status){
        return given()
                    .contentType("application/json")
                    .baseUri("https://petstore.swagger.io/")
                    .basePath("v2/pet"+"/findByStatus")
                    .queryParam("status",status)
                .when()
                    .get()
                .then()
                    .extract()
                    .response();


    }
    public static Response put(String endpoint,Pet updatePayload){
        return given()
                    .contentType("application/json")
                    .body(updatePayload)
                .when()
                    .put(endpoint)
                .then()
                    .extract()
                    .response();
    }
    public static <T> T put(String endpoint,T updatePayload,Class<T> desiredType){
        return given()
                    .contentType("application/json")
                    .body(updatePayload)
                .when()
                    .put(endpoint)
                .then()
                    .extract()
                    .response()
                    .as(desiredType);
    }

    public static Response delete(String endpoint,long petId){
        return given()
                    .contentType("application/json")
                    .pathParam("petId",petId)
                .when()
                    .delete(endpoint+"/{petId}")
                .then()
                    .extract()
                    .response();
    }

    @SafeVarargs
    public static <T> void createTestData(String endpoint, T... itemToBeCreated){
        List<T> list = new ArrayList<>(Arrays.asList(itemToBeCreated));
        for(T p: list){
            given()
                    .contentType("application/json")
                    .body(p)
                    .expect()
                    .statusCode(200)
            .when()
                    .post(endpoint);
        }
    }

    public static void deletePetsOnTeardown(String endpoint,long... itemToDelete){
        for (long s: itemToDelete){

            given()
                    .contentType("application/json")
                    .expect()
                    .statusCode(200)
            .when()
                    .delete(endpoint+"/"+s);
        }
    }
}
