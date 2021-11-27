package com.example.realtimechat.datalayer;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.realtimechat.app.App;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SPControl {

    private static final String APP_PREFS_NAME = "appPrefsName";

    //Класс который описывает управление префами - получить префы, обновить префы
    private static SPControl SPControl = new SPControl();

    public static SPControl getInstance() {
        return SPControl;
    }

    public SharedPreferences getAppPrefs() {
        return App.getAppContext().getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
    }

    //Метод для обновления значения в префах - Возвращает преф едитор
    private SharedPreferences.Editor getPrefsEditor() {
        return App.getAppContext().getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE).edit();
    }

    //метод обновления префов
    public void updatePrefs(String key, String valueStr) {
        getPrefsEditor().putString(key, valueStr).apply();
    }

    public void updatePrefs(String key, Boolean valueBol) {
        getPrefsEditor().putBoolean(key, valueBol).apply();
    }

    public void updatePrefs(String key, Float valueFloat) {
        getPrefsEditor().putFloat(key, valueFloat).apply();
    }

    public void updatePrefs(String key, Integer valueInt) {
        getPrefsEditor().putInt(key, valueInt).apply();
    }

    public void updatePrefs(String key, Long valueLong) {
        getPrefsEditor().putLong(key, valueLong).apply();
    }

    public void updatePrefs(String key, List<String> stringList) {
        getPrefsEditor().putStringSet(key, new HashSet<>(stringList)).apply();
    }

    public void updatePrefs(final String key, final double value) {
        getPrefsEditor().putLong(key, Double.doubleToRawLongBits(value)).apply();
    }

    //Получение объектов из префов
    public String getStringPrefs(String strName) {
        return getAppPrefs().getString(strName, "");
    }

    public Boolean getBoolPrefs(String strName) {
        return getAppPrefs().getBoolean(strName, false);
    }

    public Float getFloatPrefs(String strName) {
        return getAppPrefs().getFloat(strName, 0);
    }

    public Integer getIntPrefs(String strName) {
        return getAppPrefs().getInt(strName, 0);
    }

    public Long getLongPrefs(String strName) {
        return getAppPrefs().getLong(strName, 0);
    }

    public List<String> getStringListPrefs(String key) {
        Set<String> stringSet = getAppPrefs().getStringSet(key, new HashSet<>());
        if (stringSet != null) {
            return new ArrayList<>(stringSet);
        } else return new ArrayList<>();
    }

    public double getDoublePrefs(final String key) {
        return Double.longBitsToDouble(getAppPrefs().getLong(key, Double.doubleToLongBits(0.0)));
    }
}
