package ir.ceit.resa.presenter;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.ceit.resa.contract.AdminSettingContract;
import ir.ceit.resa.model.ERole;
import ir.ceit.resa.model.payload.request.AddUserRequest;
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
                    email.trim(),
                    UserProfileUtil.getRoleSet(role),
                    password,
                    name.trim(),
                    familyName.trim(),
                    faculty.trim()));
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
                        view.showStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
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
            view.showUsernameError("?????? ???????????? " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (name.trim().isEmpty()) {
            view.showNameError("?????? " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (familyName.trim().isEmpty()) {
            view.showFamilyNameError("?????? ???????????????? " + Constants.CANT_BE_EMPTY);
            isOk = false;
        }
        if (password.length() < 6) {
            view.showPasswordError(Constants.NEW_PASS_IS_TOO_SHORT);
            isOk = false;
        }
        if (email.trim().isEmpty()) {
            view.showEmailError("?????????? " + Constants.CANT_BE_EMPTY);
            isOk = false;
        } else if (!isInEmailFormat(email)) {
            view.showEmailError("?????????? " + Constants.WRONG_EMAIL_FORMAT);
            isOk = false;
        }
        return isOk;
    }

    private boolean isInEmailFormat(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
