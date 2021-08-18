package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.controller.storage.ResaSharedPreferences;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.view.activity.LoginActivity;

public class DashboardActivityPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private Context context;
    private UserProfile userProfile;

    public DashboardActivityPresenter(DashboardContract.View view, Context context, UserProfile userProfile) {
        this.view = view;
        this.context = context;
        this.userProfile = userProfile;
    }

    @Override
    public void onLogoutClicked() {
        ResaSharedPreferences.clearPreferences(context);
        openLoginActivity();
    }

    @Override
    public void onProfileClicked() {

    }

    @Override
    public void onSettingsCLicked() {

    }

    public void openLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
