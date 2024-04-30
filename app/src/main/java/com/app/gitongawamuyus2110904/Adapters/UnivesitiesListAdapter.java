/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gitongawamuyus2110904.Models.UniListModel;
import com.app.gitongawamuyus2110904.activities.ThreeDaysForcast;
import com.app.gitongawamuyus2110904.R;

import java.util.List;
public class UnivesitiesListAdapter extends RecyclerView.Adapter<UnivesitiesListAdapter.UniViewHolder> {

    private List<UniListModel> uniList;

    public UnivesitiesListAdapter(List<UniListModel> uniList) {
        this.uniList = uniList;
    }

    @NonNull
    @Override
    public UniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.universities_single_layout, parent, false);
        return new UniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniViewHolder holder, int position) {
        UniListModel uni = uniList.get(position);
        holder.uniName.setText(uni.getNameUni());
        holder.uniLocation.setText(uni.getUniLoctaion());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), ThreeDaysForcast.class);
                intent.putExtra("UNIVERSITY_LOCATION", uni.getUniLoctaion());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return uniList.size();
    }

    static class UniViewHolder extends RecyclerView.ViewHolder {
        TextView uniName;
        CardView cardView;
        TextView uniLocation;

        UniViewHolder(@NonNull View itemView) {
            super(itemView);
            uniName = itemView.findViewById(R.id.UniName);
            uniLocation = itemView.findViewById(R.id.UniLoc);
            cardView = itemView.findViewById(R.id.cardGCU);
        }
    }
}