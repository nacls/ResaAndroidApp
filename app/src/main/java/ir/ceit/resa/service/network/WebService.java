package ir.ceit.resa.service.network;

import java.util.List;

import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.payload.request.AddUserRequest;
import ir.ceit.resa.model.payload.request.ChangeMembershipRequest;
import ir.ceit.resa.model.payload.request.ChangePasswordRequest;
import ir.ceit.resa.model.payload.request.CreateAnnouncementRequest;
import ir.ceit.resa.model.payload.request.CreateBoardRequest;
import ir.ceit.resa.model.payload.request.EditBoardRequest;
import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.request.SearchBoardRequest;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.BoardMemberResponse;
import ir.ceit.resa.model.payload.response.JwtResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class WebService {

    public static void login(LoginRequest loginRequest, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<JwtResponse> call = service.getUserLoginInfo(loginRequest);
        call.enqueue(callback);
    }

    public static void getJoinedBoards(String token, String username, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<BoardInfoResponse>> call = service.getUserBoards(username, token);
        call.enqueue(callback);
    }

    public static void getBoardAnnouncements(String token, String boardId, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<Announcement>> call = service.getBoardAnnouncements(boardId, token);
        call.enqueue(callback);
    }

    public static void addAnnouncementToBoard(String token,
                                              String boardId,
                                              CreateAnnouncementRequest announcementRequest,
                                              Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.addAnnouncementToBoard(boardId, token, announcementRequest);
        call.enqueue(callback);
    }

    public static void createBoard(String token,
                                   CreateBoardRequest createBoardRequest,
                                   Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<BoardInfoResponse> call = service.createBoard(token, createBoardRequest);
        call.enqueue(callback);
    }

    public static void searchBoard(String token,
                                   SearchBoardRequest searchBoardRequest,
                                   Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<BoardInfoResponse>> call = service.searchBoard(token, searchBoardRequest);
        call.enqueue(callback);
    }

    public static void joinBoard(String token,
                                 String boardId,
                                 Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.joinBoard(boardId, token);
        call.enqueue(callback);
    }

    public static void leaveBoard(String token,
                                  String boardId,
                                  Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.leaveBoard(boardId, token);
        call.enqueue(callback);
    }

    public static void editBoard(String token,
                                 String boardId,
                                 EditBoardRequest editBoardRequest,
                                 Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<BoardInfoResponse> call = service.editBoard(boardId, token, editBoardRequest);
        call.enqueue(callback);
    }

    public static void deleteBoard(String token,
                                   String boardId,
                                   Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.deleteBoard(boardId, token);
        call.enqueue(callback);
    }

    public static void getBoardMembers(String token,
                                       String boardId,
                                       Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<BoardMemberResponse>> call = service.getBoardMembers(boardId, token);
        call.enqueue(callback);
    }

    public static void changeBoardMembership(String token,
                                             ChangeMembershipRequest changeMembershipRequest,
                                             Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.changeUserMembership(token, changeMembershipRequest);
        call.enqueue(callback);
    }

    public static void changePassword(String token,
                                             ChangePasswordRequest changePasswordRequest,
                                             Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.changePassword(token, changePasswordRequest);
        call.enqueue(callback);
    }

    public static void addUser(String token,
                                      AddUserRequest addUserRequest,
                                      Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<MessageResponse> call = service.addUser(token, addUserRequest);
        call.enqueue(callback);
    }
}
