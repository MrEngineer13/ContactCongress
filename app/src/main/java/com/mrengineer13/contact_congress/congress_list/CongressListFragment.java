package com.mrengineer13.contact_congress.congress_list;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseFragment;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.person_details.PersonDetailsActivity;
import com.mrengineer13.contact_congress.utils.Keys;

import butterknife.BindView;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongressListFragment extends BaseFragment implements CongressListView, CongressListAdapter.OnItemClickedListener {

    @BindView(R.id.bill_list)
    RecyclerView list;

    private CongressListPresenter presenter;

    public CongressListFragment() {
    }

    @Override
    public void onViewInjected(View view) {
        super.onViewInjected(view);

        presenter = new CongressListPresenter(this, realm);
        presenter.getLegislatorsFromDb();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        list.setHasFixedSize(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void gotLegislatorsFromDatabase(RealmResults<Legislator> legislators) {
        CongressListAdapter adapter = new CongressListAdapter(legislators, this);
        list.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_legislator_list;
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