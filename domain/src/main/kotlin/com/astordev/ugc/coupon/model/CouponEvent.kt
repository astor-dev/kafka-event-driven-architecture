package com.astordev.ugc.coupon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime


class CouponEvent private constructor (
    var id: CouponEventId,
    var displayName: String,
    var expiresAt: LocalDateTime,
    var issueLimit: Long
) {
    companion object {
        fun generate(
            displayName: String,
            expiresAt: LocalDateTime,
            issueLimit: Long
        ): CouponEvent {
            return CouponEvent(CouponEventId.generate(), displayName, expiresAt, issueLimit)
        }
    }

    @get:JsonIgnore
    val isExpired: Boolean
        get() = expiresAt.isBefore(LocalDateTime.now())
}