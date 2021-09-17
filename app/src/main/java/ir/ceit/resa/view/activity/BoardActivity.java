package ir.ceit.resa.view.activity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.BoardActivityPresenter;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private BoardActivityPresenter boardPresenter;
    private Toolbar toolbar;
    private Menu toolbarMenu;


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

    private void setViewBasedOnRole() {
        switch (boardPresenter.board.getUserMembership()) {
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