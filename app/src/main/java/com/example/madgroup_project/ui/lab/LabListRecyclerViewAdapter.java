package com.example.madgroup_project.ui.lab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Lab;

import java.util.List;

public class LabListRecyclerViewAdapter extends RecyclerView.Adapter<LabListRecyclerViewAdapter.LabViewHolder> {
    private List<Lab> labs;

    public LabListRecyclerViewAdapter(List<Lab> labs) {
        this.labs = labs;
    }

    public void addLab(Lab lab){
        this.labs.add(lab);
        notifyItemInserted(labs.indexOf(lab));
    }

    public void setLabs(List<Lab> labs){
        this.labs = labs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LabListRecyclerViewAdapter.LabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_lab_view, parent, false);
        return new LabListRecyclerViewAdapter.LabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabListRecyclerViewAdapter.LabViewHolder holder, int position) {
        holder.labNameTextview.setText(labs.get(position).getName());
        holder.labCodeTextview.setText(labs.get(position).getCode());
        holder.labSupervisorTextview.setText(labs.get(position).getSupervisor());
    }

    @Override
    public int getItemCount() {
        return labs == null ? 0 : labs.size();
    }

    public class LabViewHolder extends RecyclerView.ViewHolder{

        private TextView labNameTextview;
        private TextView labCodeTextview;
        private TextView labSupervisorTextview;

        public LabViewHolder(@NonNull View itemView) {
            super(itemView);
            labNameTextview = itemView.findViewById(R.id.labNameTextview);
            labCodeTextview = itemView.findViewById(R.id.labCodeTextview);
            labSupervisorTextview = itemView.findViewById(R.id.labSupervisorTextview);
        }
    }
}