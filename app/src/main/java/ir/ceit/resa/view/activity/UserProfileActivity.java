package ir.ceit.resa.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import ir.ceit.resa.R;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.UserProfileUtil;

public class UserProfileActivity extends AppCompatActivity {

    // Toolbar
    private Toolbar toolbar;
    // User Info
    private TextView userName;
    private TextView userFamilyName;
    private TextView username;
    private TextView email;
    private TextView faculty;
    // Avatar
    private ImageView userAvatar;
    private LinearLayout roleDescriptionLayout;
    private TextView roleDescriptionTv;

    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfile = UserProfileUtil.createUserProfile(this);
        validateUserProfile();

        setupActivityView();
    }

    private void setupActivityView() {
        initializeViewComponents();
        setupToolbar();
        setupProfileView();
        setAvatarViewBaseOnRole();
    }

    private void initializeViewComponents() {
        toolbar = findViewById(R.id.profile_toolbar);
        // User Info
        userName = findViewById(R.id.user_name);
        userFamilyName = findViewById(R.id.user_family_name);
        username = findViewById(R.id.user_username);
        email = findViewById(R.id.user_email);
        faculty = findViewById(R.id.user_faculty);
        // Avatar
        userAvatar = findViewById(R.id.avatar_iv);
        roleDescriptionLayout = findViewById(R.id.profile_description_layout);
        roleDescriptionTv = findViewById(R.id.profile_description);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.profile));

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupProfileView() {
        userName.setText(userProfile.getFirstName());
        userFamilyName.setText(userProfile.getLastName());
        username.setText(userProfile.getUsername());
        email.setText(userProfile.getEmail());
        if (userProfile.getFaculty() == null)
            faculty.setText(Constants.NOT_DEFINED);
        else
            faculty.setText(userProfile.getFaculty());
    }

    private void validateUserProfile() {
        if (userProfile == null) {
            finish();
        }
    }

    private void setAvatarViewBaseOnRole() {
        setupAvatarImage();
        showRoleInfo();
    }

    private void setupAvatarImage() {
        switch (userProfile.getRole()) {
            case ROLE_ADMIN:
                userAvatar.setBackground(ContextCompat.getDrawable(this, R.drawable.admin_avatar));
                break;
            case ROLE_CREATOR:
                userAvatar.setBackground(ContextCompat.getDrawable(this, R.drawable.creator_avatar));
                break;
            case ROLE_USER:
            default:
                userAvatar.setBackground(ContextCompat.getDrawable(this, R.drawable.user_avatar));
                break;
        }
    }

    private void showRoleInfo() {
        switch (userProfile.getRole()) {
            case ROLE_ADMIN:
                roleDescriptionLayout.setVisibility(View.VISIBLE);
                roleDescriptionTv.setText(Constants.ADMIN_DESCRIPTION);
                break;
            case ROLE_CREATOR:
                roleDescriptionLayout.setVisibility(View.VISIBLE);
                roleDescriptionTv.setText(Constants.CREATOR_DESCRIPTION);
                break;
            case ROLE_USER:
            default:
                roleDescriptionLayout.setVisibility(View.GONE);
                break;
        }
    }
}