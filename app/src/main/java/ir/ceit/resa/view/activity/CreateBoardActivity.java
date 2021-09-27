package ir.ceit.resa.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.CreateBoardContract;
import ir.ceit.resa.presenter.CreateBoardActivityPresenter;
import ir.ceit.resa.view.dialog.ExitDialog;

public class CreateBoardActivity extends AppCompatActivity implements CreateBoardContract.View {

    private CreateBoardActivityPresenter createBoardPresenter;
    // view
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        createBoardPresenter = new CreateBoardActivityPresenter(this, this);

        createBoardPresenter.onCreated();
    }

    @Override
    public void onBackPressed() {
        openExitDialog();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.create_board_toolbar);
    }

    public void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.create_new_board));

        toolbar.setNavigationOnClickListener(v -> openExitDialog());
    }

    private void openExitDialog() {
        String exitQuestion = getResources().getString(R.string.exit_board_question) + "\n" +
                getResources().getString(R.string.sure_to_exit);

        ExitDialog exitDialog = new ExitDialog(this, exitQuestion);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }
}