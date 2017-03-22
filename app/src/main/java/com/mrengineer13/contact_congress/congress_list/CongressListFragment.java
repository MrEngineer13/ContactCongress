package com.mrengineer13.contact_congress.congress_list;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.base.BaseFragment;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.data.models.events.UpdateLegislatorsEvent;
import com.mrengineer13.contact_congress.person_details.PersonDetailsActivity;
import com.mrengineer13.contact_congress.utils.Keys;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.BindView;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class CongressListFragment extends BaseFragment implements CongressListView, CongressListAdapter.OnItemClickedListener {

    @BindView(R.id.bill_list)
    RecyclerView list;

    private CongressListPresenter presenter;
    private CongressListAdapter adapter;

    public CongressListFragment() {
    }

    @Override
    public void onViewInjected(View view) {
        super.onViewInjected(view);

        adapter = new CongressListAdapter(this);
        list.setAdapter(adapter);

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
    public void onResume() {
        super.onResume();
        listenForEvents();
    }

    private void listenForEvents() {
        Observable<Object> sharedBus = bus.toObserverable().share();

        sharedBus.ofType(UpdateLegislatorsEvent.class)
                .compose(this.<UpdateLegislatorsEvent>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new Action1<UpdateLegislatorsEvent>() {
                    @Override
                    public void call(UpdateLegislatorsEvent event) {
                        Timber.d("Receive Call");
                        presenter.getLegislatorsFromDb();

                    }
                });

    }

    @Override
    public void gotLegislatorsFromDatabase(RealmResults<Legislator> legislators) {
        Timber.d("Got legs %d", legislators.size());
        adapter.addLegislators(legislators);
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