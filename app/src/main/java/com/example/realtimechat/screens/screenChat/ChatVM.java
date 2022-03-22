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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.model.Message;
import com.example.realtimechat.instruments.AppStatements;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.myCallBack;
import com.example.realtimechat.screens.screenChatInfo.ChatInfoActivity;
import com.example.realtimechat.screens.screenUserProfile.UserProfileActivity;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ChatVM extends AndroidViewModel {
    private final AuthRepo authRepo;
    private final String uid;
    private final NotificationManager notificationManager;
    private String userName;
    private final ArrayList<Message> mListMessages = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ChatVM(@NonNull Application application) {
        super(application);
        AppStatements.sendOnline();
        authRepo = new AuthRepo();
        uid = SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID);
        if(SPControl.getInstance().getBoolPrefs(Constants.APP_PREFS_IS_AUTH)){
            authRepo.readUserFromDataBase(uid, user -> userName = user.name);
        }
        notificationManager = (NotificationManager) getApplication().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    //Инициализация RecyclerView
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void initRecyclerView(RecyclerView recyclerView, myCallBack<Boolean> myCallBack) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setStackFromEnd(true);

        MessageAdapter adapter = new MessageAdapter(getApplication(), mListMessages);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = new Intent(getApplication(), ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_MUTABLE);

        //Получение сообщений из базы данных
        authRepo.getMessages(new AuthRepo.DataListener<Message>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void data(Message message) {
                mListMessages.add(message);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
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
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(1);
                myCallBack.data(true);
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

    public void goToSettings() {
        getApplication().startActivity(new Intent(getApplication(), UserProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void goToChatInfo() {
        getApplication().startActivity(new Intent(getApplication(), ChatInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
