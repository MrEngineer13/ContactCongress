package com.mrengineer13.contact_congress.congress_search;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.SearchFragment;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.data.models.LegislatorResults;
import com.mrengineer13.contact_congress.person_details.PersonDetailsActivity;
import com.mrengineer13.contact_congress.utils.Keys;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongressSearchFragment extends SearchFragment implements CongressSearchView, CongressSearchAdapter.OnItemClickedListener {

    @BindView(R.id.bill_list)
    RecyclerView list;

    @BindView(R.id.empty_message)
    TextView emptyMessage;

    private CongressSearchAdapter congressSearchAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private CongressSearchPresenter presenter;


    public CongressSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewInjected(View view) {
        super.onViewInjected(view);

        presenter = new CongressSearchPresenter(this, api);

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        congressSearchAdapter = new CongressSearchAdapter(this);
        list.setAdapter(congressSearchAdapter);
        emptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void search(String query) {
        this.query = query;
        presenter.searchForLegislators(query);
    }

    @Override
    public void legislatorSearchResults(LegislatorResults results) {
        congressSearchAdapter.replaceLegislator(results.legislators);
        emptyMessage.setVisibility(View.GONE);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void onItemClicked(Legislator legislator) {
        Intent intent = new Intent(getActivity(), PersonDetailsActivity.class);
        intent.putExtra(Keys.LEGISLATOR, legislator);
        startActivity(intent);
    }

    @Override
    public void onCallClicked(Legislator legislator) {
        callLegislator(legislator);
    }

    @Override
    public void onEmailClicked(Legislator legislator) {
        emailLegislator(legislator);
    }
}