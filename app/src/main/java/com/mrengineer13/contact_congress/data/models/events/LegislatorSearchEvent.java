package com.mrengineer13.contact_congress.data.models.events;

/**
 * Created by Jon on 6/30/16.
 */
public class LegislatorSearchEvent {

    public String query;

    public LegislatorSearchEvent(String query) {
        this.query = query;
    }
}
