package com.mrengineer13.contact_congress.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jon on 6/28/16.
 */
public class Legislator extends RealmObject implements Parcelable {
    @SerializedName("bioguide_id")
    @Expose
    @PrimaryKey
    public String bioguideId;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("chamber")
    @Expose
    public String chamber;
    @SerializedName("contact_form")
    @Expose
    public String contactForm;
    @SerializedName("crp_id")
    @Expose
    public String crpId;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("facebook_id")
    @Expose
    public String facebookId;
    @SerializedName("fax")
    @Expose
    public String fax;
    @SerializedName("fec_ids")
    @Expose
    @Ignore
    public List<String> fecIds = new ArrayList<String>();
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("govtrack_id")
    @Expose
    public String govtrackId;
    @SerializedName("icpsr_id")
    @Expose
    public Integer icpsrId;
    @SerializedName("in_office")
    @Expose
    public Boolean inOffice;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("middle_name")
    @Expose
    public String middleName;
    @SerializedName("name_suffix")
    @Expose
    public String nameSuffix;
    @SerializedName("nickname")
    @Expose
    public String nickname;
    @SerializedName("oc_email")
    @Expose
    public String email;
    @SerializedName("ocd_id")
    @Expose
    public String ocdId;
    @SerializedName("office")
    @Expose
    public String office;
    @SerializedName("party")
    @Expose
    public String party;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("state_name")
    @Expose
    public String stateName;
    @SerializedName("term_end")
    @Expose
    public String termEnd;
    @SerializedName("term_start")
    @Expose
    public String termStart;
    @SerializedName("thomas_id")
    @Expose
    public String thomasId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("twitter_id")
    @Expose
    public String twitterId;
    @SerializedName("votesmart_id")
    @Expose
    public Integer votesmartId;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("youtube_id")
    @Expose
    public String youtubeId;
    @SerializedName("lis_id")
    @Expose
    public String lisId;
    @SerializedName("senate_class")
    @Expose
    public Integer senateClass;
    @SerializedName("state_rank")
    @Expose
    public String stateRank;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bioguideId);
        dest.writeString(this.birthday);
        dest.writeString(this.chamber);
        dest.writeString(this.contactForm);
        dest.writeString(this.crpId);
        dest.writeString(this.district);
        dest.writeString(this.facebookId);
        dest.writeString(this.fax);
        dest.writeStringList(this.fecIds);
        dest.writeString(this.firstName);
        dest.writeString(this.gender);
        dest.writeString(this.govtrackId);
        dest.writeValue(this.icpsrId);
        dest.writeValue(this.inOffice);
        dest.writeString(this.lastName);
        dest.writeString(this.middleName);
        dest.writeString(this.nameSuffix);
        dest.writeString(this.nickname);
        dest.writeString(this.email);
        dest.writeString(this.ocdId);
        dest.writeString(this.office);
        dest.writeString(this.party);
        dest.writeString(this.phone);
        dest.writeString(this.state);
        dest.writeString(this.stateName);
        dest.writeString(this.termEnd);
        dest.writeString(this.termStart);
        dest.writeString(this.thomasId);
        dest.writeString(this.title);
        dest.writeString(this.twitterId);
        dest.writeValue(this.votesmartId);
        dest.writeString(this.website);
        dest.writeString(this.youtubeId);
        dest.writeString(this.lisId);
        dest.writeValue(this.senateClass);
        dest.writeString(this.stateRank);
    }

    public Legislator() {
    }

    protected Legislator(Parcel in) {
        this.bioguideId = in.readString();
        this.birthday = in.readString();
        this.chamber = in.readString();
        this.contactForm = in.readString();
        this.crpId = in.readString();
        this.district = in.readString();
        this.facebookId = in.readString();
        this.fax = in.readString();
        this.fecIds = in.createStringArrayList();
        this.firstName = in.readString();
        this.gender = in.readString();
        this.govtrackId = in.readString();
        this.icpsrId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.inOffice = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.lastName = in.readString();
        this.middleName = in.readString();
        this.nameSuffix = in.readString();
        this.nickname = in.readString();
        this.email = in.readString();
        this.ocdId = in.readString();
        this.office = in.readString();
        this.party = in.readString();
        this.phone = in.readString();
        this.state = in.readString();
        this.stateName = in.readString();
        this.termEnd = in.readString();
        this.termStart = in.readString();
        this.thomasId = in.readString();
        this.title = in.readString();
        this.twitterId = in.readString();
        this.votesmartId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.website = in.readString();
        this.youtubeId = in.readString();
        this.lisId = in.readString();
        this.senateClass = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stateRank = in.readString();
    }

    public static final Parcelable.Creator<Legislator> CREATOR = new Parcelable.Creator<Legislator>() {
        @Override
        public Legislator createFromParcel(Parcel source) {
            return new Legislator(source);
        }

        @Override
        public Legislator[] newArray(int size) {
            return new Legislator[size];
        }
    };
}
