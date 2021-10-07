package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.UserSettingContract;

public class UserSettingActivityPresenter implements UserSettingContract.Presenter {

    private UserSettingContract.View view;
    private Context context;

    public UserSettingActivityPresenter(Context context, UserSettingContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void changePasswordClicked(String password) {

    }
}
