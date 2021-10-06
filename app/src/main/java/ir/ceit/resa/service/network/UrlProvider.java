package ir.ceit.resa.service.network;

public class UrlProvider {

    public static final String BASE_URL = "http://10.0.2.2:8080/";

    // Authentication endpoints
    public static final String LOGIN_URL = "api/auth/signin";
    public static final String CHANGE_PASSWORD = "";
    // Admin
    public static final String ADD_USER = "";

    // Board endpoints
    public static final String JOINED_BOARDS = "/api/board/joined/{username}";
    public static final String SEARCH_BOARDS = "/api/board/search";
    public static final String JOIN_BOARD = "/api/board/join/{boardId}";
    public static final String LEAVE_BOARD = "/api/board/leave/{boardId}";
    // Create board
    public static final String CREATE_BOARD = "/api/board/create";
    // Edit board
    public static final String EDIT_BOARD = "/api/board/edit/{boardId}";
    public static final String DELETE_BOARD = "/api/board/delete/{boardId}";
    public static final String BOARD_ACCESS_CONTROL = "/api/board/access-control";
    public static final String GET_BOARD_MEMBERS = "/api/board/members/{boardId}";

    // Announcement endpoints
    public static final String GET_BOARD_ANNOUNCEMENTS = "/api/announcement/get/{boardId}";
    public static final String ADD_ANNOUNCEMENT_TO_BOARD = "/api/announcement/add/{boardId}";
}
