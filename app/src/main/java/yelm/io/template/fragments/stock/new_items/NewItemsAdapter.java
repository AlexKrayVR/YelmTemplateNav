package yelm.io.template.fragments.stock.new_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import yelm.io.template.R;
import yelm.io.template.databinding.ItemNewItemsBinding;

public class NewItemsAdapter extends RecyclerView.Adapter<NewItemsAdapter.SliderViewHolder> {

    private List<NewItem> items;
    Context context;

    public NewItemsAdapter(List<NewItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(ItemNewItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        NewItem current = items.get(position);
        holder.binding.name.setText(current.getName());
        holder.binding.name.setLineSpacing(1,1.4f);
        holder.binding.description.setText(current.getDescription());
        holder.binding.description.setLineSpacing(1,1.4f);

        holder.binding.price.setText(current.getPrice());
        holder.binding.image.setImageResource(current.getImageUrl());

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.setMargins(0, (int) context.getResources().getDimension(R.dimen.dimens_20dp), 0, 0);
        holder.binding.name.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, (int) context.getResources().getDimension(R.dimen.dimens_12dp), 0, 0);

        holder.binding.description.setLayoutParams(lp2);
        holder.binding.price.setLayoutParams(lp2);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        private ItemNewItemsBinding binding;

        public SliderViewHolder(ItemNewItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}