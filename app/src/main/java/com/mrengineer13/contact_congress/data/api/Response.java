package com.mrengineer13.contact_congress.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jon on 3/3/17.
 */
public class Response {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}