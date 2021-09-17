package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.controller.SolarCalendar;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.view.activity.BoardActivity;

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
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pen));
                break;
            case CREATOR:
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.key));
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
            String lastAnnouncement =board.getLatestAnnouncement().getMessage() +
                    "\n" +
                    SolarCalendar.getShamsiDate(board.getLatestAnnouncement().getCreationDate());
            announcement.setText(lastAnnouncement);
        } else {
            announcementLayout.setVisibility(View.GONE);
        }

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardActivity.class);
                intent.putExtra("board", boards.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parentLayout;
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
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}