package com.sharipov.dogs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breeds {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private Message message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


}
