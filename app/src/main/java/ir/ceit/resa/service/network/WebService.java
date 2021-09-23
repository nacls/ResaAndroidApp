package ir.ceit.resa.service.network;

import java.util.List;

import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;
import ir.ceit.resa.model.payload.response.JwtResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class WebService {

    public static void login(LoginRequest body, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<JwtResponse> call = service.getUserLoginInfo(body);
        call.enqueue(callback);
    }

    public static void getJoinedBoards(String token, String username, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<BoardInfoResponse>> call = service.getUserBoards(username, token);
        call.enqueue(callback);
    }

    public static void getBoardAnnouncements(String token, String boardId, Callback callback){
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<List<Announcement>> call = service.getBoardAnnouncements(boardId, token);
        call.enqueue(callback);
    }
}
