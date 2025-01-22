package com.example.madgroup_project.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.models.Lab;

import java.util.List;

public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ItemListViewHolder> {
    List<Item> items;

    public ItemListRecyclerViewAdapter(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemListRecyclerViewAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item_view, parent, false);
        return new ItemListRecyclerViewAdapter.ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListRecyclerViewAdapter.ItemListViewHolder holder, int position) {
        holder.tvItemName.setText(items.get(position).getName());
        holder.tvItemSerialNo.setText(items.get(position).getSerialNumber());
        holder.tvItemType.setText(items.get(position).getType().toString());
        holder.tvItemCondition.setText(items.get(position).getCondition().toString());
        switch (items.get(position).getCondition()){
            case WORKING:
                holder.cvItemCondition.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.badge_positive));
                break;
            case MAINTENANCE:
                holder.cvItemCondition.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.badge_informative));
                holder.tvItemCondition.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.background));
                break;
            case BROKEN:
                holder.cvItemCondition.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.badge_negative));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ItemListViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvItemName, tvItemSerialNo, tvItemCondition, tvItemType;
        private CardView cvItemCondition;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemSerialNo = itemView.findViewById(R.id.tvItemSerialNo);
            tvItemCondition = itemView.findViewById(R.id.tvItemCondition);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            cvItemCondition = itemView.findViewById(R.id.cvItemCondition);
        }
    }
}
