package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.SplashContract;
import ir.ceit.resa.presenter.SplashActivityPresenter;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    SplashActivityPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashActivityPresenter(this, this);

        splashPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {

    }

    @Override
    public void startProgressThread() {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                    splashPresenter.openLoginActivity();
                    finish();
                } catch (Exception ignored) {
                }
            }
        };
        background.start();
    }
}