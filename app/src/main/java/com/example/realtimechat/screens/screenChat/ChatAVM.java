package com.example.realtimechat.screens.screenChat;

import static android.telephony.AvailableNetworkInfo.PRIORITY_HIGH;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.realtimechat.MainActivity;
import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.model.Message;
import com.example.realtimechat.instruments.AppStatements;
import com.example.realtimechat.instruments.Constants;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatAVM extends AndroidViewModel {
    private final AuthRepo authRepo;
    private final String uid;
    private final NotificationManager notificationManager;
    private String userName;
    private final ArrayList<Message> mListMessages = new ArrayList<>();
    private Message lastMessage;
    private final MutableLiveData<ArrayList<Message>> messageMutableLiveData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ChatAVM(@NonNull Application application) {
        super(application);
        AppStatements.sendOnline();
        authRepo = new AuthRepo();
        messageMutableLiveData = new MutableLiveData<>();
        uid = SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID);
        if (SPControl.getInstance().getBoolPrefs(Constants.APP_PREFS_IS_AUTH)) {
            authRepo.readUserFromDataBase(uid, user -> userName = user.name);
        }
        initList();
        notificationManager = (NotificationManager) getApplication().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    //Инициализация списка сообщений
    public void initList() {
        Intent intent = new Intent(getApplication(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_MUTABLE);
        //Получение сообщений из базы данных
        authRepo.getMessages(new AuthRepo.DataListener<Message>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void data(Message message) {
                mListMessages.add(message);
                //Настройка уведомлений
                if (!SPControl.getInstance().getBoolPrefs(Constants.APP_PREFS_IS_ACTIVE)
                        && !message.getUid().equals(SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID))) {
                    NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(getApplication().getApplicationContext(), Constants.CHANNEL_ID)
                                    .setAutoCancel(true)
                                    .setSmallIcon(R.drawable.ic_send_mail)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentTitle(message.getName())
                                    .setContentIntent(pendingIntent)
                                    .setContentText(message.getMessageText())
                                    .setPriority(PRIORITY_HIGH)
                                    .setDefaults(Notification.DEFAULT_ALL);
                    createChannelIfNeeded(notificationManager);
                    notificationManager.notify(Constants.NOTIFY_ID, notificationBuilder.build());
                }
                messageMutableLiveData.postValue(mListMessages);
            }
            @Override
            public void error(String error) {
                Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Метод отправки сообщения
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMessage(String messageText) {
        //Проверка на заполненность сообщения
        if (messageText != null) {
            //Отправление сообщения в базу данных
            authRepo.sendMessage(messageText,
                    //Получение реалього времени по Москве
                    ZonedDateTime
                            .ofInstant(Clock.systemUTC().instant(), ZoneId.of("Europe/Moscow"))
                            .format(DateTimeFormatter.ofPattern("HH:mm")),
                    userName, uid);
            //Добавление сообщения на экран
        } else {
            Toast.makeText(getApplication(), "Упс! Кажется сообщение пустое(", Toast.LENGTH_SHORT).show();
        }
    }

    public void activityStatus(Boolean isActive) {
        SPControl.getInstance().updatePrefs(Constants.APP_PREFS_IS_ACTIVE, isActive);
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public MutableLiveData<ArrayList<Message>> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }
}
