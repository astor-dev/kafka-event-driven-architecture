package com.astordev.ugc.coupon.model

import com.astordev.ugc.user.model.UserId
import java.time.LocalDateTime

class Coupon private constructor (
    var id: CouponId,
    var userId: UserId,
    var couponEventId: CouponEventId,
    var issuedAt: LocalDateTime,
    var usedAt: LocalDateTime?
) {
    companion object {
        fun generate(userId: UserId, couponEventId: CouponEventId): Coupon {
            val now = LocalDateTime.now()
            return Coupon(CouponId.generate(), userId, couponEventId, now, null)
        }

        fun fron(id: CouponId, userId: UserId, couponEventId: CouponEventId, issuedAt: LocalDateTime, usedAt: LocalDateTime?): Coupon {
            return Coupon(id, userId, couponEventId, issuedAt, usedAt)
        }
    }

    fun use(): Coupon {
        this.usedAt = LocalDateTime.now()
        return this
    }
}