package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.ceit.resa.contract.DashboardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.UserProfile;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.BoardUtil;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.UserProfileUtil;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import ir.ceit.resa.view.activity.CreateBoardActivity;
import ir.ceit.resa.view.activity.LoginActivity;
import ir.ceit.resa.view.activity.SearchBoardActivity;
import ir.ceit.resa.view.activity.UserProfileActivity;
import ir.ceit.resa.view.activity.UserSettingActivity;
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
        this.userProfile = UserProfileUtil.createUserProfile(context);
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
        view.showProgressBar();
        getUserJoinedBoardsFromServer();
    }

    @Override
    public void onLogoutClicked() {
        ResaSharedPreferences.clearPreferences(context);
        openLoginActivity();
    }

    @Override
    public void onProfileClicked() {
        openProfileActivity();
    }

    @Override
    public void onSettingsCLicked() {
        openSettingActivity();
    }

    @Override
    public void onSearchClicked() {
        openSearchBoardActivity();
    }

    @Override
    public void onCreateBoardClicked() {
        openCreateBoardActivity();
    }

    @Override
    public void onAdminSettingsClicked() {
        System.out.println("ADMIN SETTINGS CLICKED");
    }

    private void openCreateBoardActivity() {
        Intent intent = new Intent(context, CreateBoardActivity.class);
        context.startActivity(intent);
    }

    private void openSearchBoardActivity() {
        Intent intent = new Intent(context, SearchBoardActivity.class);
        context.startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    private void openSettingActivity(){
        Intent intent = new Intent(context, UserSettingActivity.class);
        context.startActivity(intent);
    }

    public void getUserJoinedBoardsFromServer() {
        String token = ResaSharedPreferences.getToken(context);
        WebService.getJoinedBoards(token, userProfile.getUsername(), new Callback<List<BoardInfoResponse>>() {
            @Override
            public void onResponse(Call<List<BoardInfoResponse>> call, Response<List<BoardInfoResponse>> response) {
                if (response.isSuccessful()) {
                    List<BoardInfoResponse> joinedBoards = response.body();
                    assert joinedBoards != null;
                    if (joinedBoards.size() == 0) {
                        view.showNoJoinedBoards(Constants.NO_BOARDS_TO_SHOW);
                    } else {
                        view.showJoinedBoards(makeBoardModelFromPayload(joinedBoards));
                    }
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    view.showNoJoinedBoards(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<BoardInfoResponse>> call, Throwable t) {
                view.showNoJoinedBoards(Constants.CONNECTION_PROBLEM);
            }
        });
    }

    public List<Board> makeBoardModelFromPayload(List<BoardInfoResponse> payload) {
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < payload.size(); i++) {
            Board board = BoardUtil.makeBoardFromBoardInfoResponse(payload.get(i));
            boards.add(board);
        }
        Collections.sort(boards);
        return boards;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
