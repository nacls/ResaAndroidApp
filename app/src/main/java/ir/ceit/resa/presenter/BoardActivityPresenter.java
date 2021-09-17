package ir.ceit.resa.presenter;

import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Board;

public class BoardActivityPresenter implements BoardContract.Presenter {

    private BoardContract.View view;
    public Board board;

    public BoardActivityPresenter(BoardContract.View view, Board board) {
        this.view = view;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void onInfoEditClicked() {
        System.out.println("INFO CLICKED");
    }
}
