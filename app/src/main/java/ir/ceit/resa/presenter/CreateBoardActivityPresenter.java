package ir.ceit.resa.presenter;

import android.content.Context;
import android.content.Intent;

import ir.ceit.resa.contract.CreateBoardContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.request.CreateBoardRequest;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import ir.ceit.resa.service.BoardUtil;
import ir.ceit.resa.service.Constants;
import ir.ceit.resa.service.SolarCalendar;
import ir.ceit.resa.service.network.ErrorUtils;
import ir.ceit.resa.service.network.WebService;
import ir.ceit.resa.service.storage.ResaSharedPreferences;
import ir.ceit.resa.view.activity.BoardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBoardActivityPresenter implements CreateBoardContract.Presenter {

    private CreateBoardContract.View view;
    private Context context;
    private boolean submitInProgress = false;

    public CreateBoardActivityPresenter(CreateBoardContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void submitBoardClicked(String boardId, String boardTitle, String boardCategory, String boardFaculty) {
        if (submitInProgress) {
            return;
        }

        if (validateOrShowError(boardId, boardTitle, boardCategory)) {
            view.disableSubmitButton();
            submitInProgress = true;
            sendCreateBoardRequestToServer(makeCreateBoardRequest(boardId, boardTitle, boardCategory, boardFaculty));
            view.showBoardStatus(Constants.REQUEST_SENT_SUCCESSFULLY, false);
        }
    }

    private CreateBoardRequest makeCreateBoardRequest(String boardId, String boardTitle, String boardCategory, String boardFaculty) {
        return new CreateBoardRequest(boardId,
                boardTitle,
                boardCategory,
                boardFaculty,
                SolarCalendar.getCurrentTimestamp());
    }

    private void sendCreateBoardRequestToServer(CreateBoardRequest createBoardRequest) {
        String token = ResaSharedPreferences.getToken(context);
        WebService.createBoard(token, createBoardRequest, new Callback<BoardInfoResponse>() {
            @Override
            public void onResponse(Call<BoardInfoResponse> call, Response<BoardInfoResponse> response) {
                submitInProgress = false;
                view.enableSubmitButton();
                if (response.isSuccessful()) {
                    view.clearProblemLayouts();
                    BoardInfoResponse boardInfoResponse = response.body();
                    openBoardActivity(BoardUtil.makeBoardFromBoardInfoResponse(boardInfoResponse));
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    if (error == null) {
                        view.clearProblemLayouts();
                        view.showBoardStatus(Constants.UNEXPECTED_PROBLEM_NOTIFY_ADMIN, true);
                    } else if (error.getMessage().equals(Constants.BOARD_ID_TAKEN_ERROR)) {
                        view.clearProblemLayouts();
                        view.showBoardIdError(Constants.BOARD_ID_TAKEN_CHOOSE_ANOTHER);
                    } else {
                        view.clearProblemLayouts();
                        view.showBoardStatus(error.getMessage(), true);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                submitInProgress = false;
                view.enableSubmitButton();
                view.clearProblemLayouts();
                view.showBoardStatus(Constants.CONNECTION_PROBLEM, true);
            }
        });
    }

    private void openBoardActivity(Board board) {
        Intent intent = new Intent(context, BoardActivity.class);
        intent.putExtra("board", board);
        context.startActivity(intent);
    }

    private boolean validateOrShowError(String boardId, String boardTitle,
                                        String boardCategory) {
        boolean isOk = true;
        if (!isValid(boardId)) {
            showErrorBasedOnField(EFields.ID);
            isOk = false;
        }
        if (!isValid(boardTitle)) {
            showErrorBasedOnField(EFields.TITLE);
            isOk = false;
        }
        if (!isValid(boardCategory)) {
            showErrorBasedOnField(EFields.CATEGORY);
            isOk = false;
        }
        return isOk;
    }

    public boolean isValid(String value) {
        return !value.isEmpty();
    }

    public void showErrorBasedOnField(EFields field) {
        String error = Constants.CANT_BE_EMPTY;
        switch (field) {
            case ID:
                view.showBoardIdError("شناسه برد " + error);
                break;
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

    private enum EFields {
        ID,
        TITLE,
        CATEGORY
    }
}