package ir.ceit.resa.contract;

import java.util.List;

import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;

public interface BoardMembersContract {

    interface View {

        void setupActivityView();

        void showBoardMembers(List<BoardMemberResponse> members);

        void showProgressBar();

        void showStatusLayout(String status, int i);

        void showToastStatus(String status, boolean isLong);
    }

    interface Presenter {

        void onCreated();

        void addMemberClicked(String username, EMembership membership);

        void changeMembershipClicked(String username, EMembership membership);
    }
}
