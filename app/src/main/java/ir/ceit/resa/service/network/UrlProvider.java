package ir.ceit.resa.service.network;

public class UrlProvider {

    public static final String BASE_URL = "http://10.0.2.2:8080/";
    // Authentication endpoints
    public static final String LOGIN_URL = "api/auth/signin";

    // Board endpoints
    public static final String JOINED_BOARDS = "/api/board/joined/{username}";

    public static final String CREATE_BOARD = "/api/board/create";

    // Announcement endpoints
    public static final String GET_BOARD_ANNOUNCEMENTS = "/api/announcement/get/{boardId}";
    public static final String ADD_ANNOUNCEMENT_TO_BOARD = "/api/announcement/add/{boardId}";
}
