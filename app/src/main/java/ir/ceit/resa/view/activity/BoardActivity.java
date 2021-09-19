package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.BoardActivityPresenter;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private BoardActivityPresenter boardPresenter;
    // Toolbar components
    private Toolbar toolbar;
    private Menu toolbarMenu;
    // Layouts (controlling big picture view)
    private ConstraintLayout showAnnouncementsLayout;
    private LinearLayout showProblemLayout;
    private LinearLayout addAnnouncementLayout;
    private ProgressBar progressBar;
    // View items
    private RecyclerView announcementsRv;
    private EditText announcementEt;
    private ImageView announcementProblemIv;
    private TextView announcementProblemTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Board board = (Board) getIntent().getSerializableExtra("board");

        boardPresenter = new BoardActivityPresenter(this, board);

        boardPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setUpToolbar();

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void showNoAnnouncements(String status) {

    }

    @Override
    public void showAnnouncements(List<Announcement> announcements) {

    }

    private void setViewBasedOnRole() {
        switch (boardPresenter.getBoard().getUserMembership()) {
            case CREATOR:
                toolbarMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.edit_board));
                break;
            case WRITER:

                break;
            case REGULAR_MEMBER:

                break;
            default:
                break;
        }
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.board_toolbar);
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