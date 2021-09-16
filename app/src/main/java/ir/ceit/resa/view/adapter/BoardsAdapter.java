package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.model.Board;

public class BoardsAdapter extends
        RecyclerView.Adapter<BoardsAdapter.ViewHolder> {


    private List<Board> boards;

    public BoardsAdapter(List<Board> boards) {
        this.boards = boards;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View boardView = inflater.inflate(R.layout.joined_board_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(boardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int i) {
        Board board = boards.get(i);

        ImageView status = viewHolder.membershipStatus;
        Context context = status.getContext();
        switch (board.getUserMembership()) {
            case WRITER:
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.write_board));
                break;
            case CREATOR:
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.own_board));
                break;
            case REGULAR_MEMBER:
                status.setVisibility(View.GONE);
            default:
                break;
        }

        TextView description = viewHolder.boardDescription;
        description.setText(board.getDescription());

        TextView creator = viewHolder.boardCreator;
        String boardCreator = "بانی برد: " + board.getCreatorUsername();
        creator.setText(boardCreator);

        LinearLayout announcementLayout = viewHolder.announcementLayout;
        if (board.getLatestAnnouncement() != null) {
            TextView announcement = viewHolder.latestAnnouncement;
            announcement.setText(board.getLatestAnnouncement().getMessage());
        } else {
            announcementLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout announcementLayout;
        public ImageView membershipStatus;
        public TextView boardDescription;
        public TextView boardCreator;
        public TextView latestAnnouncement;

        public ViewHolder(View itemView) {
            super(itemView);

            membershipStatus = itemView.findViewById(R.id.membershipIv);
            boardDescription = itemView.findViewById(R.id.boardDescription);
            boardCreator = itemView.findViewById(R.id.boardCreator);
            latestAnnouncement = itemView.findViewById(R.id.announcementTv);
            announcementLayout = itemView.findViewById(R.id.announcementLayout);
        }
    }
}