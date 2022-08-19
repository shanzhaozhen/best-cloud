package org.shanzhaozhen.uaa.constant;

public interface OAuth2ParameterNames {

    /**
     * {@code grant_type} - used in Access Token Request.
     */
    String GRANT_TYPE = "grant_type";

    /**
     * {@code response_type} - used in Authorization Request.
     */
    String RESPONSE_TYPE = "response_type";

    /**
     * {@code client_id} - used in Authorization Request and Access Token Request.
     */
    String CLIENT_ID = "client_id";

    /**
     * {@code client_secret} - used in Access Token Request.
     */
    String CLIENT_SECRET = "client_secret";

    /**
     * {@code client_assertion_type} - used in Access Token Request.
     * @since 5.5
     */
    String CLIENT_ASSERTION_TYPE = "client_assertion_type";

    /**
     * {@code client_assertion} - used in Access Token Request.
     * @since 5.5
     */
    String CLIENT_ASSERTION = "client_assertion";

    /**
     * {@code assertion} - used in Access Token Request.
     * @since 5.5
     */
    String ASSERTION = "assertion";

    /**
     * {@code redirect_uri} - used in Authorization Request and Access Token Request.
     */
    String REDIRECT_URI = "redirect_uri";

    /**
     * {@code scope} - used in Authorization Request, Authorization Response, Access Token
     * Request and Access Token Response.
     */
    String SCOPE = "scope";

    /**
     * {@code state} - used in Authorization Request and Authorization Response.
     */
    String STATE = "state";

    /**
     * {@code code} - used in Authorization Response and Access Token Request.
     */
    String CODE = "code";

    /**
     * {@code access_token} - used in Authorization Response and Access Token Response.
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * {@code token_type} - used in Authorization Response and Access Token Response.
     */
    String TOKEN_TYPE = "token_type";

    /**
     * {@code expires_in} - used in Authorization Response and Access Token Response.
     */
    String EXPIRES_IN = "expires_in";

    /**
     * {@code refresh_token} - used in Access Token Request and Access Token Response.
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * {@code username} - used in Access Token Request.
     */
    String USERNAME = "username";

    /**
     * {@code password} - used in Access Token Request.
     */
    String PASSWORD = "password";

    /**
     * {@code error} - used in Authorization Response and Access Token Response.
     */
    String ERROR = "error";

    /**
     * {@code error_description} - used in Authorization Response and Access Token
     * Response.
     */
    String ERROR_DESCRIPTION = "error_description";

    /**
     * {@code error_uri} - used in Authorization Response and Access Token Response.
     */
    String ERROR_URI = "error_uri";

    /**
     * Non-standard parameter (used internally).
     */
    String REGISTRATION_ID = "registration_id";

    /**
     * {@code token} - used in Token Revocation Request.
     * @since 5.5
     */
    String TOKEN = "token";

    /**
     * {@code token_type_hint} - used in Token Revocation Request.
     * @since 5.5
     */
    String TOKEN_TYPE_HINT = "token_type_hint";

}
