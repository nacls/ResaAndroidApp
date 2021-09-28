package ir.ceit.resa.service;

import ir.ceit.resa.model.Board;
import ir.ceit.resa.model.payload.response.BoardInfoResponse;

public class BoardUtil {

    public static Board makeBoardFromBoardInfoResponse(BoardInfoResponse boardInfoResponse) {
        return new Board(boardInfoResponse.getBoardId(),
                boardInfoResponse.getDescription(),
                boardInfoResponse.getCategory(),
                boardInfoResponse.getCreatorUsername(),
                boardInfoResponse.getFaculty(),
                boardInfoResponse.getLatestAnnouncement(),
                boardInfoResponse.getUserMembership()
        );
    }
}
