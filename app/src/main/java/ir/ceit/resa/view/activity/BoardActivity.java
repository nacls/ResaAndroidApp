package ir.ceit.resa.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.presenter.BoardActivityPresenter;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.view.adapter.AnnouncementAdapter;
import ir.ceit.resa.view.dialog.AssuranceDialog;
import ir.ceit.resa.view.dialog.BoardInfoDialog;
import ir.ceit.resa.view.util.AssuranceDialogListener;
import ir.ceit.resa.view.util.RecyclerViewOffsetDecoration;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private BoardActivityPresenter boardPresenter;

    private ActivityResultLauncher<Intent> configureBoardActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Board board = null;
                        if (data != null) {
                            board = (Board) data.getSerializableExtra("board");
                        }
                        boardPresenter.returnFromConfigureBoardActivity(board);
                    }
                }
            });
    ;
    // Toolbar components
    private Toolbar toolbar;
    private Menu toolbarMenu;
    // Layouts (controlling big picture view)
    private RelativeLayout showAnnouncementsLayout;
    private LinearLayout showProblemLayout;
    private LinearLayout addAnnouncementLayout;
    private ProgressBar progressBar;
    // View items
    private RecyclerView announcementsRv;
    private SwipyRefreshLayout swipeContainer;
    private EditText addAnnouncementEt;
    private ImageView addAnnouncementIv;
    private ImageView announcementProblemIv;
    private TextView announcementProblemTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Board board = (Board) getIntent().getSerializableExtra("board");

        boardPresenter = new BoardActivityPresenter(this, this, board);

        boardPresenter.onCreated();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boardPresenter.getBoardAnnouncementsFromServer();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
        setOnSwipe();
        setupRecyclerView();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        showAnnouncementsLayout.setVisibility(View.GONE);
        showProblemLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoAnnouncements(String status) {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        announcementsRv.setVisibility(View.GONE);
        showAnnouncementsLayout.setVisibility(View.VISIBLE);
        showProblemLayout.setVisibility(View.VISIBLE);
        announcementProblemTv.setText(status);

        if (status.equals(Constants.NO_ANNOUNCEMENTS_TO_SHOW)) {
            announcementProblemIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.empty_board));
            setAddAnnouncementViewBasedOnRole();
        }

        if (status.equals(Constants.CONNECTION_PROBLEM)) {
            addAnnouncementEt.setVisibility(View.GONE);
            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (30 * scale + 0.5f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
            params.weight = 1.0f;
            params.gravity = Gravity.TOP;
            addAnnouncementIv.setLayoutParams(params);
            addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.error_red));
        }
    }

    @Override
    public void showAnnouncements(List<Announcement> announcements) {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        showProblemLayout.setVisibility(View.GONE);
        showAnnouncementsLayout.setVisibility(View.VISIBLE);
        AnnouncementAdapter adapter = new AnnouncementAdapter(announcements);
        // Attach the adapter to the recyclerview to populate items
        announcementsRv.setVisibility(View.VISIBLE);
        announcementsRv.setAdapter(adapter);
        announcementsRv.scrollToPosition(0);
        setAddAnnouncementViewBasedOnRole();
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {
        if (isLong)
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateToolbarTitle() {
        getSupportActionBar().setTitle(boardPresenter.getBoard().getDescription());
    }

    @Override
    public void openBoardInfoDialog(Board board) {
        BoardInfoDialog boardInfoDialog = new BoardInfoDialog(this, board);
        boardInfoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        boardInfoDialog.show();
    }

    @Override
    public void openConfigureBoardActivity(Board board) {
        Intent intent = new Intent(this, ConfigureBoardActivity.class);
        intent.putExtra("board", board);
        configureBoardActivityLauncher.launch(intent);
    }

    @Override
    public void updateMembershipIv(EMembership newMembership) {
        switch (newMembership) {
            case NOT_JOINED:
                addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_logo));
                break;
            case REGULAR_MEMBER:
                addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.joined_tick));
                break;
            default:
                break;
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    private void setAddAnnouncementViewBasedOnRole() {
        EMembership userMembership = boardPresenter.getBoard().getUserMembership();
        switch (userMembership) {
            case CREATOR:
            case WRITER:
                setupAddAnnouncementButton();
                showAnnouncementsLayout.setVisibility(View.VISIBLE);
                addAnnouncementLayout.setVisibility(View.VISIBLE);
                addAnnouncementEt.setVisibility(View.VISIBLE);
                addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_announcement));
                final float scale1 = this.getResources().getDisplayMetrics().density;
                int pixels1 = (int) (30 * scale1 + 0.5f);
                int rightMargin = (int) (5 * scale1 + 0.5f);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pixels1, pixels1);
                params1.gravity = Gravity.CENTER;
                params1.rightMargin = rightMargin;
                addAnnouncementIv.setLayoutParams(params1);
                boardPresenter.setWritingMode(true);
                return;
            case REGULAR_MEMBER:
            case NOT_JOINED:
            default:
                setupAddAnnouncementButton();
                addAnnouncementEt.setVisibility(View.GONE);
                final float scale = this.getResources().getDisplayMetrics().density;
                int pixels = (int) (30 * scale + 0.5f);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                params.weight = 1.0f;
                params.gravity = Gravity.TOP;
                addAnnouncementIv.setVisibility(View.VISIBLE);
                addAnnouncementIv.setLayoutParams(params);
                break;
        }

        if (userMembership == EMembership.REGULAR_MEMBER) {
            addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.joined_tick));
        } else if (userMembership == EMembership.NOT_JOINED) {
            addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_logo));
        } else {
            addAnnouncementIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.error_red));
        }
    }

    private void setupAddAnnouncementButton() {
        addAnnouncementIv.setOnClickListener(v -> {
            if (addAnnouncementIv.getVisibility() == View.VISIBLE) {
                if (addAnnouncementIv.getDrawable().getConstantState() ==
                        ContextCompat.getDrawable(this, R.drawable.add_announcement).getConstantState()) {
                    sendAnnouncementToPresenter();
                } else if (addAnnouncementIv.getDrawable().getConstantState() ==
                        ContextCompat.getDrawable(this, R.drawable.joined_tick).getConstantState()) {
                    openLeaveBoardDialog();
                } else if (addAnnouncementIv.getDrawable().getConstantState() ==
                        ContextCompat.getDrawable(this, R.drawable.add_logo).getConstantState()) {
                    boardPresenter.joinBoardClicked();
                }
            }
        });
    }

    private void openLeaveBoardDialog() {
        String exitQuestion = Constants.BE_SURE_TO_LEAVE_BOARD;

        AssuranceDialog exitDialog = new AssuranceDialog(this, exitQuestion, new AssuranceDialogListener() {
            @Override
            public void onRejectClicked() {

            }

            @Override
            public void onAcceptClicked() {
                boardPresenter.leaveBoardClicked();
            }
        });
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

    private void sendAnnouncementToPresenter() {
        String editTextMessage = addAnnouncementEt.getText().toString();
        if (editTextMessage.isEmpty()) {
            showToastStatus(Constants.ANNOUNCEMENTS_CANT_BE_EMPTY, false);
        } else {
            boardPresenter.addAnnouncementIvClicked(editTextMessage);
            addAnnouncementEt.getText().clear();
        }
    }


    private void setupRecyclerView() {
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        announcementsRv.setLayoutManager(layoutManager);
        // recycler view items offset
        float offsetPx = getResources().getDimension(R.dimen.announcement_offset_dp);
        RecyclerViewOffsetDecoration bottomOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, false, true, (int) offsetPx, false);
        announcementsRv.addItemDecoration(bottomOffsetDecoration);
        RecyclerViewOffsetDecoration topOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, true, true, 0, false);
        announcementsRv.addItemDecoration(topOffsetDecoration);
        // divide items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                announcementsRv.getContext(),
                layoutManager.getOrientation()
        );
        dividerItemDecoration.setDrawable(
                Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.announcement_divider))
        );
        announcementsRv.addItemDecoration(dividerItemDecoration);
    }

    private void setViewBasedOnRole() {
        switch (boardPresenter.getBoard().getUserMembership()) {
            case CREATOR:
                toolbarMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.edit_board));
                break;
            case WRITER:
            case REGULAR_MEMBER:
            default:
                break;
        }
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.board_toolbar);
        // progress
        progressBar = findViewById(R.id.announcement_progress_bar);
        // announcements
        // show
        showAnnouncementsLayout = findViewById(R.id.show_announcement_layout);
        announcementsRv = findViewById(R.id.announcementsRv);
        swipeContainer = findViewById(R.id.swipe_container_announcement);
        // add
        addAnnouncementEt = findViewById(R.id.add_announcement_et);
        addAnnouncementIv = findViewById(R.id.add_announcement_iv);
        addAnnouncementLayout = findViewById(R.id.add_announcement_layout);
        // problem
        showProblemLayout = findViewById(R.id.container_announcements_problem);
        announcementProblemIv = findViewById(R.id.announcement_problem_iv);
        announcementProblemTv = findViewById(R.id.announcement_status_tv);
    }

    private void setOnSwipe() {
        swipeContainer.setOnRefreshListener(direction -> boardPresenter.getBoardAnnouncementsFromServer());
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(boardPresenter.getBoard().getDescription());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.info_icon) {
            boardPresenter.onInfoEditClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_board, menu);
        toolbarMenu = menu;
        setViewBasedOnRole();
        return true;
    }
}