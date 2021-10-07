package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.content.Intent;
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
import org.w3c.dom.Text;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.service.SolarCalendar;
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

        return new ViewHolder(boardView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int i) {
        Board board = boards.get(i);


        TextView description = viewHolder.boardDescription;
        description.setText(board.getDescription());

        TextView creator = viewHolder.boardCreator;
        String boardCreator = board.getCreatorUsername();
        creator.setText(boardCreator);

        LinearLayout announcementLayout = viewHolder.announcementLayout;
        if (board.getLatestAnnouncement() != null) {
            TextView announcement = viewHolder.latestAnnouncement;
            TextView announcementDate = viewHolder.latestAnnouncementDate;
            String announcementMessage = board.getLatestAnnouncement().getMessage();
            announcementMessage = announcementMessage.replace("\n", " ");
            if (announcementMessage.length() > 45)
                announcementMessage = board.getLatestAnnouncement().getMessage().substring(0, 45) + "...";

            String lastAnnouncementDate = SolarCalendar.getShamsiDateRtl(board.getLatestAnnouncement().getCreationDate());
            announcement.setText(announcementMessage);
            announcementDate.setText(lastAnnouncementDate);

        } else {
            announcementLayout.setVisibility(View.GONE);
        }

        Context context = announcementLayout.getContext();

        viewHolder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, BoardActivity.class);
            intent.putExtra("board", boards.get(i));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }


    public void clear() {
        boards.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Board> list) {
        boards.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parentLayout;
        public LinearLayout announcementLayout;
        public TextView boardDescription;
        public TextView boardCreator;
        public TextView latestAnnouncement;
        public TextView latestAnnouncementDate;

        public ViewHolder(View itemView) {
            super(itemView);

            boardDescription = itemView.findViewById(R.id.boardDescription);
            boardCreator = itemView.findViewById(R.id.boardCreator);
            latestAnnouncement = itemView.findViewById(R.id.announcementTv);
            latestAnnouncementDate = itemView.findViewById(R.id.announcementDateTv);
            announcementLayout = itemView.findViewById(R.id.announcementLayout);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}