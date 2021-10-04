package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import ir.ceit.resa.view.activity.BoardActivity;

public class SearchResultAdapter extends
        RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<Board> boards;

    private String query;

    public SearchResultAdapter(List<Board> boards, String query) {
        this.boards = boards;
        this.query = query;
    }

    @NonNull
    @NotNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View boardView = inflater.inflate(R.layout.search_result_item, viewGroup, false);

        return new SearchResultAdapter.ViewHolder(boardView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchResultAdapter.ViewHolder viewHolder, int i) {
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
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.joined_tick));
                break;
            case NOT_JOINED:
                status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.add_logo));
                break;
            default:
                break;
        }

        TextView description = viewHolder.boardDescription;
        description.setText(board.getDescription());

        TextView boardId = viewHolder.boardId;
        String boardIdWithTag = "@" + board.getBoardId();
        String newString = boardIdWithTag.replaceAll(query, "<font color='#81A89C'>" + query + "</font>");
        boardId.setText(Html.fromHtml(newString));

        TextView creator = viewHolder.boardCreator;
        String boardCreator = board.getCreatorUsername();
        creator.setText(boardCreator);

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
        public ImageView membershipStatus;
        public TextView boardDescription;
        public TextView boardCreator;
        public TextView boardId;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.search_item_parent_layout);
            membershipStatus = itemView.findViewById(R.id.membershipIvSearch);
            boardDescription = itemView.findViewById(R.id.boardDescriptionSearch);
            boardCreator = itemView.findViewById(R.id.boardCreatorSearch);
            boardId = itemView.findViewById(R.id.boardIdSearch);
        }
    }
}