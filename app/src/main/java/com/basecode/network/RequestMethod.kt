package com.basecode.network

/**
 * The enum Request method.
 */
enum class RequestMethod {
    /**
     * Get request method.
     */
    GET,
    /**
     * Get request method with url query params.
     */
    GET_WITH_PARAMS,
    /**
     * Post request method.
     */
    POST,
    /**
     * Multipart request method.
     */
    MULTIPART
}
