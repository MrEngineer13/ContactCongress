package com.mrengineer13.contact_congress.data.api;

import com.mrengineer13.contact_congress.data.models.LegislatorResults;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jon on 6/23/16.
 */

public interface ApiService {

    @GET("/legislators/locate")
    Observable<LegislatorResults> legislators(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("/legislators/locate")
    Observable<LegislatorResults> legislators(@Query("zip") String zip);

    @GET("/legislators?per_page=all")
    Observable<LegislatorResults> legislatorsSearch(@Query("query") String query);
}
