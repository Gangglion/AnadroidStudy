package com.example.practice_and.data

data class CandleChartExData(
    var createdAt: Long = 0,
    val open: Float,
    val close: Float,
    val shadowHigh: Float,
    val shadowLow: Float
)

object CandleChartExDataUtil {
    fun getExData(): List<CandleChartExData> {
        return listOf(
            CandleChartExData(
                createdAt = 0,
                open = 222.8F,
                close = 222.9F,
                shadowHigh = 224.0F,
                shadowLow = 222.2F
            ),
            CandleChartExData(
                createdAt = 1,
                open = 222.0F,
                close = 222.2F,
                shadowHigh = 222.4F,
                shadowLow = 222.0F
            ),
            CandleChartExData(
                createdAt = 2,
                open = 222.2F,
                close = 221.9F,
                shadowHigh = 222.5F,
                shadowLow = 221.5F
            ),
            CandleChartExData(
                createdAt = 3,
                open = 222.4F,
                close = 222.3F,
                shadowHigh = 223.7F,
                shadowLow = 222.1F
            ),
            CandleChartExData(
                createdAt = 4,
                open = 221.6F,
                close = 221.9F,
                shadowHigh = 221.9F,
                shadowLow = 221.5F
            ),
            CandleChartExData(
                createdAt = 5,
                open = 221.8F,
                close = 224.9F,
                shadowHigh = 225.0F,
                shadowLow = 221.0F
            ),
            CandleChartExData(
                createdAt = 6,
                open = 225.0F,
                close = 220.2F,
                shadowHigh = 225.4F,
                shadowLow = 219.2F
            ),
            CandleChartExData(
                createdAt = 7,
                open = 222.2F,
                close = 225.9F,
                shadowHigh = 227.5F,
                shadowLow = 222.2F
            ),
            CandleChartExData(
                createdAt = 8,
                open = 226.0F,
                close = 228.1F,
                shadowHigh = 228.1F,
                shadowLow = 225.1F
            ),
            CandleChartExData(
                createdAt = 9,
                open = 227.6F,
                close = 228.9F,
                shadowHigh = 230.9F,
                shadowLow = 226.5F
            ),
            CandleChartExData(
                createdAt = 10,
                open = 228.6F,
                close = 221.6F,
                shadowHigh = 230.9F,
                shadowLow = 228.0F
            ),
            CandleChartExData(
                createdAt = 11,
                open = 226.0F,
                close = 228.1F,
                shadowHigh = 228.1F,
                shadowLow = 225.1F
            ),
            CandleChartExData(
                createdAt = 12,
                open = 227.6F,
                close = 228.9F,
                shadowHigh = 230.9F,
                shadowLow = 226.5F
            ),
            CandleChartExData(
                createdAt = 13,
                open = 228.6F,
                close = 221.6F,
                shadowHigh = 230.9F,
                shadowLow = 228.0F
            ),
            CandleChartExData(
                createdAt = 14,
                open = 222.4F,
                close = 222.3F,
                shadowHigh = 223.7F,
                shadowLow = 222.1F
            ),
            CandleChartExData(
                createdAt = 15,
                open = 221.6F,
                close = 221.9F,
                shadowHigh = 221.9F,
                shadowLow = 221.5F
            ),
            CandleChartExData(
                createdAt = 16,
                open = 221.8F,
                close = 224.9F,
                shadowHigh = 225.0F,
                shadowLow = 221.0F
            ),
            CandleChartExData(
                createdAt = 17,
                open = 222.8F,
                close = 222.9F,
                shadowHigh = 224.0F,
                shadowLow = 222.2F
            ),
            CandleChartExData(
                createdAt = 18,
                open = 222.0F,
                close = 222.2F,
                shadowHigh = 222.4F,
                shadowLow = 222.0F
            ),
            CandleChartExData(
                createdAt = 19,
                open = 222.2F,
                close = 221.9F,
                shadowHigh = 222.5F,
                shadowLow = 221.5F
            ),
            CandleChartExData(
                createdAt = 20,
                open = 222.4F,
                close = 222.3F,
                shadowHigh = 223.7F,
                shadowLow = 222.1F
            )
        )
    }
}
