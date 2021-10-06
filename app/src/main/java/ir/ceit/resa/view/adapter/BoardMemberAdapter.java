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

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.view.activity.BoardActivity;

public class BoardMemberAdapter extends
        RecyclerView.Adapter<BoardMemberAdapter.ViewHolder> {

    private List<BoardMemberResponse> members;

    public BoardMemberAdapter(List<BoardMemberResponse> members) {
        this.members = members;
    }

    @NonNull
    @NotNull
    @Override
    public BoardMemberAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View membersView = inflater.inflate(R.layout.board_member_item, viewGroup, false);

        return new ViewHolder(membersView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BoardMemberAdapter.ViewHolder viewHolder, int i) {
        BoardMemberResponse member = members.get(i);

        ImageView status = viewHolder.membershipStatus;
        Context context = status.getContext();
        switch (member.getMembership()) {
            case WRITER:
                status.setBackground(ContextCompat.getDrawable(context, R.drawable.pen));
                break;
            case CREATOR:
                status.setBackground(ContextCompat.getDrawable(context, R.drawable.creator_icon));
                break;
            case REGULAR_MEMBER:
                status.setVisibility(View.INVISIBLE);
            default:
                break;
        }

        TextView description = viewHolder.fullName;
        description.setText(member.getFullName());

        TextView username = viewHolder.username;
        username.setText(member.getUsername());

        viewHolder.parentLayout.setOnClickListener(view -> {
            System.out.println("USER CLICKED "+ viewHolder.username);
        });
    }


    @Override
    public int getItemCount() {
        return members.size();
    }


    public void clear() {
        members.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<BoardMemberResponse> list) {
        members.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parentLayout;
        public ImageView membershipStatus;
        public TextView fullName;
        public TextView username;

        public ViewHolder(View itemView) {
            super(itemView);

            membershipStatus = itemView.findViewById(R.id.boardMembershipIv);
            fullName = itemView.findViewById(R.id.memberFullName);
            username = itemView.findViewById(R.id.memberUsername);
            parentLayout = itemView.findViewById(R.id.member_layout);
        }
    }
}
