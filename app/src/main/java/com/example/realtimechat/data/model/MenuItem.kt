package com.example.realtimechat.data.model

import com.example.realtimechat.R
import com.example.realtimechat.instruments.Constants

data class MenuItem(val category: String, val iconId: Int, val title: String, val text: String) {
    companion object {
        val menuItemsList = arrayListOf(
            MenuItem(
                Constants.CATEOGRY_CAT,
                R.drawable.ic_baseline_chat,
                "Питомец",
                "Все данные вашего питомца в одном месте"
            ),
            MenuItem(
                Constants.CATEOGRY_CARE,
                R.drawable.ic_baseline_chat,
                "Уход",
                "Инструментарий для ухода за вашим питомцем"
            ),

            MenuItem(
                Constants.CATEOGRY_CHAT,
                R.drawable.ic_baseline_chat,
                "Общий чат",
                "Чат для общения всех пользователей нашего приложения"
            ),
            MenuItem(
                Constants.CATEOGRY_USERS,
                R.drawable.ic_baseline_chat,
                "Список пользователей",
                "Список всех участников"
            )
        )
    }
}