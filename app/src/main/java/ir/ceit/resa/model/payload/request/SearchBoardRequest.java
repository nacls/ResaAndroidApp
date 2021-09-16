package ir.ceit.resa.model.payload.request;

import com.google.gson.annotations.SerializedName;


public class SearchBoardRequest {

    @SerializedName("boardId")
    private String boardId;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
