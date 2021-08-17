package ir.ceit.resa.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.ceit.resa.R;
import ir.ceit.resa.model.UserProfile;

public class DashboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        UserProfile userProfile = data.getParcelable("user_profile");

        System.out.println("maryam: role: " + userProfile.getRole() + ", username: " + userProfile.getUsername());
    }
}