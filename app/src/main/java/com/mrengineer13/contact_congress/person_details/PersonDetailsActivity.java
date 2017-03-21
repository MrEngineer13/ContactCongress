package com.mrengineer13.contact_congress.person_details;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseActivity;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.utils.Keys;

import static com.mrengineer13.contact_congress.person_details.PersonDetailsFragment.PERSON_DETAILS_TAG;
import static com.mrengineer13.contact_congress.person_details.PersonDetailsFragment.create;

public class PersonDetailsActivity extends BaseActivity {

    public Legislator legislator;

    @Override
    public void onViewInjected(Bundle savedInstanceState) {
        super.onViewInjected(savedInstanceState);

        if (savedInstanceState != null) {
            legislator = savedInstanceState.getParcelable(Keys.LEGISLATOR);
        } else {
            legislator = getIntent().getParcelableExtra(Keys.LEGISLATOR);
        }

        addFragment();
    }

    private void addFragment() {
        final FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentByTag(PERSON_DETAILS_TAG);

        if (fragment == null) {
            manager.beginTransaction()
                    .add(R.id.container, create(legislator), PERSON_DETAILS_TAG)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Keys.LEGISLATOR, legislator);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        legislator = savedInstanceState.getParcelable(Keys.LEGISLATOR);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_person_details;
    }
}
