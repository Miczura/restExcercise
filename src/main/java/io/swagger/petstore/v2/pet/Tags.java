package io.swagger.petstore.v2.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tags {
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    public Tags(){

    }

    public Tags(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
