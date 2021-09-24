package ir.ceit.resa.view.activity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import ir.ceit.resa.view.util.RecyclerViewOffsetDecoration;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private BoardActivityPresenter boardPresenter;
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
    public void setupActivityView() {
        initializeViewComponents();
        setUpToolbar();

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        showAnnouncementsLayout.setVisibility(View.GONE);
        showProblemLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoAnnouncements(String status) {
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
        progressBar.setVisibility(View.GONE);
        showProblemLayout.setVisibility(View.GONE);
        showAnnouncementsLayout.setVisibility(View.VISIBLE);
        AnnouncementAdapter adapter = new AnnouncementAdapter(announcements);
        // Attach the adapter to the recyclerview to populate items
        announcementsRv.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        announcementsRv.setLayoutManager(layoutManager);
        // recycler view items offset
        float offsetPx = getResources().getDimension(R.dimen.announcement_offset_dp);
        RecyclerViewOffsetDecoration bottomOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, false, true, (int) offsetPx);
        announcementsRv.addItemDecoration(bottomOffsetDecoration);
        RecyclerViewOffsetDecoration topOffsetDecoration = new RecyclerViewOffsetDecoration((int) offsetPx, true, true, (int) offsetPx);
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
        announcementsRv.scrollToPosition(0);
        setAddAnnouncementViewBasedOnRole();
    }

    private void setAddAnnouncementViewBasedOnRole() {
        EMembership userMembership = boardPresenter.getBoard().getUserMembership();
        switch (userMembership) {
            case CREATOR:
            case WRITER:
                showAnnouncementsLayout.setVisibility(View.VISIBLE);
                addAnnouncementLayout.setVisibility(View.VISIBLE);
                return;
            case REGULAR_MEMBER:
            case NOT_JOINED:
            default:
                addAnnouncementEt.setVisibility(View.GONE);
                final float scale = this.getResources().getDisplayMetrics().density;
                int pixels = (int) (30 * scale + 0.5f);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                params.weight = 1.0f;
                params.gravity = Gravity.TOP;
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
        // add
        addAnnouncementEt = findViewById(R.id.add_announcement_et);
        addAnnouncementIv = findViewById(R.id.add_announcement_iv);
        addAnnouncementLayout = findViewById(R.id.add_announcement_layout);
        // problem
        showProblemLayout = findViewById(R.id.container_announcements_problem);
        announcementProblemIv = findViewById(R.id.announcement_problem_iv);
        announcementProblemTv = findViewById(R.id.announcement_status_tv);
    }

    private void setUpToolbar() {
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