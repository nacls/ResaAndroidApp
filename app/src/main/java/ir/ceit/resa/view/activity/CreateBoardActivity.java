package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.CreateBoardContract;
import ir.ceit.resa.presenter.CreateBoardActivityPresenter;

public class CreateBoardActivity extends AppCompatActivity implements CreateBoardContract.View {

    private CreateBoardActivityPresenter createBoardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        createBoardPresenter = new CreateBoardActivityPresenter(this, this);

        createBoardPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {

    }
}