package ir.ceit.resa.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.ceit.resa.contract.SearchContract;
import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.EMembership;
import ir.ceit.resa.model.payload.request.SearchBoardRequest;
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

public class SearchBoardActivityPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private Context context;

    public SearchBoardActivityPresenter(SearchContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreated() {
        view.setupActivityView();
    }

    @Override
    public void searchButtonClicked(String boardQuery) {
        view.showProgress();
        searchInServerBoards(new SearchBoardRequest(boardQuery));
    }

    public void searchInServerBoards(SearchBoardRequest searchBoardRequest) {
        String token = ResaSharedPreferences.getToken(context);
        WebService.searchBoard(token, searchBoardRequest, new Callback<List<BoardInfoResponse>>() {
            @Override
            public void onResponse(Call<List<BoardInfoResponse>> call, Response<List<BoardInfoResponse>> response) {
                if (response.isSuccessful()) {
                    List<BoardInfoResponse> joinedBoards = response.body();
                    assert joinedBoards != null;
                    if (joinedBoards.size() == 0) {
                        view.showSearchResultProblem(Constants.NO_SEARCH_RESULT, 1);
                    } else {
                        view.showSearchResult(makeBoardModelFromPayload(joinedBoards));
                    }
                } else {
                    MessageResponse error = ErrorUtils.parseMessageResponse(response);
                    view.showSearchResultProblem(error.getMessage(), 0);
                }
            }

            @Override
            public void onFailure(Call<List<BoardInfoResponse>> call, Throwable t) {
                view.showSearchResultProblem(Constants.CONNECTION_PROBLEM, 2);
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
}