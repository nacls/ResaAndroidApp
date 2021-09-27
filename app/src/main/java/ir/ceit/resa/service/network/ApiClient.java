package ir.ceit.resa.service.network;

import java.util.List;

import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.payload.request.CreateAnnouncementRequest;
import ir.ceit.resa.model.payload.request.CreateBoardRequest;
import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.JwtResponse;
import ir.ceit.resa.model.payload.response.MessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Call<MessageResponse> createBoard(@Header("Authorization") String token,
                                      @Body CreateBoardRequest createBoardRequest);
}
