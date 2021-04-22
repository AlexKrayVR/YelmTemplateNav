package yelm.io.template.stuff;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import yelm.io.template.R;

public class ItemOffsetDecorationRemoveMarginEnd extends RecyclerView.ItemDecoration {
    Context context;

    public ItemOffsetDecorationRemoveMarginEnd(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == (state.getItemCount() - 1)) {
            ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) view.findViewById(R.id.image).getLayoutParams();
            newLayoutParams.rightMargin = 0;
            view.findViewById(R.id.image).setLayoutParams(newLayoutParams);
        }

        if (parent.getChildAdapterPosition(view) == 0) {
            ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) view.findViewById(R.id.layoutText).getLayoutParams();
            newLayoutParams.leftMargin = (int)context.getResources().getDimension(R.dimen.dimens_20dp);
            view.findViewById(R.id.layoutText).setLayoutParams(newLayoutParams);
        }
    }
}
