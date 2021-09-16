package ir.ceit.resa.model.payload.request;


import com.google.gson.annotations.SerializedName;

import ir.ceit.resa.model.EMembership;

public class ChangeMembershipRequest {

    @SerializedName("boardId")
    private String boardId;

    @SerializedName("username")
    private String username;

    @SerializedName("membership")
    private EMembership membership;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EMembership getMembership() {
        return membership;
    }

    public void setMembership(EMembership membership) {
        this.membership = membership;
    }
}
