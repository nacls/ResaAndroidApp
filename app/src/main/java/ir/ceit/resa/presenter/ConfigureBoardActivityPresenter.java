package ir.ceit.resa.presenter;

import android.content.Context;

import ir.ceit.resa.contract.ConfigureBoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.request.EditBoardRequest;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.BoardUtil;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigureBoardActivityPresenter implements ConfigureBoardContract.Presenter {

    private ConfigureBoardContract.View view;
    private Context context;
    private Board board;
    private boolean submitInProgress = false;

    public ConfigureBoardActivityPresenter(ConfigureBoardContract.View view, Context context, Board board) {
        this.view = view;
        this.context = context;
        this.board = board;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void onChangeBoardClicked(String title, String category, String faculty) {
        if (submitInProgress) {
            return;
        }

        if (validateOrShowError(title, category)) {
            view.disableEditBoardButton();
            submitInProgress = true;
            view.clearProblemLayouts();
            sendEditBoardRequestToServer(makeEditBoardRequest(title, category, faculty));
            view.showBoardStatus(Constants.REQUEST_SENT_SUCCESSFULLY, false);
        }
    }

    @Override
    public void onDeletedBoardClicked() {
        sendDeleteBoardRequestToServer();
    }

    @Override
    public void onOpenBoardMembersClicked() {

    }

    private void sendDeleteBoardRequestToServer() {
        String token = ResaSharedPreferences.getToken(context);
        WebService.deleteBoard(token, board.getBoardId(), new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    if (messageResponse.getMessage().equals(Constants.BOARD_WAS_DELETED)) {
                        setBoard(null);
                        view.onBoardDeleted();
                    }
                } else {
                    view.showToastStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                view.showToastStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    private void sendEditBoardRequestToServer(EditBoardRequest editBoardRequest) {
        String token = ResaSharedPreferences.getToken(context);
        WebService.editBoard(token, board.getBoardId(), editBoardRequest, new Callback<BoardInfoResponse>() {
            @Override
            public void onResponse(Call<BoardInfoResponse> call, Response<BoardInfoResponse> response) {
                submitInProgress = false;
                view.enableEditBoardButton();
                if (response.isSuccessful()) {
                    view.clearProblemLayouts();
                    BoardInfoResponse boardInfoResponse = response.body();
                    setBoard(BoardUtil.makeBoardFromBoardInfoResponse(boardInfoResponse));
                    view.showBoardStatus(Constants.BOARD_CHANGES_SUBMITTED, false);
                    view.updateBoardConfiguration();
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    view.clearProblemLayouts();
                    if (error == null) {
                        view.showBoardStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                    } else {
                        view.showBoardStatus(error.getMessage(), true);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                submitInProgress = false;
                view.enableEditBoardButton();
                view.clearProblemLayouts();
                view.showBoardStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    private EditBoardRequest makeEditBoardRequest(String boardTitle, String boardCategory, String boardFaculty) {
        return new EditBoardRequest(
                boardTitle,
                boardCategory,
                boardFaculty);
    }

    private boolean validateOrShowError(String boardTitle,
                                        String boardCategory) {
        boolean isOk = true;
        if (!isValid(boardTitle)) {
            showErrorBasedOnField(EFields.TITLE);
            isOk = false;
        }
        if (!isValid(boardCategory)) {
            showErrorBasedOnField(EFields.CATEGORY);
            isOk = false;
        }
        if (!isOk) {
            view.hideGeneralStatusLayout();
        }
        return isOk;
    }

    public void showErrorBasedOnField(EFields field) {
        String error = Constants.CANT_BE_EMPTY;
        switch (field) {
            case TITLE:
                view.showBoardTitleError("عنوان برد " + error);
                break;
            case CATEGORY:
                view.showBoardCategoryError("دسته بندی برد " + error);
                break;
            default:
                break;
        }
    }

    public boolean isValid(String value) {
        return !value.isEmpty();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private enum EFields {
        TITLE,
        CATEGORY
    }
}
