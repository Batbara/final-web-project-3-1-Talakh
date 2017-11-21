package by.tr.web.exception;

import org.omg.CORBA.PUBLIC_MEMBER;

public final class ExceptionMessage {
    public static final String INVALID_LOGIN = "Incorrect login";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INCORRECT_PASSWORD = "Incorrect password";
    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String EXISTING_USER = "User already exists";

    public static final String CONNECTION_POOL_INSTANCE_FAILURE = "Failed to get instance of connection pool";
    public static final String CONNECTION_FAILURE = "Failed to get connection";
    public static final String SQL_ERROR = "SQL error";
    public static final String MISSING_DB_DRIVER = "Can't find database driver class";
    public static final String CLOSING_CONNECTION_ERROR = "Closing connection error";
    public static final String CLOSING_RESULT_SET_ERROR = "Closing result set error";
    public static final String CLOSING_STATEMENT_ERROR = "Closing statement error";
    public static final String CONNECTION_ALREADY_CLOSED = "Trying to close closed connection";
    public static final String CONNECTION_ACCESS_ERROR = "Can't access connection";
    public static final String GIVEN_AWAY_CONNECTION_QUEUE_ERROR = "Error deleting connection from the given away connections pool";
    public static final String CONNECTION_QUEUE_ERROR = "Error allocating connection in the pool";
    public static final String CONNECTING_TO_DATA_SOURCE_ERROR = "Error connecting to the data source";

}
