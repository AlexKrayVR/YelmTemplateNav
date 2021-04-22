package yelm.io.template.fragments.stock.image_slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import yelm.io.template.databinding.ItemSliderImageBinding;
import yelm.io.template.stuff.Logging;


public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> items;
    private Context context;

    private ViewPager2 viewPager2;

    public SliderAdapter(List<SliderItem> items, Context context, ViewPager2 viewPager2) {
        this.items = items;
        this.context = context;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(ItemSliderImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(items.get(position));
        if (position == items.size() - 2) {
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        private ItemSliderImageBinding binding;

        public SliderViewHolder(ItemSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setImage(SliderItem sliderItem) {
            binding.image.setImageResource(sliderItem.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Logging.logDebug("items.addAll(items)");

            items.addAll(items);
            notifyDataSetChanged();
        }
    };

}