package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.presenter.ConfigureBoardActivityPresenter;

public class ConfigureBoardActivity extends AppCompatActivity implements ConfigureBoardContract.View {

    private ConfigureBoardActivityPresenter configurePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_board);

        //configurePresenter = new ConfigureBoardActivityPresenter(this, this);
    }

    @Override
    public void setupActivityView() {

    }
}