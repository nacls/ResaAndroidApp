package ir.ceit.resa.presenter;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivityPresenter implements BoardContract.Presenter {

    private BoardContract.View view;
    private Context context;
    private Board board;

    public BoardActivityPresenter(BoardContract.View view, Context context, Board board) {
        this.view = view;
        this.context = context;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
        view.showProgressBar();
        getBoardAnnouncementsFromServer();
    }

    public void getBoardAnnouncementsFromServer() {
        String token = ResaSharedPreferences.getToken(context);
        WebService.getBoardAnnouncements(token, board.getBoardId(), new Callback<List<Announcement>>(){

            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.isSuccessful()) {
                    List<Announcement> announcements = response.body();
                    assert announcements != null;
                    if (announcements.size() == 0) {
                        view.showNoAnnouncements(Constants.NO_ANNOUNCEMENTS_TO_SHOW);
                    } else {
                        Collections.sort(announcements);
                        view.showAnnouncements(announcements);
                    }
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    view.showNoAnnouncements(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                view.showNoAnnouncements(Constants.CONNECTION_PROBLEM);
            }
        });
    }

    @Override
    public void onInfoEditClicked() {
        System.out.println("INFO CLICKED");
    }

    public Board getBoard() {
        return board;
    }
}
