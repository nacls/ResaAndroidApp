package ir.ceit.resa.controller.network;

import ir.ceit.resa.model.payload.request.LoginRequest;
import ir.ceit.resa.model.payload.response.JwtResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class WebService {

    public static void login(LoginRequest body, Callback callback) {
        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);
        Call<JwtResponse> call = service.getUserLoginInfo(body);
        call.enqueue(callback);
    }
}
