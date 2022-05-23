package com.example.realtimechat.instruments;

import com.example.realtimechat.data.SPControl;

public enum AppStatements {
    ONLINE("В сети"),
    OFFLINE("Не в сети");
    private final String title;

    public String getTitle() {
        return title;
    }

    AppStatements(String title) {
        this.title = title;
    }

    public static void sendOnline(){
        Constants.PATH_TO_USER.child(SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID)).child("status").setValue(ONLINE.getTitle());
    }

    public static void sendOffline() {
        Constants.PATH_TO_USER.child(SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID)).child("status").setValue(OFFLINE.getTitle());
    }
}
