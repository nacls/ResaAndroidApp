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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient {

    @POST(UrlProvider.LOGIN_URL)
    Call<JwtResponse> getUserLoginInfo(@Body LoginRequest body);

    @Headers("Content-Type: application/json")
    @GET(UrlProvider.JOINED_BOARDS)
    Call<List<BoardInfoResponse>> getUserBoards(@Path("username") String username,
                                                @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET(UrlProvider.GET_BOARD_ANNOUNCEMENTS)
    Call<List<Announcement>> getBoardAnnouncements(@Path("boardId") String boardId,
                                                   @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST(UrlProvider.ADD_ANNOUNCEMENT_TO_BOARD)
    Call<MessageResponse> addAnnouncementToBoard(@Path("boardId") String boardId,
                                                 @Header("Authorization") String token,
                                                 @Body CreateAnnouncementRequest announcementRequest);

    @Headers("Content-Type: application/json")
    @POST(UrlProvider.CREATE_BOARD)
    Call<BoardInfoResponse> createBoard(@Header("Authorization") String token,
                                        @Body CreateBoardRequest createBoardRequest);

    @Headers("Content-Type: application/json")
    @POST(UrlProvider.SEARCH_BOARDS)
    Call<List<BoardInfoResponse>> searchBoard(@Header("Authorization") String token,
                                              @Body SearchBoardRequest searchBoardRequest);

    @Headers("Content-Type: application/json")
    @GET(UrlProvider.JOIN_BOARD)
    Call<MessageResponse> joinBoard(@Path("boardId") String boardId,
                                    @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT(UrlProvider.LEAVE_BOARD)
    Call<MessageResponse> leaveBoard(@Path("boardId") String boardId,
                                     @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT(UrlProvider.EDIT_BOARD)
    Call<BoardInfoResponse> editBoard(@Path("boardId") String boardId,
                                    @Header("Authorization") String token,
                                    @Body EditBoardRequest editBoardRequest);

    @Headers("Content-Type: application/json")
    @DELETE(UrlProvider.DELETE_BOARD)
    Call<MessageResponse> deleteBoard(@Path("boardId") String boardId,
                                      @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET(UrlProvider.GET_BOARD_MEMBERS)
    Call<List<BoardMemberResponse>> getBoardMembers(@Path("boardId") String boardId,
                                                    @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT(UrlProvider.BOARD_ACCESS_CONTROL)
    Call<MessageResponse> changeUserMembership(@Header("Authorization") String token,
                                               @Body ChangeMembershipRequest changeMembershipRequest);

    @Headers("Content-Type: application/json")
    @POST(UrlProvider.CHANGE_PASSWORD)
    Call<MessageResponse> changePassword(@Header("Authorization") String token,
                                      @Body ChangePasswordRequest changePasswordRequest);

    @Headers("Content-Type: application/json")
    @POST(UrlProvider.ADD_USER)
    Call<MessageResponse> addUser(@Header("Authorization") String token,
                                         @Body AddUserRequest addUserRequest);
}
