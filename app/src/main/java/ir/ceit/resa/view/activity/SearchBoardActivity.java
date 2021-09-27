package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.SearchContract;
import ir.ceit.resa.presenter.SearchBoardActivityPresenter;

public class SearchBoardActivity extends AppCompatActivity implements SearchContract.View {

    private SearchBoardActivityPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_board);

        searchPresenter = new SearchBoardActivityPresenter(this, this);
    }

    @Override
    public void setupActivityView() {

    }
}