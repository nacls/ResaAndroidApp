package ir.ceit.resa.service;

public class Constants {

    // Roles
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String CREATOR = "creator";

    // General
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String CONNECTION_PROBLEM = "مشکلی در برقراری ارتباط با سرور پیش آمده است.";
    public static final String CHECK_CONNECTION = "اتصال اینترنت خود را بررسی کنید.";
    public static final String TRY_AGAIN = "مجدداً تلاش کنید.";
    public static final String CHECK_CONNECTION_TRY_AGAIN = "اتصال اینترنت خود را بررسی کنید و مجدداً تلاش کنید.";
    public static final String UNKNOWN_ERROR_TRY_AGAIN = "خطای نامشخص، مجدداً تلاش کنید.";
    public static final String UNEXPECTED_PROBLEM_NOTIFY_ADMIN = "مشکل غیر منتظره ای رخ داده است. " + "\n" + "به پشتیبانی سیستم اطلاع دهید.";
    public static final String NOT_DEFINED = "تعریف نشده";

    // Login
    public static final String PROBLEM_OCCURRED_DURING_LOGIN = "مشکلی در ورود پیش آمده است.";
    public static final String LOGIN_SUCCESSFUL_WAIT = "ورود مووفقیت آمیز بود، اندکی صبر کنید.";
    public static final String USERNAME_LENGTH_ERROR = "نام کاربری نمی تواند کمتر از 2 حرف باشد";
    public static final String USERNAME_SPACE_ERROR = "نام کاربری نمی تواند شامل فاصله باشد";
    public static final String PASSWORD_LENGTH_ERROR = "رمز عبور نمی تواند کمتر از 6 حرف باشد";
    public static final String PASSWORD_SPACE_ERROR = "رمز عبور نمی تواند شامل فاصله باشد";
    public static final String USERNAME_OR_PASSWORD_WRONG = "نام کاربری و یا رمز عبور اشتباه است.";

    // Board
    public static final String NO_BOARDS_TO_SHOW = "بردی برای نمایش وجود ندارد!";
    public static final String NO_SEARCH_RESULT = "بردی با شناسه وارد شده پیدا نشد!";
    public static final String BE_SURE_TO_LEAVE_BOARD = " آیا از خروج از برد اطمینان دارید؟";
    public static final String YOU_JOINED_BOARD = "با موفقیت در برد عضو شدید!";
    public static final String YOU_LEFT_BOARD = "از برد خارج شدید!";
    public static final String BOARD_CHANGES_SUBMITTED = "تغییرات برد با موفقیت ثبت شد!";
    public static final String BE_SURE_TO_DELETE_BOARD = " آیا از حذف برد اطمینان دارید؟";
    public static final String MEMBERSHIP_DID_NOT_CHANGE = "درخواست شما انجام نشد، مجدداً تلاش کنید.";
    public static final String USER_WITH_ENTERED_USERNAME_DOES_NOT_EXIST = "کاربری با شناسه وارد شده وجود ندارد!";

    // Announcements
    public static final String NO_ANNOUNCEMENTS_TO_SHOW = "هنوز اطلاعیه ای برای نمایش در این برد وجود ندارد!";
    public static final String ADD_ANNOUNCEMENTS_SUCCESSFUL = "اطلاعیه با موفقیت اضافه شد!";
    public static final String ADD_ANNOUNCEMENTS_FAILED = "اطلاعیه ارسال نشد.";
    public static final String ANNOUNCEMENTS_CANT_BE_EMPTY = "اطلاعیه نمی تواند خالی باشد.";

    // Profile
    public static final String ADMIN_DESCRIPTION = "شما می توانید کاربران جدید به سیستم اضافه کنید و مشکلات سیستم را پیگیری کنید.";
    public static final String CREATOR_DESCRIPTION = "شما می توانید بردهای جدید ایجاد کنید، در آن ها اطلاعیه قرار دهید و آن ها را مدیریت کنید.";

    // User setting
    public static final String REPEATED_PASSWORD_IS_WRONG = "کلمات عبور وارد شده یکسان نیستند.";
    public static final String OLD_PASSWORD_IS_WRONG = "رمز عبور وارد شده اشتباه است.";
    public static final String NEW_PASS_IS_TOO_SHORT = "رمز عبور نمی تواند کمتر از 6 حرف باشد.";
    public static final String YOUR_PASS_WAS_CHANGED = "رمز عبور شما با موفقیت تغییر کرد.";

    // Admin setting
    public static final String USER_WAS_ADDED = "کاربر با موفقیت ساخته شد.";
    public static final String ENTERED_USERNAME_IS_TAKEN = "این نام کاربری قبلاً استفاده شده است.";
    public static final String ENTERED_EMAIL_IS_TAKEN = "این ایمیل قبلاً استفاده شده است.";


    // Submission Errors
    public static final String CANT_BE_EMPTY = "نمی تواند خالی باشد.";
    public static final String NOTICE_ERRORS_RETRY = "خطاهای گفته شده را حل کنید و مجدداً تلاش کنید.";
    public static final String LAST_REQUEST_STILL_IN_PROGRESS = "درخواست قبلی هنوز در حال پردازش است، لطفاً منتظر بمانید.";
    public static final String REQUEST_SENT_SUCCESSFULLY = "درخواست شما با موفقیت ارسال شد، لطفاً منتظر بمانید.";
    public static final String BOARD_ID_TAKEN_CHOOSE_ANOTHER = "این شناسه قبلاً استفاده شده است،" + "\n" + "شناسه دیگری انتخاب کنید.";

    // Server Messages
    public static final String SERVER_RESPONSE_ADD_ANNOUNCEMENT_OK = "Announcement added successfully!";
    public static final String BOARD_ID_TAKEN_ERROR = "Error: board id is already taken!";
    public static final String USER_JOINED_BOARD = "Membership added";
    public static final String USER_LEFT_BOARD = "user left board!";
    public static final String BOARD_WAS_DELETED = "Board deleted successfully!";
    public static final String MEMBERSHIP_CHANGED = "Membership changed";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String PASSWORD_CHANGED = "Password changed successfully!";
    public static final String USERNAME_IS_TAKEN = "Error: Username is already taken!";
    public static final String EMAIL_IS_TAKEN = "Error: Email is already in use!";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully!";
}
