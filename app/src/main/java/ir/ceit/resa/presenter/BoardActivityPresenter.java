package ir.ceit.resa.presenter;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import ir.ceit.resa.contract.BoardContract;
import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.model.payload.request.CreateAnnouncementRequest;
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
    private boolean writingMode = false;

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

    @Override
    public void onInfoEditClicked() {
        if (board.getUserMembership() == EMembership.CREATOR) {
            System.out.println("EDIT CLICKED");
        } else {
            System.out.println("INFO CLICKED");
        }
    }

    @Override
    public void setWritingMode(boolean inWritingMode) {
        writingMode = inWritingMode;
    }

    @Override
    public void addAnnouncementIvClicked(String message) {
        if (writingMode) {
            sendAnnouncementToServer(message);
        }
    }

    public void sendAnnouncementToServer(String message) {
        String token = ResaSharedPreferences.getToken(context);
        CreateAnnouncementRequest announcementRequest = createAnnouncementRequest(message);
        WebService.addAnnouncementToBoard(token, board.getBoardId(), announcementRequest, new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse serverMessage = response.body();
                    assert serverMessage != null;
                    if (serverMessage.getMessage().equals(Constants.SERVER_RESPONSE_ADD_ANNOUNCEMENT_OK)) {
                        view.showToastAnnouncementStatus(Constants.ADD_ANNOUNCEMENTS_SUCCESSFUL, true);
                        getBoardAnnouncementsFromServer();
                    } else {
                        view.showToastAnnouncementStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                    }
                } else {
                    view.showToastAnnouncementStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                view.showToastAnnouncementStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    public CreateAnnouncementRequest createAnnouncementRequest(String message) {
        CreateAnnouncementRequest announcementRequest = new CreateAnnouncementRequest();
        announcementRequest.setMessage(message);
        String timestamp = new SimpleDateFormat(Constants.DATE_FORMAT).format(Calendar.getInstance().getTime());
        announcementRequest.setCreationDate(timestamp);
        return announcementRequest;
    }

    public void getBoardAnnouncementsFromServer() {
        String token = ResaSharedPreferences.getToken(context);
        WebService.getBoardAnnouncements(token, board.getBoardId(), new Callback<List<Announcement>>() {

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

    public Board getBoard() {
        return board;
    }
}
