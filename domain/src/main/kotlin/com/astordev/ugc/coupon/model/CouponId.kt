package com.astordev.ugc.coupon.model

import com.astordev.ugc.IdProvider
import com.astordev.ugc.SnowflakeIdFactory

@JvmInline
value class CouponId private constructor(val long : Long){
    companion object : IdProvider<CouponId> by SnowflakeIdFactory(::CouponId)
}