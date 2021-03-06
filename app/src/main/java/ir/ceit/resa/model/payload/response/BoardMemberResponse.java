package ir.ceit.resa.model.payload.response;

import com.google.gson.annotations.SerializedName;

import ir.ceit.resa.model.EMembership;


public class BoardMemberResponse implements Comparable<BoardMemberResponse> {

    @SerializedName("username")
    private String username;

    @SerializedName("full-name")
    private String fullName;

    @SerializedName("membership")
    private EMembership membership;

    public BoardMemberResponse(String username, String fullName, EMembership membership) {
        this.username = username;
        this.fullName = fullName;
        this.membership = membership;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public EMembership getMembership() {
        return membership;
    }

    public void setMembership(EMembership membership) {
        this.membership = membership;
    }

    @Override
    public int compareTo(BoardMemberResponse o) {
        if (getMembership() == null) {
            return (o.getMembership() == null) ? 0 : 1;
        }
        if (o.getMembership() == null) {
            return -1;
        }

        if (getMembership() == EMembership.CREATOR) {
            return 1;
        }

        return getMembership().compareTo(o.getMembership());
    }
}
