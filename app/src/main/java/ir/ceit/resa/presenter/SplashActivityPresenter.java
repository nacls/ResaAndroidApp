package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import ir.ceit.resa.contract.SplashContract;
import ir.ceit.resa.service.UserProfileManager;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.view.activity.DashboardActivity;
import ir.ceit.resa.view.activity.LoginActivity;

public class SplashActivityPresenter implements SplashContract.Presenter {

    private Context context;

    private SplashContract.View view;

    public SplashActivityPresenter(
            SplashContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreated() {
        if (!isUserAlreadyLoggedIn()) {
            view.startProgressThread();
        } else {
            loadDashboardActivity();
        }
    }

    public boolean isUserAlreadyLoggedIn() {
        String token = ResaSharedPreferences.getToken(context);
        return token != null;
    }

    public void loadDashboardActivity() {
        UserProfile userProfile = UserProfileManager.createUserProfile(context);
        if (userProfile == null) {
            openLoginActivity();
        } else {
            openDashboardActivity(userProfile);
        }
    }

    private void openDashboardActivity(UserProfile userProfile) {
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.putExtra("user_profile", userProfile);
        context.startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
