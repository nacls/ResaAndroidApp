package ir.ceit.resa.model.payload.response;

import com.google.gson.annotations.SerializedName;

public class LoginError {

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    @SerializedName("status")
    private Integer status;

    @SerializedName("path")
    private String path;

    public LoginError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
