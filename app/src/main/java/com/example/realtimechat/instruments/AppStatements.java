package com.example.realtimechat.instruments;

public enum AppStatements {
    ONLINE("В сети"),
    OFFLINE("Не в сети");

    AppStatements(String appState) {
    }

    public static void sendOnline(){
        Constants.PATH_TO_USER_STATUS.setValue(ONLINE.toString());
    }

    public static void sendOffline() {
        Constants.PATH_TO_USER_STATUS.setValue(OFFLINE.toString());
    }
}
