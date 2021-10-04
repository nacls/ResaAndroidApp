package ir.ceit.resa.view.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOffsetDecoration extends RecyclerView.ItemDecoration {

    private int offset;
    private boolean top;
    private boolean isReverse;
    private int borders;
    private boolean needsBottomOffset;

    public RecyclerViewOffsetDecoration(int bottomOffset, boolean top, boolean isReverse, int borders, boolean needsBottomOffset) {
        this.offset = bottomOffset;
        this.top = top;
        this.isReverse = isReverse;
        this.borders = borders;
        this.needsBottomOffset = needsBottomOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (dataSize <= 0) {
            outRect.set(borders, 0, borders, 0);
        } else {
            if (position == dataSize - 1) {
                if (top) {
                    if (isReverse){
                        outRect.set(borders, offset, borders, 0);
                        return;
                    }
                } else {
                    if (!isReverse && needsBottomOffset) {
                        outRect.set(borders, 0, borders, offset);
                        return;
                    }
                }
            } else if (position == 0) {
                if (top) {
                    if (!isReverse){
                        outRect.set(borders, offset, borders, 0);
                        return;
                    }
                } else {
                    if (isReverse && needsBottomOffset){
                        outRect.set(borders, 0, borders, offset);
                        return;
                    }
                }
            }
        }
        outRect.set(borders, 0, borders, 0);
    }
}