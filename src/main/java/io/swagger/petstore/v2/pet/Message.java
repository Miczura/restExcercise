package io.swagger.petstore.v2.pet;

public class Message {
    private int code;
    private String type;
    private String message;
    public Message(){}

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
