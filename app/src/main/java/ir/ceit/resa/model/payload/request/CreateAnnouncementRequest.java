package ir.ceit.resa.model.payload.request;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateAnnouncementRequest {

    @SerializedName("timestamp")
    private Date creationDate;

    @SerializedName("message")
    private String message;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
