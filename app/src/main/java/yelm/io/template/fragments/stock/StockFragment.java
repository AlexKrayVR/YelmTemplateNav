package yelm.io.template.fragments.stock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import yelm.io.template.R;
import yelm.io.template.databinding.FragmentStockBinding;
import yelm.io.template.fragments.stock.image_slider.SliderAdapter;
import yelm.io.template.fragments.stock.image_slider.SliderItem;
import yelm.io.template.fragments.stock.new_items.NewItem;
import yelm.io.template.fragments.stock.new_items.NewItemsAdapter;
import yelm.io.template.stuff.ItemOffsetDecorationRemoveMarginEnd;

public class StockFragment extends Fragment {

    FragmentStockBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStockBinding.inflate(inflater, container, false);
        binding();

        List<SliderItem> items = new ArrayList<>();
        items.add(new SliderItem(R.drawable.first));
        items.add(new SliderItem(R.drawable.second));
        items.add(new SliderItem(R.drawable.sixth));
        items.add(new SliderItem(R.drawable.fourth));
        items.add(new SliderItem(R.drawable.fifth));
        binding.pager.setAdapter(new SliderAdapter(items, getActivity(), binding.pager));

        ViewPager2.PageTransformer parallax = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position >= -1 && position <= 1) {
                    page.findViewById(R.id.root).setTranslationX(-position * page.getWidth() / 2);
                } else {
                    page.setAlpha(1);
                }
            }
        };
        binding.pager.setPageTransformer(parallax);

        setNewItems();

        return binding.getRoot();
    }


    private void setNewItems() {
        List<NewItem> items = new ArrayList<>();
        items.add(new NewItem(1,R.drawable.first, "Item 1", "Description 1", "122 ₽"));
        items.add(new NewItem(2,R.drawable.second, "Item 2 with long naming sdfhghgrt5 ", "Description 2: long text with something", "4567 ₽"));
        items.add(new NewItem(3,R.drawable.sixth, "Item 3", "Description 3", "466.80 ₽"));
        items.add(new NewItem(4,R.drawable.fourth, "Item 4", "Description 4", "223 ₽"));
        items.add(new NewItem(5,R.drawable.fifth, "Item 5", "Description 5", "79 ₽"));

        items.add(new NewItem(6,R.drawable.first, "Item 1", "Description 1", "122 ₽"));
        items.add(new NewItem(7,R.drawable.second, "Item 2 with long naming sdfhghgrt5 ", "Description 2: long text with something", "4567 ₽"));
        items.add(new NewItem(8,R.drawable.sixth, "Item 3", "Description 3", "466.80 ₽"));
        items.add(new NewItem(9,R.drawable.fourth, "Item 4", "Description 4", "223 ₽"));
        items.add(new NewItem(10,R.drawable.fifth, "Item 5", "Description 5", "79 ₽"));

        items.add(new NewItem(11,R.drawable.first, "Item 1", "Description 1", "122 ₽"));
        items.add(new NewItem(12,R.drawable.second, "Item 2 with long naming sdfhghgrt5 ", "Description 2: long text with something", "4567 ₽"));
        items.add(new NewItem(13,R.drawable.sixth, "Item 3", "Description 3", "466.80 ₽"));
        items.add(new NewItem(14,R.drawable.fourth, "Item 4", "Description 4", "223 ₽"));
        items.add(new NewItem(15,R.drawable.fifth, "Item 5", "Description 5", "79 ₽"));

        items.add(new NewItem(16,R.drawable.first, "Item 1", "Description 1", "122 ₽"));
        items.add(new NewItem(17,R.drawable.second, "Item 2 with long naming sdfhghgrt5 ", "Description 2: long text with something", "4567 ₽"));
        items.add(new NewItem(18,R.drawable.sixth, "Item 3", "Description 3", "466.80 ₽"));
        items.add(new NewItem(19,R.drawable.fourth, "Item 4", "Description 4", "223 ₽"));
        items.add(new NewItem(20,R.drawable.fifth, "Item 5", "Description 5", "79 ₽"));


        binding.recyclerNewItems.setHasFixedSize(true);
        binding.recyclerNewItems.addItemDecoration(new ItemOffsetDecorationRemoveMarginEnd(getContext()));
        binding.recyclerNewItems.setAdapter(new NewItemsAdapter(items, getContext()));

    }

    private void binding() {

    }


}