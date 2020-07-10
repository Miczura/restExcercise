package io.swagger.petstore.v2.pet;


import io.qameta.allure.Description;
import io.restassured.response.Response;
import io.swagger.petstore.v2.helper.Helper;
import org.junit.*;

import utils.DeserializerForListsGeneric;
import utils.DeserializerForSingleObjectGeneric;
import utils.Endpoint;
import utils.SaveResponse;
import java.util.List;

import static io.swagger.petstore.v2.helper.Helper.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;


public class PetTest {
    private static final int petIdToPost=10100000;
    private static final int petToGetId =10110000;
    private static final int petToDeleteId=10120000;
    private static final int petToPutId=10130000;
    private static final int petToPostForUpdateNameAndStatus=10140000;
    private static final int petToPostForUploadImage=10150000;

    private static Response response;

    @BeforeClass
    public static void onSetup(){

        Pet petForGetPetTest = new Pet
                .Builder()
                .id(petToGetId)
                .category(new Category(11,"getCategory"))
                .name("Hektor")
                .addPhotoUrl("http://get.get.ep")
                .addTags(new Tags(11,"getTag"))
                .status("available")
                .build();

        Pet petForDeleteTest = new Pet
                .Builder()
                .id(petToDeleteId)
                .category(new Category(12,"deleteCategory"))
                .name("Azor")
                .addPhotoUrl("http://delete.bla")
                .addTags(new Tags(12,"deleteTag"))
                .status("sold")
                .build();

        Pet petForPutTest = new Pet
                .Builder()
                .id(petToPutId)
                .category(new Category(13,"putCategory"))
                .name("Sonia")
                .addPhotoUrl("http://put.put.pp")
                .addTags(new Tags(13,"putTag"))
                .status("available")
                .build();

        Pet petForPostPetUpdateNameTest = new Pet
                .Builder()
                .id(petToPostForUpdateNameAndStatus)
                .category(new Category(14,"updateNameCategory"))
                .name("Szarik")
                .addPhotoUrl("http://updateName.oo")
                .addTags(new Tags(14,"updateNameTag"))
                .status("available")
                .build();

        Pet petForPostPetUploadImageTest = new Pet
                .Builder()
                .id(petToPostForUploadImage)
                .category(new Category(15,"fixedCategory"))
                .name("Cywil")
                .addPhotoUrl("http://fixes")
                .addTags(new Tags(15,"fixed"))
                .status("available")
                .build();

        createTestData(Endpoint.BASE_PET_ENDPOINT,
                petForGetPetTest,petForDeleteTest,
                petForPutTest,petForPostPetUpdateNameTest,
                petForPostPetUploadImageTest);
    }
    @Test
    @Description("Verify status code, name and status of a pet created with post method")
    public void postPetTest(){
        Pet postPetPayload = new Pet
                .Builder()
                .id(petIdToPost)
                .category(new Category(10,"postCategory"))
                .name("Pimpek")
                .addPhotoUrl("http://post.bla")
                .addTags(new Tags(10,"postTag"))
                .status("available")
                .build();

        response= Helper.post(Endpoint.BASE_PET_ENDPOINT,postPetPayload);

        Pet deserializedPet=DeserializerForSingleObjectGeneric.
                deserializeToAnyObject(response,Pet.class);

        Assert.assertEquals("Wrong status code  ",200,response.statusCode());
        Assert.assertEquals("Wrong name of pet","Pimpek", deserializedPet.getName());
        Assert.assertEquals("Wrong status of pet","available", deserializedPet.getStatus());
    }
    @Test
    @Description("Verify if pet name and status has been changed")
    public void postPetUpdateNameTest(){

        updatePetNameAndStatus(petToPostForUpdateNameAndStatus,
                "Pimpek3","available",Endpoint.BASE_PET_ENDPOINT);

        response = getAsResponse(petToPostForUpdateNameAndStatus,Endpoint.BASE_PET_ENDPOINT);

        Pet deserializedPet= get(petToPostForUpdateNameAndStatus,
                Endpoint.BASE_PET_ENDPOINT,Pet.class);

        Assert.assertEquals("Invalid status","available",deserializedPet.getStatus());
        Assert.assertEquals("Invalid name","Pimpek3",deserializedPet.getName());
    }

    @Test
    @Description("Send an image url into pet")
    public void postPetUploadImageTest(){

        response = uploadAnImage(petToPostForUploadImage,"test"
                ,"https://www.ooo.gg",Endpoint.BASE_PET_ENDPOINT);

        Message deserializedMessage=DeserializerForSingleObjectGeneric.
                deserializeToAnyObject(response,Message.class);

        Assert.assertEquals("Wrong status code ",200,deserializedMessage.getCode());
        Assert.assertThat("Message do not contain expected string",deserializedMessage.getMessage(),containsString("test"));
    }
    @Test
    @Description("Get a pet by its id")
    public void getPetByIdTest(){
        response = getAsResponse(petToGetId,Endpoint.BASE_PET_ENDPOINT);

        Pet deserializedPet=get(petToGetId,Endpoint.BASE_PET_ENDPOINT,Pet.class);

        Assert.assertEquals("Id of pet is not valid",petToGetId,deserializedPet.getId());
    }
    @Test
    @Description("Get pets by its status")
    public void getPetByStatusTest(){
        response = getPetsWithDesiredStatus("available");

        List<Pet> deserializedPets= DeserializerForListsGeneric.
                deserializeToList(response,Pet.class);

        Assert.assertThat(deserializedPets.size(),greaterThan(10));
    }

    @Test
    @Description("Update an existing pet")
    public void putPetTest(){
        Pet updatePetPayload = new Pet
                .Builder()
                .id(petToPutId)
                .category(new Category(petToPutId+1,"updated"))
                .name("Updated")
                .addPhotoUrl("http://updated.bla")
                .addTags(new Tags(petToPutId+1,"updated"))
                .status("sold")
                .build();

        response = put(Endpoint.BASE_PET_ENDPOINT,updatePetPayload);

        Pet deserializedPet=DeserializerForSingleObjectGeneric.
                deserializeToAnyObject(response,Pet.class);

        Assert.assertEquals("Category not updated ","updated",deserializedPet.getCategory().getName());
        Assert.assertEquals("Tag is not updated ","updated",deserializedPet.getTags().get(0).getName());
    }

    @Test
    @Description("Delete a pet")
    public void deletePetTest(){
        response = delete(Endpoint.BASE_PET_ENDPOINT,petToDeleteId);

        Message message= DeserializerForSingleObjectGeneric.
                deserializeToAnyObject(response, Message.class);

        Assert.assertEquals("Not valid status code",200,message.getCode());
    }

    @After
    public void collectResponses(){
        SaveResponse.saveResponse(response.getBody().asString(),response.getStatusCode(),response.getHeaders().asList());
    }

    @AfterClass
    public static void onTeardown(){
        deletePetsOnTeardown(Endpoint.BASE_PET_ENDPOINT,10100000,10110000
                ,10130000,10140000,10150000);
    }

}