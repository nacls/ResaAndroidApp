package ir.ceit.resa.controller.network;

import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.response.JwtResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiClient {

    @POST(UrlProvider.LOGIN_URL)
    Call<JwtResponse> getUserLoginInfo(@Body LoginRequest body);


    @GET(UrlProvider.LOGIN_URL)
    Call<JwtResponse> getUserBoards(@Header("Authorization") String token);
}
