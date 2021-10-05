package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.model.Board;

public class ConfigureBoardActivityPresenter implements ConfigureBoardContract.Presenter {

    private ConfigureBoardContract.View view;
    private Context context;
    private Board board;

    public ConfigureBoardActivityPresenter(ConfigureBoardContract.View view, Context context, Board board) {
        this.view = view;
        this.context = context;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void onChangeBoardClicked() {

    }

    @Override
    public void onDeletedBoardClicked() {

    }

    @Override
    public void onOpenBoardMembersClicked() {

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
