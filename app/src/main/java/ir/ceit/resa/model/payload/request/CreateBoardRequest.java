package ir.ceit.resa.model.payload.request;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateBoardRequest {

    @SerializedName("boardId")
    private String boardId;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("faculty")
    private String faculty;

    @SerializedName("timestamp")
    private String creationDate;

    public CreateBoardRequest(String boardId, String description, String category, String faculty, String creationDate) {
        this.boardId = boardId;
        this.description = description;
        this.category = category;
        this.faculty = faculty;
        this.creationDate = creationDate;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
