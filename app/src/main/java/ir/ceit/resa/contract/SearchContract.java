package ir.ceit.resa.contract;

import android.graphics.drawable.Drawable;

import java.util.List;

import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;

public interface SearchContract {

    interface View {

        void setupActivityView();

        void showProgress();

        void showSearchResult(List<Board> boards);

        void updateBoardMembershipStatusIv(EMembership membership, int boardIndex);

        void showSearchResultProblem(String problem, int statusImage);
    }

    interface Presenter {

        void onCreated();

        void searchButtonClicked(String boardQuery);

        void boardMembershipStatusClicked(EMembership membership, String boardId);
    }
}
