package com.example.madgroup_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder> {

    private List<Lab> labList;
    private OnLabClickListener onLabClickListener;

    public LabAdapter(List<Lab> labList, OnLabClickListener listener) {
        this.labList = labList;
        this.onLabClickListener = listener;
    }

    @Override
    public LabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the lab_card_item.xml layout for each RecyclerView item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lab_view, parent, false);
        return new LabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LabViewHolder holder, int position) {
        Lab lab = labList.get(position);
        holder.labName.setText(lab.getName());
        holder.labDescription.setText(lab.getDescription());

        // Set a click listener for each item (card)
        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to start the LabDetailActivity
            Context context = v.getContext();
            Intent intent = new Intent(context, LabDetailActivity.class);

            // Pass the data to the LabDetailActivity
            intent.putExtra("lab_name", lab.getName());
            intent.putExtra("lab_description", lab.getDescription());
            intent.putStringArrayListExtra("lab_equipment", new ArrayList<>(lab.getEquipment()));

            // Start the activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return labList.size();
    }

    public static class LabViewHolder extends RecyclerView.ViewHolder {
        TextView labName, labDescription;

        public LabViewHolder(View itemView) {
            super(itemView);
            labName = itemView.findViewById(R.id.lab_name);
            labDescription = itemView.findViewById(R.id.lab_description);
        }
    }

    public interface OnLabClickListener {
        void onLabClick(Lab lab);
    }
}
