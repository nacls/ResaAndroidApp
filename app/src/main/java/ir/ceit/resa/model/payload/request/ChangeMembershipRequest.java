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

    public ChangeMembershipRequest(String boardId, String username, EMembership membership) {
        this.boardId = boardId;
        this.username = username;
        this.membership = membership;
    }

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
