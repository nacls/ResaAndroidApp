package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.SearchContract;
import ir.ceit.resa.model.EMembership;

public class SearchBoardActivityPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private Context context;

    public SearchBoardActivityPresenter(SearchContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void searchButtonClicked(String boardQuery) {
        view.showProgress();
    }

    @Override
    public void boardMembershipStatusClicked(EMembership membership, String boardId) {

    }
}