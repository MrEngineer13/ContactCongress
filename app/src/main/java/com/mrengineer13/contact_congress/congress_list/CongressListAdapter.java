package com.mrengineer13.contact_congress.congress_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.data.models.Legislator;
import com.mrengineer13.contact_congress.data.models.LegislatorPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jon on 6/26/16.
 */

public class CongressListAdapter extends RecyclerView.Adapter<CongressListAdapter.ViewHolder> {

    private List<Legislator> data;

    private OnItemClickedListener listener;

    public interface OnItemClickedListener {
        void onItemClicked(Legislator legislator);

        void onCallClicked(Legislator legislator);

        void onEmailClicked(Legislator legislator);
    }

    public CongressListAdapter(List<Legislator> data, OnItemClickedListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public CongressListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_legislator, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CongressListAdapter.ViewHolder holder, int position) {
        Legislator legislator = data.get(position);
        holder.bind(legislator, listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.legislator_details)
        public TextView details;
        @BindView(R.id.legislator_name)
        public TextView name;
        @BindView(R.id.legislator_avatar)
        CircleImageView sponsorAvatar;

        Legislator legislator;

        private OnItemClickedListener listener;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Legislator legislator, OnItemClickedListener listener) {
            LegislatorPresenter presenter = new LegislatorPresenter(legislator);
            this.legislator = legislator;
            this.listener = listener;

            name.setText(presenter.getName());
            details.setText(presenter.getPosition());
            presenter.loadLegislatorImage(sponsorAvatar);
        }

        @OnClick(R.id.action_call)
        public void call() {
            listener.onCallClicked(legislator);
        }

        @OnClick(R.id.action_email)
        public void email() {
            listener.onEmailClicked(legislator);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(legislator);
        }
    }

}


