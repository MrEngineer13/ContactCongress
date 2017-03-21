package com.mrengineer13.contact_congress.congress_search;

import com.mrengineer13.contact_congress.data.api.DataStatusView;
import com.mrengineer13.contact_congress.data.models.LegislatorResults;

/**
 * Created by Jon on 3/20/17.
 */

public interface CongressSearchView extends DataStatusView {

    public void legislatorSearchResults(LegislatorResults results);
}
