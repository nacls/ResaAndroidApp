package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.presenter.BoardMembersActivityPresenter;
import ir.ceit.resa.view.adapter.AnnouncementAdapter;
import ir.ceit.resa.view.adapter.BoardMemberAdapter;
import ir.ceit.resa.view.util.RecyclerViewOffsetDecoration;

public class BoardMembersActivity extends AppCompatActivity implements BoardMembersContract.View {

    private BoardMembersActivityPresenter boardMembersPresenter;

    // Toolbar
    private Toolbar toolbar;
    // Problem layout
    private LinearLayout statusLayout;
    private ImageView statusIv;
    private TextView statusTv;
    // Progress bar
    private ProgressBar progressBar;
    // Members components
    private LinearLayout membersLayout;
    private TextView addMemberTv;
    private RecyclerView membersRv;

    private BoardMemberAdapter adapter;

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
        setupRecyclerView();
    }

    @Override
    public void showBoardMembers(List<BoardMemberResponse> members) {
        statusLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        membersLayout.setVisibility(View.VISIBLE);

        adapter = new BoardMemberAdapter(members);
        // Attach the adapter to the recyclerview to populate items
        membersRv.setVisibility(View.VISIBLE);
        membersRv.setAdapter(adapter);
        membersRv.scrollToPosition(0);
    }

    @Override
    public void showProgressBar() {
        statusLayout.setVisibility(View.GONE);
        membersLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStatusLayout(String status, int i) {
        membersLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        statusLayout.setVisibility(View.VISIBLE);

        statusTv.setText(status);

        switch (i) {
            case 1: // no members
                statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.no_boards));
                break;
            case 2: // connection problem
                statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.boards_problem));
            default:
                break;
        }
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {

    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.members_toolbar);
        // Status
        statusLayout = findViewById(R.id.member_status_layout);
        statusIv = findViewById(R.id.member_status_iv);
        statusTv = findViewById(R.id.member_status_tv);
        // Progress bar
        progressBar = findViewById(R.id.member_progress_bar);
        // Show members
        membersLayout = findViewById(R.id.members_layout);
        addMemberTv = findViewById(R.id.add_member_tv);
        membersRv = findViewById(R.id.member_result_rv);
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

    private void setupRecyclerView() {
        membersRv.setLayoutManager(new LinearLayoutManager(this));
    }
}