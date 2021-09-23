package ir.ceit.resa.view.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;
    private boolean top;
    private boolean isReverse;

    public RecyclerViewOffsetDecoration(int bottomOffset, boolean top, boolean isReverse) {
        this.offset = bottomOffset;
        this.top = top;
        this.isReverse = isReverse;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (dataSize <= 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (position == dataSize - 1) {
                if (top) {
                    if (isReverse) {
                        outRect.set(0, offset, 0, 0);
                    } else {
                        outRect.set(0, 0, 0, 0);
                    }
                } else {
                    if (isReverse) {
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, 0, 0, offset);
                    }
                }
            } else if (position == 0) {
                if (top) {
                    if (isReverse) {
                        outRect.set(0, 0, 0, 0);
                    } else {
                        outRect.set(0, offset, 0, 0);
                    }
                } else {
                    if (isReverse) {
                        outRect.set(0, 0, 0, offset);
                    } else {
                        outRect.set(0, 0, 0, 0);
                    }
                }
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }
}