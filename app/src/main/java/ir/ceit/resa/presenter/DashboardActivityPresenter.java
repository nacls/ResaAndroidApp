package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.controller.UserProfileManager;
import ir.ceit.resa.controller.network.ErrorUtils;
import ir.ceit.resa.controller.network.WebService;
import ir.ceit.resa.controller.storage.ResaSharedPreferences;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.view.activity.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivityPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private Context context;
    private UserProfile userProfile;

    public DashboardActivityPresenter(DashboardContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.userProfile = UserProfileManager.createUserProfile(context);
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
        getUserJoinedBoardsFromServer();
    }

    @Override
    public void onLogoutClicked() {
        ResaSharedPreferences.clearPreferences(context);
        openLoginActivity();
    }

    @Override
    public void onProfileClicked() {
        System.out.println("ADMIN SETTINGS CLICKED");
    }

    @Override
    public void onSettingsCLicked() {
        System.out.println("SETTINGS CLICKED");
    }

    @Override
    public void onSearchClicked() {
        System.out.println("SEARCH CLICKED");
    }

    @Override
    public void onCreateBoardClicked() {
        System.out.println("CREATE BOARD CLICKED");
    }

    @Override
    public void onAdminSettingsClicked() {
        System.out.println("ADMIN SETTINGS CLICKED");
    }

    public void openLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void getUserJoinedBoardsFromServer() {
        String token = ResaSharedPreferences.getToken(context);
        WebService.getJoinedBoards(token, userProfile.getUsername(), new Callback<List<BoardInfoResponse>>() {
            @Override
            public void onResponse(Call<List<BoardInfoResponse>> call, Response<List<BoardInfoResponse>> response) {
                if (response.isSuccessful()) {
                    List<BoardInfoResponse> joinedBoards = response.body();
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<BoardInfoResponse>> call, Throwable t) {
            }
        });
    }
}
