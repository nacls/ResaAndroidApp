package ir.ceit.resa.model.payload.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("new-password")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
