package com.KaraveddyConnect.exception.constant;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:47 PM
 **/
public class ExceptionConstant {
    public static final short INVALID_USERID_OR_PASSWORD = 1008;
    public static final short USER_TYPE_MISMATCH = 1012;
    public static final short USER_BLACKLISTED = 1030;
    public static final short COMMON_EXCEPTION = 1072;

    public static String getDescription(short code) {
        String message = "";
        switch (code) {
            case 401:
                message = "UnAuthorized.";
                break;

            case 403:
                message = "Forbidden.";
                break;

            case 500:
                message = "Internal Server Error.";
                break;

            case 1001:
                message = "Incorrect OTP. Please try again.";
                break;

            case 1086:
                message = "Email Id Does not Exist";
                break;

            case 1030:
                message = "User is blocked";
                break;

            case 1072:
                message = "Sorry! Something went wrong.";
                break;

        }
        return message;
    }

}
