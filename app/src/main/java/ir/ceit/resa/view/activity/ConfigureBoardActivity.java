package ir.ceit.resa.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.presenter.ConfigureBoardActivityPresenter;

public class ConfigureBoardActivity extends AppCompatActivity implements ConfigureBoardContract.View {

    private ConfigureBoardActivityPresenter configurePresenter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_board);

        Board board = (Board) getIntent().getSerializableExtra("board");

        configurePresenter = new ConfigureBoardActivityPresenter(this, this, board);

        configurePresenter.onCreated();
    }

    @Override
    public void setupActivityView() {

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
            }
        });
    }

    public void returnResult() {

        configurePresenter.getBoard().setDescription("عنوان جدید!");
        Intent returnIntent = new Intent();
        returnIntent.putExtra("board", configurePresenter.getBoard());
        setResult(Activity.RESULT_OK);
        finish();
    }
}