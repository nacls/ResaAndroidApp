package ir.ceit.resa.presenter;

import ir.ceit.resa.contract.BoardContract;

public class BoardActivityPresenter implements BoardContract.Presenter {

    private BoardContract.View view;

    public BoardActivityPresenter(BoardContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }
}
