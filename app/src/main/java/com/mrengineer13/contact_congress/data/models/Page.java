package com.mrengineer13.contact_congress.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jon on 6/23/16.
 */
public class Page {
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("page")
    @Expose
    public Integer page;
}
