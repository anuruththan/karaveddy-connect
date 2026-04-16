package com.example.karaveddy_connect.repository.userAuth;

public class UserAuthConstant {

    public final static String GET_USER_AUTH_BY_EMAIL = "{CALL get_user_auth_by_email(?)}";

    public final static String INSERT_USER = "{CALL insert_user_details( ?, ?, ?, ?, ?, ?)}";

    public final static String UPDATE_USER = "{CALL update_user_details( ?, ?, ?, ?, ?, ?, ?)}";

    public final static String CHANGE_PASSWORD = "{CALL change_password( ?, ?, ?)}";

}
