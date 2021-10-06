package ir.ceit.resa.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.presenter.BoardMembersActivityPresenter;
import ir.ceit.resa.view.adapter.BoardMemberAdapter;
import ir.ceit.resa.view.dialog.AddMemberDialog;
import ir.ceit.resa.view.util.AddMemberDialogListener;
import ir.ceit.resa.view.util.ChangeMemberAccessListener;

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
        setupAddUserLayout();
    }

    @Override
    public void showBoardMembers(List<BoardMemberResponse> members) {
        statusLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        membersLayout.setVisibility(View.VISIBLE);

        adapter.clear();
        adapter.addAll(members);
        membersRv.setVisibility(View.VISIBLE);
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
        if (isLong)
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
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

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        adapter = new BoardMemberAdapter(new ArrayList<>(), new ChangeMemberAccessListener() {
            @Override
            public void removeMemberFromBoardClicked(String username) {
                boardMembersPresenter.changeMembershipClicked(username, EMembership.NOT_JOINED);
            }

            @Override
            public void giveMemberWriterAccessClicked(String username) {
                boardMembersPresenter.changeMembershipClicked(username, EMembership.WRITER);
            }

            @Override
            public void takeWriterAccessFromMemberClicked(String username) {
                boardMembersPresenter.changeMembershipClicked(username, EMembership.REGULAR_MEMBER);
            }
        });
        membersRv.setAdapter(adapter);
        membersRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAddUserLayout() {
        addMemberTv.setOnClickListener(v -> {
            openAddMemberDialog();
        });
    }

    private void openAddMemberDialog() {
        AddMemberDialog addMemberDialog = new AddMemberDialog(this, new AddMemberDialogListener() {
            @Override
            public void addUserClicked(String username, boolean isWriter) {
                if (isWriter)
                    boardMembersPresenter.changeMembershipClicked(username, EMembership.WRITER);
                else
                    boardMembersPresenter.changeMembershipClicked(username, EMembership.REGULAR_MEMBER);

            }
        });
        addMemberDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMemberDialog.show();
    }
}