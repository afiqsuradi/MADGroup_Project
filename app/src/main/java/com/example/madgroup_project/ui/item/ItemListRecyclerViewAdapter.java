package com.example.madgroup_project.ui.item;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.models.Lab;

import java.util.List;
import java.util.Objects;

public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ItemListViewHolder> {
    List<Item> items;
    private Fragment fragment;

    public ItemListRecyclerViewAdapter(List<Item> items, Fragment fragment) {
        this.fragment = fragment;
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
        final Item item = items.get(position);
        holder.tvItemName.setText(item.getName());
        holder.tvItemSerialNo.setText(item.getSerialNumber());
        holder.tvItemType.setText(item.getType().toString());
        holder.tvItemCondition.setText(item.getCondition().toString());
        holder.cvItemCard.setOnClickListener(v -> showBottomDialog(holder.itemView.getContext(), item));
        holder.btnShowDetails.setOnClickListener(v -> showBottomDialog(holder.itemView.getContext(), item));
        switch (item.getCondition()){
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

    private void showBottomDialog(Context context, Item item) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_details_bottom_sheet);

        Button btnCancelItemSheet = dialog.findViewById(R.id.btnCancelItemSheet);
        Button btnEditItem = dialog.findViewById(R.id.btnEditItem);
        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
        TextView tvItemSerialNo = dialog.findViewById(R.id.tvItemSerialNo);
        TextView tvItemType = dialog.findViewById(R.id.tvItemType);
        TextView tvItemCondition = dialog.findViewById(R.id.tvItemCondition);

        tvItemName.setText(item.getName());
        tvItemSerialNo.setText(item.getSerialNumber());
        tvItemType.setText(item.getType().toString());
        tvItemCondition.setText(item.getCondition().toString());

        btnCancelItemSheet.setOnClickListener(v -> dialog.dismiss());
        btnEditItem.setOnClickListener(v -> {
            dialog.dismiss();
            navigateToItemEditActivity(item);
        });
        dialog.show();
        setupDialogWindow(dialog);
    }

    private void navigateToItemEditActivity(Item item) {
        if (fragment != null && fragment.getActivity() != null) {
            Context context = fragment.requireContext();
            Intent intent = new Intent(context, ItemEditActivity.class);
            intent.putExtra("item_id", item.getId());
            intent.putExtra("lab_id", item.getLabId());
            context.startActivity(intent);
        }
    }

    private void setupDialogWindow(Dialog dialog){
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public class ItemListViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvItemName, tvItemSerialNo, tvItemCondition, tvItemType;
        private CardView cvItemCondition, cvItemCard;

        private ImageButton btnShowDetails;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemSerialNo = itemView.findViewById(R.id.tvItemSerialNo);
            tvItemCondition = itemView.findViewById(R.id.tvItemCondition);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            cvItemCondition = itemView.findViewById(R.id.cvItemCondition);
            cvItemCard = itemView.findViewById(R.id.cvItemCard);
            btnShowDetails = itemView.findViewById(R.id.btnShowDetails);
        }
    }
}
