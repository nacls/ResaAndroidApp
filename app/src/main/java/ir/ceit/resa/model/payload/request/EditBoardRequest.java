package ir.ceit.resa.model.payload.request;

import com.google.gson.annotations.SerializedName;

public class EditBoardRequest {

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("faculty")
    private String faculty;

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
}
