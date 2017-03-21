package com.mrengineer13.contact_congress.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jon on 6/28/16.
 */
public class LegislatorResults {

    @SerializedName("results")
    @Expose
    public List<Legislator> legislators = new ArrayList<Legislator>();
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("page")
    @Expose
    public Page page;

}
