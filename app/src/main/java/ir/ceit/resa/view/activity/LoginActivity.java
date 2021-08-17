package ir.ceit.resa.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.LoginContract;
import ir.ceit.resa.presenter.LoginActivityPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginActivityPresenter loginPresenter;

    private Button submitButton;
    private EditText usernameEt;
    private EditText passwordEt;
    private TextView statusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginActivityPresenter(this, this);

        initializeView();

        registerSubmitButton();
    }

    private void initializeView() {
        submitButton = findViewById(R.id.submitBtn);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);
        statusTv = findViewById(R.id.statusTv);
    }


    private void registerSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStatusTv();
                String username = String.valueOf(usernameEt.getText());
                String password = String.valueOf(passwordEt.getText());
                loginPresenter.onButtonClick(username, password);
            }
        });
    }

    private void clearStatusTv() {
        statusTv.setText("");
        statusTv.setVisibility(View.GONE);
    }

    @Override
    public void setStatusText(String statusText, boolean isError) {
        statusTv.setText(statusText);
        int color;
        if (isError)
            color = ResourcesCompat.getColor(getResources(), R.color.error_red, null);
        else
            color = ResourcesCompat.getColor(getResources(), R.color.success_green, null);
        statusTv.setTextColor(color);
        statusTv.setVisibility(View.VISIBLE);
    }
}