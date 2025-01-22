package com.example.madgroup_project.ui.lab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.btnLabDetail.setOnClickListener(v -> onCardClicked(v, holder));
        holder.labCardView.setOnClickListener(v -> onCardClicked(v, holder));
    }

    @Override
    public int getItemCount() {
        return labs == null ? 0 : labs.size();
    }

    private void onCardClicked(View v, @NonNull LabListRecyclerViewAdapter.LabViewHolder holder){
        int adapterPosition = holder.getAdapterPosition();
        if(adapterPosition != RecyclerView.NO_POSITION){
            Context context = v.getContext();
            Intent intent = new Intent(context, LabDetailActivity.class);
            Lab lab = labs.get(adapterPosition);
            intent.putExtra("lab_id", lab.getId());
            context.startActivity(intent);

        }
    }

    public class LabViewHolder extends RecyclerView.ViewHolder{

        private TextView labNameTextview;
        private TextView labCodeTextview;
        private TextView labSupervisorTextview;

        private ImageButton btnLabDetail;
        private CardView labCardView;



        public LabViewHolder(@NonNull View itemView) {
            super(itemView);
            labNameTextview = itemView.findViewById(R.id.labNameTextview);
            labCodeTextview = itemView.findViewById(R.id.labCodeTextview);
            labSupervisorTextview = itemView.findViewById(R.id.labSupervisorTextview);
            labCardView = itemView.findViewById(R.id.labCardView);
            btnLabDetail = itemView.findViewById(R.id.btnLabDetail);
        }

    }
}