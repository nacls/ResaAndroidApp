package ir.ceit.resa.service;

public class Constants {

    // General
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String CONNECTION_PROBLEM = "مشکلی در برقراری ارتباط با سرور پیش آمده است.";
    public static final String CHECK_CONNECTION = "اتصال اینترنت خود را بررسی کنید.";
    public static final String TRY_AGAIN = "مجدداً تلاش کنید.";
    public static final String CHECK_CONNECTION_TRY_AGAIN = "اتصال اینترنت خود را بررسی کنید و مجدداً تلاش کنید.";
    public static final String UNKNOWN_ERROR_TRY_AGAIN = "خطای نامشخص، مجدداً تلاش کنید.";
    public static final String UNEXPECTED_PROBLEM_NOTIFY_ADMIN = "مشکل غیر منتظره ای رخ داده است. " + "\n" + "به پشتیبانی سیستم اطلاع دهید.";

    // Login
    public static final String PROBLEM_OCCURRED_DURING_LOGIN = "مشکلی در ورود پیش آمده است.";
    public static final String LOGIN_SUCCESSFUL_WAIT = "ورود مووفقیت آمیز بود، اندکی صبر کنید.";
    public static final String USERNAME_ERROR = "نام کاربری نمی تواند کمتر از 2 حرف باشد.";
    public static final String PASSWORD_ERROR = "رمز عبور نمی تواند کمتر از 2 حرف باشد.";
    public static final String USERNAME_AND_PASSWORD_ERROR = "نام کاربری و رمز عبور نمی توانند کمتر از 2 حرف باشند.";
    public static final String USERNAME_OR_PASSWORD_WRONG = "نام کاربری و یا رمز عبور اشتباه است.";

    // Board
    public static final String NO_BOARDS_TO_SHOW = "بردی برای نمایش وجود ندارد!";
    public static final String NO_SEARCH_RESULT = "بردی با شناسه وارد شده، پیدا نشد!";
    // Announcements
    public static final String NO_ANNOUNCEMENTS_TO_SHOW = "هنوز اطلاعیه ای برای نمایش در این برد وجود ندارد!";
    public static final String ADD_ANNOUNCEMENTS_SUCCESSFUL = "اطلاعیه با موفقیت اضافه شد!";
    public static final String ADD_ANNOUNCEMENTS_FAILED = "اطلاعیه ارسال نشد.";
    public static final String ANNOUNCEMENTS_CANT_BE_EMPTY = "اطلاعیه نمی تواند خالی باشد.";
    // Server Messages
    public static final String SERVER_RESPONSE_ADD_ANNOUNCEMENT_OK = "Announcement added successfully!";
    public static final String BOARD_ID_TAKEN_ERROR = "Error: board id is already taken!";
    // Submission Errors
    public static final String CANT_BE_EMPTY = "نمی تواند خالی باشد.";
    public static final String NOTICE_ERRORS_RETRY = "خطاهای گفته شده را حل کنید و مجدداً تلاش کنید.";
    public static final String LAST_REQUEST_STILL_IN_PROGRESS = "درخواست قبلی هنوز در حال پردازش است، لطفاً منتظر بمانید.";
    public static final String REQUEST_SENT_SUCCESSFULLY = "درخواست شما با موفقیت ارسال شد، لطفاً منتظر بمانید.";
    public static final String BOARD_ID_TAKEN_CHOOSE_ANOTHER = "این شناسه قبلاً استفاده شده است،"+"\n"+"شناسه دیگری انتخاب کنید.";

}
