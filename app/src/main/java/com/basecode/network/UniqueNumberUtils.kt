package com.basecode.network

import java.util.concurrent.atomic.AtomicInteger


/**
 * The type Unique number utils.
 *
 */
class UniqueNumberUtils private constructor() {

    private val seq: AtomicInteger

    /**
     * Gets unique id.
     *
     * @return the unique id
     */
    val uniqueId: Int
        get() = seq.incrementAndGet()

    init {
        seq = AtomicInteger(0)
    }

    companion object {

        /**
         * Gets instance.
         *
         * @return the instance
         */
        val instance = UniqueNumberUtils()
    }
}