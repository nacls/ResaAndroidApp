package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.ceit.resa.contract.LoginContract;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.response.JwtResponse;
import ir.ceit.resa.model.payload.response.LoginError;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.UserProfileUtil;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.NetworkUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import ir.ceit.resa.view.activity.DashboardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Context context;
    private boolean loginInProgress = false;

    public LoginActivityPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void onLoginButtonClick(String username, String password) {
        if (isLoginInProgress()) {
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            view.setStatusText(Constants.CHECK_CONNECTION_TRY_AGAIN, true);
            return;
        }

        if (isUsernameValid(username) && isPasswordValid(password)) {
            setLoginInProgress(true);
            requestLoginAndHandleErrors(new LoginRequest(username, password));
        } else {
            showUserPassError(username, password);
        }
    }

    private void requestLoginAndHandleErrors(LoginRequest loginRequest) {
        WebService.login(loginRequest, new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful()) {
                    JwtResponse jwtResponse = response.body();
                    loadDashboardActivity(jwtResponse);
                    view.setStatusText(Constants.LOGIN_SUCCESSFUL_WAIT, false);
                } else {
                    LoginError error = ErrorUtils.parseLoginError(response);
                    if (error.getStatus() == 401) {
                        view.setStatusText(Constants.USERNAME_OR_PASSWORD_WRONG, true);
                    } else {
                        view.setStatusText(Constants.UNKNOWN_ERROR_TRY_AGAIN, true);
                    }
                    setLoginInProgress(false);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                setLoginInProgress(false);
                view.setStatusText(Constants.CHECK_CONNECTION + " " + Constants.TRY_AGAIN, true);
            }
        });
    }

    private void loadDashboardActivity(JwtResponse jwtResponse) {
        saveValuesToSharedPreferences(jwtResponse);
        UserProfile userProfile = UserProfileUtil.createUserProfile(context);
        if (userProfile == null) {
            view.setStatusText(Constants.PROBLEM_OCCURRED_DURING_LOGIN, true);
            setLoginInProgress(false);
        } else {
            openDashboardActivity();
        }
    }

    private void saveValuesToSharedPreferences(JwtResponse jwtResponse) {
        String token = jwtResponse.getType() + " " + jwtResponse.getToken();
        ResaSharedPreferences.setToken(context, token);
        ResaSharedPreferences.setUserName(context, jwtResponse.getUsername());
        ResaSharedPreferences.setFirstName(context, jwtResponse.getFirstName());
        ResaSharedPreferences.setLastName(context, jwtResponse.getLastName());
        ResaSharedPreferences.setEmail(context, jwtResponse.getEmail());
        ResaSharedPreferences.setFaculty(context, jwtResponse.getFaculty());
        ResaSharedPreferences.setRole(context, UserProfileUtil.getHighLevelRole(jwtResponse.getRoles()));
    }

    private void openDashboardActivity() {
        Intent intent = new Intent(context, DashboardActivity.class);
        context.startActivity(intent);
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 2 && !containsWhiteSpace(username);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && !containsWhiteSpace(password);
    }

    private void showUserPassError(String username, String password) {
        String passwordError = getPasswordError(password);
        String usernameError = getUsernameError(username);
        String error;
        if (usernameError != null && passwordError != null)
            error = passwordError + " و " + usernameError + ".";
        else if (usernameError != null)
            error = usernameError + ".";
        else if (passwordError != null)
            error = passwordError + ".";
        else
            error = Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN;
        view.setStatusText(error, true);
    }

    private String getPasswordError(String password) {
        if (containsWhiteSpace(password))
            return Constants.PASSWORD_SPACE_ERROR;
        if (password.length() < 6)
            return Constants.PASSWORD_LENGTH_ERROR;
        return null;
    }

    private String getUsernameError(String username) {
        if (containsWhiteSpace(username))
            return Constants.USERNAME_SPACE_ERROR;
        if (username.length() < 2)
            return Constants.USERNAME_LENGTH_ERROR;
        return null;
    }

    private boolean containsWhiteSpace(String string) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }


    public boolean isLoginInProgress() {
        return loginInProgress;
    }

    public void setLoginInProgress(boolean loginInProgress) {
        this.loginInProgress = loginInProgress;
    }
}
