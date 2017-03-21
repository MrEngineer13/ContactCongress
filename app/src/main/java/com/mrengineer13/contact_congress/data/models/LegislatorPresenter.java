package com.mrengineer13.contact_congress.data.models;

import android.content.Context;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.utils.LegislatorImageUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jon on 8/13/16.
 */

public class LegislatorPresenter {

    private final String title;
    private final String suffix;
    private final String nickname;
    private final String firstName;
    private final String lastName;
    private final String bioguideId;
    private final String phone;
    private final String email;
    private final String party;
    private final String chamber;
    private final String district;
    private final String stateName;

    public LegislatorPresenter(Legislator legislator) {
        title = legislator.title;
        suffix = legislator.nameSuffix;
        nickname = legislator.nickname;
        firstName = legislator.firstName;
        lastName = legislator.lastName;
        bioguideId = legislator.bioguideId;
        phone = legislator.phone;
        email = legislator.email;
        party = legislator.party;
        district = legislator.district;
        chamber = legislator.chamber;
        stateName = legislator.stateName;
    }

    public String getSummary(){
        return String.format("%s %s", partyName(), getOnlyPosition());
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return firstName() + " " + lastName;
    }

    public String firstName() {
        if (nickname != null && nickname.length() > 0)
            return nickname;
        else
            return firstName;
    }

    public String titledName() {
        String name = title + ". " + getName();
        if (suffix != null && !suffix.equals(""))
            name += ", " + suffix;
        return name;
    }

    public String fullTitle() {
        String title = this.title;
        if (title.equals("Del"))
            return "Delegate";
        else if (title.equals("Com"))
            return "Resident Commissioner";
        else if (title.equals("Sen"))
            return "Senator";
        else // "Rep"
            return "Representative";
    }

    public String partyName() {
        if (party.equals("D"))
            return "Democrat";
        if (party.equals("R"))
            return "Republican";
        if (party.equals("I"))
            return "Independent";
        else
            return "";
    }

    public String getPosition() {
        String position = "";

        if (this.chamber.equals("senate"))
            position = "Senator from " + stateName;
        else if (district != null && district.equals("0")) {
            if (title.equals("Rep"))
                position = "Representative for " + stateName + " At-Large";
            else
                position = fullTitle() + " for " + stateName;
        } else
            position = "Representative for " + stateName + "-" + district;

        return "(" + party + ") " + position;
    }

    public String getOnlyPosition() {
        String position = "";

        if (this.chamber.equals("senate"))
            position = "Senator from " + stateName;
        else if (district != null && district.equals("0")) {
            if (title.equals("Rep"))
                position = "Representative for " + stateName + " At-Large";
            else
                position = fullTitle() + " for " + stateName;
        } else
            position = "Representative for " + stateName + "-" + district;

        return  position;
    }
}
