package io.swagger.petstore.v2.pet;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Pet {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;
    public Pet(){

    }

    public static final class Builder {
        private long id;
        private Category category;
        private String name;
        private List<String> photoUrls;
        private List<Tags> tags;
        private String status;

        public Builder id(long id){
            this.id = id;
            return this;
        }
        public Builder category(Category category){
            this.category=category;
            return this;
        }
        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder addPhotoUrl(String... photoUrl){
            this.photoUrls=new ArrayList<String>(Arrays.asList(photoUrl));
            return this;
        }
        public Builder addTags(Tags... tag){
            this.tags=new ArrayList<Tags>(Arrays.asList(tag));
            return this;
        }
        public Builder status(String status){
            this.status=status;
            return this;
        }
        public Pet build(){
            Pet pet = new Pet();
            pet.id=this.id;
            pet.category=this.category;
            pet.name=this.name;
            pet.photoUrls=this.photoUrls;
            pet.tags=this.tags;
            pet.status=this.status;
            return pet;
        }

    }

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }


}
