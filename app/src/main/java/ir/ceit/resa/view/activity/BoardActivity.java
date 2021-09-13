package ir.ceit.resa.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.presenter.BoardActivityPresenter;

public class BoardActivity extends AppCompatActivity implements BoardContract.View {

    private BoardActivityPresenter boardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        boardPresenter = new BoardActivityPresenter(this);

        boardPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {

    }
}