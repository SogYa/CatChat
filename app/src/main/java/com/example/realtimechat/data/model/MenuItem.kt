package com.example.realtimechat.data.model

import com.example.realtimechat.R

data class MenuItem(val categoryId: Int, val iconId: Int, val title: String, val text: String) {
    companion object {
        val menuItemsList = arrayListOf(
            MenuItem(
                1,
                R.drawable.ic_baseline_chat,
                "Питомец",
                "Все данные вашего питомца в одном месте"
            ),
            MenuItem(
                2,
                R.drawable.ic_baseline_chat,
                "Уход",
                "Инструментарий для ухода за вашим питомцем"
            ),

            MenuItem(
                3,
                R.drawable.ic_baseline_chat,
                "Общий чат",
                "Чат для общения всех пользователей нашего приложения"
            ),
            MenuItem(
                4,
                R.drawable.ic_baseline_chat,
                "Список пользователей",
                "Список всех участников"
            )
        )
    }
}