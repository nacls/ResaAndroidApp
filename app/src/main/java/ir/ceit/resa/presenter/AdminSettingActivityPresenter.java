package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.AdminSettingContract;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.payload.request.AddUserRequest;
import ir.ceit.resa.model.payload.response.LoginError;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.UserProfileUtil;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSettingActivityPresenter implements AdminSettingContract.Presenter {

    private AdminSettingContract.View view;
    private Context context;

    public AdminSettingActivityPresenter(Context context, AdminSettingContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void onSubmitUserClicked(String username,
                                    String name, String familyName,
                                    String password,
                                    String email,
                                    ERole role,
                                    String faculty) {
        view.clearProblemLayouts();
        if (validateAddUserInput(username, name, familyName, password, email)) {
            view.disableSubmitPassButton();
            sendAddUserRequestToServer(new AddUserRequest(username,
                    email,
                    UserProfileUtil.getRoleSet(role),
                    password,
                    name,
                    familyName,
                    faculty));
        }
    }

    private void sendAddUserRequestToServer(AddUserRequest addUserRequest) {
        String token = ResaSharedPreferences.getToken(context);
        WebService.addUser(token, addUserRequest, new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                view.enableSubmitPassButton();
                if (response.isSuccessful()) {
                    MessageResponse serverMessage = response.body();
                    assert serverMessage != null;
                    if (serverMessage.getMessage().equals(Constants.USER_REGISTERED_SUCCESSFULLY)) {
                        view.showStatus(Constants.USER_WAS_ADDED, false);
                    } else {
                        view.showStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN+"HEREE", true);
                    }
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    if (error.getMessage().equals(Constants.EMAIL_IS_TAKEN)) {
                        view.showEmailError(Constants.ENTERED_EMAIL_IS_TAKEN);
                    } else if (error.getMessage().equals(Constants.USERNAME_IS_TAKEN)) {
                        view.showUsernameError(Constants.ENTERED_USERNAME_IS_TAKEN);
                    } else {
                        view.showStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                view.enableSubmitPassButton();
                view.showStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    private boolean validateAddUserInput(String username,
                                         String name, String familyName,
                                         String password,
                                         String email) {
        boolean isOk = true;
        if (username.isEmpty()) {
            view.showUsernameError("نام کاربری " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (name.isEmpty()) {
            view.showNameError("نام " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (familyName.isEmpty()) {
            view.showFamilyNameError("نام خانوادگی " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (password.length() < 6) {
            view.showPasswordError(Constants.NEW_PASS_IS_TOO_SHORT);
            isOk = false;
        }
        if (email.isEmpty()) {
            view.showEmailError("ایمیل " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        return isOk;
    }
}
