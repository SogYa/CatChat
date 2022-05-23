package com.example.realtimechat.domain;


import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realtimechat.data.model.Message;
import com.example.realtimechat.data.model.User;
import com.example.realtimechat.instruments.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class FirebaseRepository {

    //Интерфейс коллбека
    public interface DataListener<T> {
        void data(T t);

        default void error(String error) {
        }
    }

    private final FirebaseAuth fbAuthReference;
    private final FirebaseDatabase mDat;
    private String photoUrl;

    public FirebaseRepository() {
        this.fbAuthReference = FirebaseAuth.getInstance();
        mDat = FirebaseDatabase.getInstance();
    }

    //Регистрация пользователя в FireBase
    public void registration(@NonNull String email, @NonNull String password,
                             DataListener<String> dataListener) {
        fbAuthReference.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataListener.data(Objects.requireNonNull(task.getResult().getUser())
                                .getUid());
                    } else {
                        if (task.getException() != null) {
                            dataListener.error(task.getException().getMessage());
                        } else {
                            dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                        }
                    }
                });
    }

    //Вход пользователя в FireBase
    public void signIn(@NonNull String email, @NonNull String password,
                       DataListener<String> dataListener) {
        fbAuthReference.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dataListener.data(Objects.requireNonNull(task.getResult().getUser()).getUid());
            } else {
                if (task.getException() != null) {
                    dataListener.error(task.getException().getMessage());
                } else {
                    dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                }
            }
        });
    }

    //Получение данных пользователя из FireBase
    public void readUserFromDataBase(String uid, DataListener<User> dataListener) {
        Constants.PATH_TO_USER.child(uid).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        dataListener.data(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dataListener.error(error.getMessage());
                    }
                }
        );
    }

    //Метод создание модели пользователя в базе данных
    public void createNewUser(String name, DataListener<String> dataListener) {
        String uid = Objects.requireNonNull(fbAuthReference.getCurrentUser()).getUid();
        String userEmail = fbAuthReference.getCurrentUser().getEmail();
        User user = new User(name, userEmail, uid, null);
        Constants.PATH_TO_USER.child(uid).setValue(user);
        dataListener.data(user.uid);
    }

    //Создание модели сообщения в базе данных
    public void sendMessage(String messageText, String time, String name, String uid) {
        Constants.PATH_TO_MESSAGES
                .push()
                .setValue(new Message(messageText, time, name, uid));
    }

    //Выход из аккаунта FireBase
    public void logOut() {
        fbAuthReference.signOut();
    }

    //Смена пароля аккаунта FireBase
    public void passwordChange(String email, DataListener<Object> dataListener) {
        fbAuthReference.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dataListener.data(true);
            } else {
                if (task.getException() != null) {
                    dataListener.error(task.getException().getMessage());
                } else {
                    dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                }
            }
        });
    }

    public void getMessages(DataListener<Message> dataListener) {
        Constants.PATH_TO_MESSAGES.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {
                dataListener.data(snapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot,
                                       @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendImageToStorage(Uri uri, DataListener<Object> dataListener) {
        Constants.STORAGE_PATH_TO_AVATAR.child(Objects.requireNonNull(fbAuthReference.getCurrentUser()).getUid()).putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dataListener.data(uri);
            } else {
                if (task.getException() != null) {
                    dataListener.error(task.getException().getMessage());
                } else {
                    dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                }
            }
        });
    }
    public void setImage(String uid){
//        Constants.PATH_TO_USER.child(uid).child("photoUrl").setValue(photoUrl);
        Constants.STORAGE_PATH_TO_AVATAR
                .child(uid).getDownloadUrl()
                .addOnCompleteListener(task ->{
                    if( task.isSuccessful()){
                        String photoUrl1 = task.getResult().toString();
                        mDat.getReference().child(Constants.NODE_USERS)
                                .child(uid)
                                .child(Constants.CHILD_PHOTO_URL)
                                .setValue(photoUrl1)
                                .addOnCompleteListener(task1 -> Constants.USER.photoUrl = photoUrl1);
                    }

        });
    }

    public void downloadImage(StorageReference path, DataListener<String> dataListener) {
        path.child(Objects.requireNonNull(fbAuthReference.getCurrentUser()).getUid()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        photoUrl = task.getResult().toString();
                        mDat.getReference().child(Constants.NODE_USERS)
                                .child(Objects.requireNonNull(fbAuthReference.
                                        getCurrentUser()).getUid())
                                .child(Constants.CHILD_PHOTO_URL)
                                .setValue(photoUrl)
                                .addOnCompleteListener(task1 -> Constants.USER.photoUrl = photoUrl);
                        dataListener.data(photoUrl);
                    } else {
                        if (task.getException() != null) {
                            dataListener.error(task.getException().getMessage());
                        } else {
                            dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                        }
                    }
                });
    }

    public void readAllUsers(DataListener<User> dataListener) {
        Constants.PATH_TO_USER.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {
                dataListener.data(snapshot.getValue(User.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot,
                                       @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateUserName(String name) {
        Constants.PATH_TO_USER.child(Objects.requireNonNull(fbAuthReference.getCurrentUser()).
                getUid()).child("name").setValue(name);
    }
}