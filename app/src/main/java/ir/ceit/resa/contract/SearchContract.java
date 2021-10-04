package ir.ceit.resa.contract;

import java.util.List;

import ir.ceit.resa.model.Board;

public interface SearchContract {

    interface View {

        void setupActivityView();

        void showProgress();

        void showSearchResult(List<Board> boards);

        void showSearchResultProblem(String problem, int statusImage);
    }

    interface Presenter {

        void onCreated();

        void searchButtonClicked(String boardQuery);
    }
}
