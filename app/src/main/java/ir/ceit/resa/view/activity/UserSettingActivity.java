package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.UserSettingContract;
import ir.ceit.resa.presenter.UserSettingActivityPresenter;

public class UserSettingActivity extends AppCompatActivity implements UserSettingContract.View {

    private UserSettingActivityPresenter userSettingAPresenter;

    private Toolbar toolbar;
    // Change Password Components
    // old pass
    private EditText oldPasswordEt;
    private LinearLayout oldPassProblemLayout;
    private TextView oldPassProblemTv;
    // new pass
    private EditText newPasswordEt;
    private EditText newPasswordRepeatEt;
    private LinearLayout newPassProblemLayout;
    private TextView newPassProblemTv;
    // submit
    private Button changePasswordButton;
    private LinearLayout passStatusLayout;
    private TextView passStatusTv;
    private ImageView passStatusIv;

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
        registerSubmitPasswordButton();
        clearProblemLayouts();
    }

    @Override
    public void showOldPassError(String error) {
        oldPassProblemLayout.setVisibility(View.VISIBLE);
        oldPassProblemTv.setText(error);
    }

    @Override
    public void showNewPassError(String error) {
        newPassProblemLayout.setVisibility(View.VISIBLE);
        newPassProblemTv.setText(error);
    }

    @Override
    public void showChangePassStatus(String status, boolean isError) {
        passStatusLayout.setVisibility(View.VISIBLE);
        passStatusTv.setText(status);
        int color;
        if (isError) {
            color = ResourcesCompat.getColor(getResources(), R.color.error_red, null);
            passStatusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.error_red));
        } else {
            color = ResourcesCompat.getColor(getResources(), R.color.success_green, null);
            passStatusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.ok));
        }
        passStatusTv.setTextColor(color);
    }

    @Override
    public void disableSubmitPassButton() {
        changePasswordButton.setEnabled(false);
    }

    @Override
    public void enableSubmitPassButton() {
        changePasswordButton.setEnabled(true);
    }

    @Override
    public void clearProblemLayouts() {
        oldPassProblemLayout.setVisibility(View.GONE);
        newPassProblemLayout.setVisibility(View.GONE);
        passStatusLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {
        if (isLong)
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.setting_toolbar);
        // old pass
        oldPasswordEt = findViewById(R.id.old_pass_et);
        oldPassProblemLayout = findViewById(R.id.old_pass_problem_layout);
        oldPassProblemTv = findViewById(R.id.old_pass_problem_description);
        // new pass
        newPasswordEt = findViewById(R.id.new_pass_et);
        newPasswordRepeatEt = findViewById(R.id.new_pass_repeat_et);
        newPassProblemLayout = findViewById(R.id.new_pass_problem_layout);
        newPassProblemTv = findViewById(R.id.new_pass_problem_description);
        // submit
        changePasswordButton = findViewById(R.id.change_pass_button);
        passStatusLayout = findViewById(R.id.change_pass_status_layout);
        passStatusTv = findViewById(R.id.change_pass_status_description);
        passStatusIv = findViewById(R.id.change_pass_status_image);
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
        clearProblemLayouts();
        String oldPass = String.valueOf(oldPasswordEt.getText());
        String newPass = String.valueOf(newPasswordEt.getText());
        String newPassRepeat = String.valueOf(newPasswordRepeatEt.getText());
        userSettingAPresenter.changePasswordClicked(oldPass, newPass, newPassRepeat);
    }
}