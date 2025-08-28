package com.astordev.ugc

interface IdProvider<T> {
    fun generate(): T
    fun from(id: Long): T
}


const val DEFAULT_ID_EPOCH = 1577836800000L
const val LOWER_HEX_FORMAT = "%016x"
const val UPPER_HEX_FORMAT = "%016X"

object SnowflakeIdGenerator {

    private const val MACHINE_ID_BITS = 10L
    private const val SEQUENCE_BITS = 12L
    private const val MAX_MACHINE_ID = (1L shl MACHINE_ID_BITS.toInt()) - 1
    private const val MAX_SEQUENCE = (1L shl SEQUENCE_BITS.toInt()) - 1
    // 기준 시간 (Epoch): 2025-01-01 00:00:00 UTC
    private const val CUSTOM_EPOCH = 1735689600000L
    private const val TIMESTAMP_SHIFT = MACHINE_ID_BITS + SEQUENCE_BITS
    private const val MACHINE_ID_SHIFT = SEQUENCE_BITS

    // NOTE: 실제로 쓸 땐 환경변수 주입
    private val machineId: Long = 123456L

    private var lastTimestamp: Long = -1L
    private var sequence: Long = 0L

    init {
        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw IllegalArgumentException("Machine ID must be between 0 and $MAX_MACHINE_ID")
        }
    }

    // --- ID 생성 메서드 ---
    @Synchronized
    fun generate(): Long {
        var currentTimestamp = System.currentTimeMillis()

        // 시계가 거꾸로 가는 경우 예외 처리
        if (currentTimestamp < lastTimestamp) {
            throw IllegalStateException("Clock moved backwards. Refusing to generate id for ${lastTimestamp - currentTimestamp} milliseconds")
        }

        // 같은 밀리초에 ID를 생성하는 경우
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) and MAX_SEQUENCE
            // 시퀀스가 가득 차면, 다음 밀리초까지 대기
            if (sequence == 0L) {
                currentTimestamp = waitUntilNextMillis(currentTimestamp)
            }
        } else {
            // 새로운 밀리초에는 시퀀스를 0으로 리셋
            sequence = 0L
        }

        lastTimestamp = currentTimestamp

        // 각 부분을 비트 연산으로 조합하여 최종 ID 생성
        return ((currentTimestamp - CUSTOM_EPOCH) shl TIMESTAMP_SHIFT.toInt()) or
                (machineId shl MACHINE_ID_SHIFT.toInt()) or
                sequence
    }

    private fun waitUntilNextMillis(currentTimestamp: Long): Long {
        var timestamp = currentTimestamp
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}

class SnowflakeIdFactory<T>(private val constructor: (Long) -> T) : IdProvider<T> {
    override fun generate(): T {
        return constructor(SnowflakeIdGenerator.generate())
    }

    override fun from(id: Long): T {
        return constructor(id)
    }
}