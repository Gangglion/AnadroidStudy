package com.example.practice_and.calendar.data

class Event {
    var year: Int
        private set
    var month: Int
        private set
    var day: Int
        private set
    var color = 0
        private set

    constructor(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
    }

    constructor(year: Int, month: Int, day: Int, color: Int) {
        this.year = year
        this.month = month
        this.day = day
        this.color = color
    }
}