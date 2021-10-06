package ir.ceit.resa.presenter;

import android.content.Context;

import java.util.List;

import ir.ceit.resa.contract.BoardMembersContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardMembersActivityPresenter implements BoardMembersContract.Presenter {

    private BoardMembersContract.View view;
    private Context context;
    private Board board;

    public BoardMembersActivityPresenter(BoardMembersContract.View view, Context context, Board board) {
        this.view = view;
        this.context = context;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
        view.showProgressBar();
        getBoardMembersFromServer();
    }

    @Override
    public void addMemberClicked(String username, EMembership membership) {

    }

    @Override
    public void changeMembershipClicked(String username, EMembership membership) {

    }

    private void getBoardMembersFromServer(){
        String token = ResaSharedPreferences.getToken(context);
        WebService.getBoardMembers(token, board.getBoardId(), new Callback<List<BoardMemberResponse>>() {
            @Override
            public void onResponse(Call<List<BoardMemberResponse>> call, Response<List<BoardMemberResponse>> response) {
                if (response.isSuccessful()) {
                    List<BoardMemberResponse> boardMembers = response.body();
                    assert boardMembers != null;
                    if (boardMembers.size() == 0) {
                        view.showStatusLayout(Constants.NO_SEARCH_RESULT, 1);
                    } else {
                        view.showBoardMembers(boardMembers);
                    }
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    view.showStatusLayout(error.getMessage(), 0);
                }
            }

            @Override
            public void onFailure(Call<List<BoardMemberResponse>> call, Throwable t) {
                view.showStatusLayout(Constants.CONNECTION_PROBLEM, 2);
            }
        });
    }
}
