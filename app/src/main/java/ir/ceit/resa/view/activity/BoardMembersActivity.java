package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.presenter.BoardMembersActivityPresenter;

public class BoardMembersActivity extends AppCompatActivity implements BoardMembersContract.View {

    private BoardMembersActivityPresenter boardMembersPresenter;

    // Toolbar
    private Toolbar toolbar;

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
        setupToolbar();
    }

    @Override
    public void showBoardMembers(List<BoardMemberResponse> members) {

    }

    @Override
    public void showToastStatus(String status, boolean isLong) {

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.manage_members));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.members_toolbar);
    }
}