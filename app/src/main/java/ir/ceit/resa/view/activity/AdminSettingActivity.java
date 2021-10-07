package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.contract.AdminSettingContract;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.presenter.AdminSettingActivityPresenter;

public class AdminSettingActivity extends AppCompatActivity implements AdminSettingContract.View {

    private AdminSettingActivityPresenter adminSettingPresenter;

    private Toolbar toolbar;
    // Username
    private EditText usernameEt;
    private LinearLayout usernameProblemLayout;
    private TextView usernameProblemTv;
    // Name
    private EditText nameEt;
    private LinearLayout nameProblemLayout;
    private TextView nameProblemTv;
    // Family name
    private EditText familyNameEt;
    private LinearLayout familyNameProblemLayout;
    private TextView familyNameProblemTv;
    // Password
    private EditText passwordEt;
    private LinearLayout passwordProblemLayout;
    private TextView passwordProblemTv;
    // Email
    private EditText emailEt;
    private LinearLayout emailProblemLayout;
    private TextView emailProblemTv;
    // Role
    private RadioGroup roleRg;
    // Faculty
    private EditText facultyEt;
    // Submit
    private Button addUserButton;
    private LinearLayout statusLayout;
    private TextView statusTv;
    private ImageView statusIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);

        adminSettingPresenter = new AdminSettingActivityPresenter(this, this);

        adminSettingPresenter.onCreated();
    }

    @Override
    public void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
        clearProblemLayouts();
        registerSubmitButton();
    }

    @Override
    public void showUsernameError(String error) {
        usernameProblemLayout.setVisibility(View.VISIBLE);
        usernameProblemTv.setText(error);
    }

    @Override
    public void showNameError(String error) {
        nameProblemLayout.setVisibility(View.VISIBLE);
        nameProblemTv.setText(error);
    }

    @Override
    public void showFamilyNameError(String error) {
        familyNameProblemLayout.setVisibility(View.VISIBLE);
        familyNameProblemTv.setText(error);
    }

    @Override
    public void showPasswordError(String error) {
        passwordProblemLayout.setVisibility(View.VISIBLE);
        passwordProblemTv.setText(error);
    }

    @Override
    public void showEmailError(String error) {
        emailProblemLayout.setVisibility(View.VISIBLE);
        emailProblemTv.setText(error);
    }

    @Override
    public void showStatus(String status, boolean isError) {
        statusLayout.setVisibility(View.VISIBLE);
        statusTv.setText(status);
        int color;
        if (isError) {
            color = ResourcesCompat.getColor(getResources(), R.color.error_red, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.error_red));
        } else {
            color = ResourcesCompat.getColor(getResources(), R.color.success_green, null);
            statusIv.setBackground(ContextCompat.getDrawable(this, R.drawable.ok));
        }
        statusTv.setTextColor(color);
    }

    @Override
    public void disableSubmitPassButton() {
        addUserButton.setEnabled(false);
    }

    @Override
    public void enableSubmitPassButton() {
        addUserButton.setEnabled(true);
    }

    @Override
    public void clearProblemLayouts() {
        usernameProblemLayout.setVisibility(View.GONE);
        nameProblemLayout.setVisibility(View.GONE);
        familyNameProblemLayout.setVisibility(View.GONE);
        passwordProblemLayout.setVisibility(View.GONE);
        emailProblemLayout.setVisibility(View.GONE);
        statusLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToastStatus(String status, boolean isLong) {
        if (isLong)
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.admin_setting_toolbar);
        // Username
        usernameEt = findViewById(R.id.add_user_username_et);
        usernameProblemLayout = findViewById(R.id.new_username_problem_layout);
        usernameProblemTv = findViewById(R.id.new_username_problem_description);
        // Name
        nameEt = findViewById(R.id.add_user_name_et);
        nameProblemLayout = findViewById(R.id.new_name_problem_layout);
        nameProblemTv = findViewById(R.id.new_name_problem_description);
        // Family name
        familyNameEt = findViewById(R.id.new_user_family_et);
        familyNameProblemLayout = findViewById(R.id.new_family_problem_layout);
        familyNameProblemTv = findViewById(R.id.new_family_problem_description);
        // Password
        passwordEt = findViewById(R.id.new_user_password_et);
        passwordProblemLayout = findViewById(R.id.new_password_problem_layout);
        passwordProblemTv = findViewById(R.id.new_password_problem_description);
        // Email
        emailEt = findViewById(R.id.new_user_email_et);
        emailProblemLayout = findViewById(R.id.new_email_problem_layout);
        emailProblemTv = findViewById(R.id.new_email_problem_description);
        // Roles
        roleRg = findViewById(R.id.new_user_roles);
        // Faculty
        facultyEt = findViewById(R.id.new_user_faculty_et);
        // Submit
        addUserButton = findViewById(R.id.submit_new_user_button);
        statusLayout = findViewById(R.id.add_user_status_layout);
        statusTv = findViewById(R.id.add_user_status_description);
        statusIv = findViewById(R.id.add_user_status_image);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.admin_control));

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void registerSubmitButton() {
        addUserButton.setOnClickListener(v -> notifyPresenterOfAddUser());
    }

    private void notifyPresenterOfAddUser() {
        String username = String.valueOf(usernameEt.getText());
        String name = String.valueOf(nameEt.getText());
        String familyName = String.valueOf(familyNameEt.getText());
        String password = String.valueOf(passwordEt.getText());
        String email = String.valueOf(emailEt.getText());
        String faculty = String.valueOf(facultyEt.getText());
        ERole highestLevelRole = getSelectedRole();

        adminSettingPresenter.onSubmitUserClicked(username,name,familyName,password,email,highestLevelRole, faculty);
    }

    private ERole getSelectedRole() {
        int selectedId = roleRg.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return ERole.ROLE_USER;
        } else {
            RadioButton radioButton = roleRg.findViewById(selectedId);
            switch (radioButton.getId()) {
                case R.id.admin_rb:
                    return ERole.ROLE_ADMIN;
                case R.id.creator_rb:
                    return ERole.ROLE_CREATOR;
                case R.id.regular_rb:
                default:
                    return ERole.ROLE_USER;
            }
        }
    }
}