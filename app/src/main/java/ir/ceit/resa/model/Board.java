package ir.ceit.resa.model;

import java.io.Serializable;
import java.util.ArrayList;

import ir.ceit.resa.model.payload.response.BoardInfoResponse;

public class Board implements Comparable<Board>, Serializable {

    private String boardId;

    private String description;

    private String category;

    private String creatorUsername;

    private String faculty;

    private Announcement latestAnnouncement;

    private EMembership userMembership;

    public Board(String boardId, String description, String category, String creatorUsername, String faculty, Announcement latestAnnouncement, EMembership userMembership) {
        this.boardId = boardId;
        this.description = description;
        this.category = category;
        this.creatorUsername = creatorUsername;
        this.faculty = faculty;
        this.latestAnnouncement = latestAnnouncement;
        this.userMembership = userMembership;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getFaculty() {
        return faculty;
    }

    public Announcement getLatestAnnouncement() {
        return latestAnnouncement;
    }

    public EMembership getUserMembership() {
        return userMembership;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setLatestAnnouncement(Announcement latestAnnouncement) {
        this.latestAnnouncement = latestAnnouncement;
    }

    public void setUserMembership(EMembership userMembership) {
        this.userMembership = userMembership;
    }

    public int compareTo(Board o) {
        if (getLatestAnnouncement() == null) {
            return (o.getLatestAnnouncement() == null) ? 0 : 1;
        }
        if (o.getLatestAnnouncement() == null) {
            return -1;
        }
        return getLatestAnnouncement().compareTo(o.getLatestAnnouncement());
    }
}
