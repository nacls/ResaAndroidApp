package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.CreateBoardContract;
import ir.ceit.resa.presenter.CreateBoardActivityPresenter;

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}