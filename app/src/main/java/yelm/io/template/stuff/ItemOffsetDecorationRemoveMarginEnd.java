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

        ConstraintLayout.LayoutParams imageParams = (ConstraintLayout.LayoutParams) view.findViewById(R.id.image).getLayoutParams();
        if (parent.getChildAdapterPosition(view) == (state.getItemCount() - 1)) {
            imageParams.rightMargin = 0;
        } else {
            imageParams.rightMargin = (int) context.getResources().getDimension(R.dimen.dimens_24dp);
        }
        view.findViewById(R.id.image).setLayoutParams(imageParams);

        ConstraintLayout.LayoutParams layoutTextParams = (ConstraintLayout.LayoutParams) view.findViewById(R.id.layoutText).getLayoutParams();
        if (parent.getChildAdapterPosition(view) == 0) {
            layoutTextParams.leftMargin = (int) context.getResources().getDimension(R.dimen.dimens_20dp);
        } else {
            layoutTextParams.leftMargin = 0;
        }
        view.findViewById(R.id.layoutText).setLayoutParams(layoutTextParams);
    }
}
