package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.UserSettingContract;
import ir.ceit.resa.presenter.UserSettingActivityPresenter;

public class UserSettingActivity extends AppCompatActivity implements UserSettingContract.View {

    private UserSettingActivityPresenter userSettingAPresenter;

    private Toolbar toolbar;
    // Change Password Components
    private EditText oldPasswordEt;
    private EditText newPasswordEt;
    private EditText newPasswordRepeatEt;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        userSettingAPresenter = new UserSettingActivityPresenter(this, this);

        userSettingAPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
        //registerSubmitPasswordButton();
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {

    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.setting_toolbar);
//        oldPasswordEt = findViewById(R.id.old_pass_et);
//        newPasswordEt = findViewById(R.id.new_pass_et);
//        newPasswordRepeatEt = findViewById(R.id.new_pass_repeat_et);
//        changePasswordButton = findViewById(R.id.change_pass_button);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.settings));

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void registerSubmitPasswordButton() {
        changePasswordButton.setOnClickListener(v -> {
            submitPasswordCLicked();
        });
    }

    private void submitPasswordCLicked() {

    }
}