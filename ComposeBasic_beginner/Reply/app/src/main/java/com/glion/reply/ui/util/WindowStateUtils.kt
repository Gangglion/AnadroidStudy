package com.glion.reply.ui.util

enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

enum class ReplyContentType{
    LIST_ONLY, // 일반 화면일때 이 값을 사용 - 목록 전용 이메일 콘텐츠 표시용
    LIST_AND_DETAIL // 확장형 화면 사용중일때 이 값을 사용 - 전체 목록과 세부정보 표시용
}