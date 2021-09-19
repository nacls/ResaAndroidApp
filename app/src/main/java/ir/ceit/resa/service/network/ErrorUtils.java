package ir.ceit.resa.service.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import ir.ceit.resa.model.payload.response.LoginError;
import ir.ceit.resa.model.payload.response.MessageResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static LoginError parseLoginError(Response<?> response) {
        Converter<ResponseBody, LoginError> converter =
                RetrofitClient.getRetrofitInstance()
                        .responseBodyConverter(LoginError.class, new Annotation[0]);

        LoginError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new LoginError();
        }

        return error;
    }

    public static MessageResponse parseMessageResponse(Response<?> response) {
        Converter<ResponseBody, MessageResponse> converter =
                RetrofitClient.getRetrofitInstance()
                        .responseBodyConverter(MessageResponse.class, new Annotation[0]);

        MessageResponse messageResponse;

        try {
            messageResponse = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new MessageResponse();
        }

        return messageResponse;
    }


}
