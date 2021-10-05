package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.presenter.BoardMembersActivityPresenter;

public class BoardMembersActivity extends AppCompatActivity implements BoardMembersContract.View {

    private BoardMembersActivityPresenter boardMembersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_members);

        Board board = (Board) getIntent().getSerializableExtra("board");

        boardMembersPresenter = new BoardMembersActivityPresenter(this, this, board);

        boardMembersPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
    }

    @Override
    public void showBoardMembers(List<BoardMemberResponse> members) {

    }

    @Override
    public void showBoardCategoryError(String error) {

    }

    @Override
    public void showToastStatus(String status, boolean isLong) {

    }

    private void initializeViewComponents() {

    }
}