package com.basecode.network

/**
 * The interface Request listener.
 *
 * @author Vijay Desai
 */
interface RequestListener {

    /**
     * On success.
     *
     * @param id       the id
     * @param response the response
     */
    fun onSuccess(id: Int, response: String)

    /**
     * On error.
     *
     * @param id      the id
     * @param message the message
     */
    fun onError(id: Int, message: String)
}
