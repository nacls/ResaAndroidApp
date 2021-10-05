package ir.ceit.resa.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.ConfigureBoardActivityPresenter;

public class ConfigureBoardActivity extends AppCompatActivity implements ConfigureBoardContract.View {

    private ConfigureBoardActivityPresenter configurePresenter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_board);

        Board board = (Board) getIntent().getSerializableExtra("board");

        System.out.println("IS BOARD GIVEN TO COFIGURE NULL? "+ board);

        configurePresenter = new ConfigureBoardActivityPresenter(this, this, board);

        configurePresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();


    }

    @Override
    public void disableEditBoardButton() {

    }

    @Override
    public void updateBoardConfiguration() {

    }

    @Override
    public void onBoardDeleted() {

    }

    public void initializeViewComponents() {
        toolbar = findViewById(R.id.configure_board_toolbar);


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_board));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        returnResult();
    }

    public void returnResult() {
        Intent returnIntent = new Intent();
        System.out.println("IS BOARD PUT IN INTENT NULL? "+ configurePresenter.getBoard());
        returnIntent.putExtra("board", configurePresenter.getBoard());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}