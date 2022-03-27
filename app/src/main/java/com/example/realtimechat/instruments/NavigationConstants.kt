package com.example.realtimechat.instruments

import com.example.realtimechat.R

enum class NavigationConstants(val destinationId: Int) {
//    START,
    LOGIN(R.id.signInFragment),
    REGISTRATION(R.id.registrationFragment),
    PASSWORDRECOVERY(R.id.passwordRecoveryFragment),
    CHAT(R.id.chatFragment2)
//    USERINFO,
//    CHATINFO,
//    CHATUSERINFO
}