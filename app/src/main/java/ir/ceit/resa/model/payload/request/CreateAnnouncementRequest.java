package ir.ceit.resa.model.payload.request;


import com.google.gson.annotations.SerializedName;

public class CreateAnnouncementRequest {

    @SerializedName("timestamp")
    private String creationDate;

    @SerializedName("message")
    private String message;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
