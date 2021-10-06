package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;

public class BoardMembersActivityPresenter implements BoardMembersContract.Presenter {

    private BoardMembersContract.View view;
    private Context context;
    private Board board;

    public BoardMembersActivityPresenter(BoardMembersContract.View view, Context context, Board board) {
        this.view = view;
        this.context = context;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
        getBoardMembersFromServer();
    }

    @Override
    public void addMemberClicked(String username, EMembership membership) {

    }

    @Override
    public void changeMembershipClicked(String username, EMembership membership) {

    }

    private void getBoardMembersFromServer(){

    }
}
