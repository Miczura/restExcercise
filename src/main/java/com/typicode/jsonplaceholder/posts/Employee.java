package com.typicode.jsonplaceholder.posts;

public class Employee {
    private String name;
    private String surname;
    private int userId;
    private int id;
    public Employee(){}
    public Employee(String name,String surname,int userId,int id){
        this.name=name;
        this.surname=surname;
        this.userId=userId;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return getClass().getName()+"{name= "+ this.name+" surname= "+this.surname+" userID= "+this.userId+" id= "+this.id+"]";
    }
}
