package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.view.util.ChangeMemberAccessListener;

public class BoardMemberAdapter extends
        RecyclerView.Adapter<BoardMemberAdapter.ViewHolder> {

    private List<BoardMemberResponse> members;

    private ChangeMemberAccessListener listener;


    public BoardMemberAdapter(List<BoardMemberResponse> members, ChangeMemberAccessListener listener) {
        this.members = members;
        this.listener = listener;
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
                status.setVisibility(View.VISIBLE);
                status.setBackground(ContextCompat.getDrawable(context, R.drawable.pen));
                break;
            case CREATOR:
                status.setVisibility(View.VISIBLE);
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
        String usernameWithAtSign = "@"+member.getUsername();
        username.setText(usernameWithAtSign);

        viewHolder.parentLayout.setOnClickListener(v -> showPopupMenu(context, v, member));

    }

    private void showPopupMenu(Context context, View v, BoardMemberResponse member) {
        PopupMenu popup = new PopupMenu(context, v);

        switch (member.getMembership()) {
            case REGULAR_MEMBER:
                popup.inflate(R.menu.memebr_popup_menu);
                break;
            case WRITER:
                popup.inflate(R.menu.writer_popup_menu);
                break;
            case CREATOR:
            default:
                return;
        }

        popup.show();

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.take_writer_access:
                    listener.takeWriterAccessFromMemberClicked(member.getUsername());
                    return true;
                case R.id.give_writer_access:
                    listener.giveMemberWriterAccessClicked(member.getUsername());
                    return true;
                case R.id.remove_user_from_board:
                    listener.removeMemberFromBoardClicked(member.getUsername());
                    return true;
                default:
                    return false;
            }
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
