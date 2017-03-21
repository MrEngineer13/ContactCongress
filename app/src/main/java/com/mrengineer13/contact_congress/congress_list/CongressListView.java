package com.mrengineer13.contact_congress.congress_list;

import com.mrengineer13.contact_congress.data.api.DataStatusView;
import com.mrengineer13.contact_congress.data.models.Legislator;

import io.realm.RealmResults;

/**
 * Created by Jon on 3/20/17.
 */

public interface CongressListView extends DataStatusView {

    public void gotLegislatorsFromDatabase(RealmResults<Legislator> legislators);
}
