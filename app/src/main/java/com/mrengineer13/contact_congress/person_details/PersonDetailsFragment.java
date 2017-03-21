package com.mrengineer13.contact_congress.person_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.konifar.fab_transformation.FabTransformation;
import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseFragment;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.data.models.LegislatorPresenter;
import com.mrengineer13.contact_congress.utils.Keys;
import com.mrengineer13.contact_congress.utils.LegislatorImageUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PersonDetailsFragment extends BaseFragment {

    public static final String PERSON_DETAILS_TAG = "person_details_tag";

    @BindView(R.id.legislator_title)
    TextView title;

    @BindView(R.id.legislator_details)
    TextView summary;

    @BindView(R.id.legislator_name)
    TextView sponsor;

    @BindView(R.id.toolbar_title)
    TextView legislator_name;

    @BindView(R.id.legislator_avatar)
    CircleImageView sponsorAvatar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar_footer)
    Toolbar bottomNavigation;

    private Legislator legislator;

    public PersonDetailsFragment() {}

    public static PersonDetailsFragment create(Legislator legislator) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Keys.LEGISLATOR, legislator);
        PersonDetailsFragment personDetailsFragment = new PersonDetailsFragment();
        personDetailsFragment.setArguments(bundle);
        return personDetailsFragment;
    }

    @Override
    public void onViewInjected(View view) {
        super.onViewInjected(view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(finishActivityOnClick);
        fab.setOnClickListener(fabClickListener);
        legislator = getArguments().getParcelable(Keys.LEGISLATOR);
        if (legislator == null) {
            return;
        }

        bindLegislator(legislator);
    }

    private void bindLegislator(Legislator legislator) {
        LegislatorPresenter presenter = new LegislatorPresenter(legislator);
        title.setText(presenter.getName());
        sponsor.setText(presenter.fullTitle());
        legislator_name.setText(presenter.getName());
        summary.setText(presenter.getSummary());
        LegislatorImageUtils.setImageView(legislator.bioguideId, LegislatorImageUtils.PIC_SMALL, getActivity(), sponsorAvatar);
    }

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FabTransformation.with(fab)
                    .transformTo(bottomNavigation);
        }
    };

    @OnClick(R.id.action_call)
    public void call() {
        callLegislator(legislator);
    }

    @OnClick(R.id.action_email)
    public void email() {
        emailLegislator(legislator);
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_person_details;
    }
}
