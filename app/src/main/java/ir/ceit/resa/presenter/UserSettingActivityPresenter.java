package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.UserSettingContract;
import ir.ceit.resa.model.payload.request.ChangePasswordRequest;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void changePasswordClicked(String oldPassword, String newPass, String newPassRepeat) {
        if (validatePasswordsEntered(newPass, newPassRepeat)) {
            view.disableSubmitPassButton();
            sendChangePassRequestToServer(new ChangePasswordRequest(newPass));
        }
    }

    public void sendChangePassRequestToServer(ChangePasswordRequest changePasswordRequest) {
        String token = ResaSharedPreferences.getToken(context);
        WebService.changePassword(token, changePasswordRequest, new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                view.enableSubmitPassButton();
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    if (messageResponse.getMessage().equals(Constants.PASSWORD_CHANGED)) {
                        view.showChangePassStatus(Constants.YOUR_PASS_WAS_CHANGED, false);
                    } else {
                        view.showChangePassStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                    }
                } else {
                    view.showChangePassStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                view.enableSubmitPassButton();
                view.showChangePassStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    private boolean validatePasswordsEntered(String newPass, String newPassRepeated) {
        // validate old pass
        if (newPass.equals(newPassRepeated)) {
            if (newPass.length() < 6){
                view.showNewPassError(Constants.NEW_PASS_IS_TOO_SHORT);
                return false;
            }
            return true;
        } else {
            view.showNewPassError(Constants.REPEATED_PASSWORD_IS_WRONG);
            return false;
        }
    }
}
