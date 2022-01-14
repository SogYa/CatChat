package com.example.realtimechat.baseRX;

import com.jakewharton.rxrelay2.Relay;
import com.example.realtimechat.baseInterfaces.MainThreadRun;


import java.util.Map;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class RxController {
    private final static String TAG = RxController.class.getSimpleName();

    private static RxController rxController = new RxController();

    public static RxController getInstance() {
        return rxController;
    }

    private ArrayMap<String, Disposable> disposableArrayMap = new ArrayMap<>(); //Массив с подписчиками


    //Подписка на PublishRelay
    public <T> void addPublishRelayCallback(Relay<T> relay,
                                            Consumer<T> consumer,
                                            String disposeKey) {
        addPublishRelayCallback(relay, consumer, null, disposeKey);
    }

    public <T> void addPublishRelayCallback(Relay<T> relay, Consumer<T> consumer, Scheduler scheduler, String disposeKey) {
        disposeCallback(disposeKey);
        if (scheduler != null) {
            disposableArrayMap.put(disposeKey, relay
                .observeOn(scheduler)
                .subscribe(consumer));
        } else {
            disposableArrayMap.put(disposeKey, relay
                .subscribe(consumer));
        }
    }

    //Добавление синглObserver
    public <T> void addPublishRelaySingle(Relay<T> relay,
                                          MyDisposableObserver<T> disposableObserver) {
        addPublishRelaySingle(relay, disposableObserver, null);
    }

    public <T> void addPublishRelaySingle(Relay<T> relay,
                                          MyDisposableObserver<T> disposableObserver,
                                          Scheduler scheduler) {
        if (scheduler != null) {
            relay
                .observeOn(scheduler)
                .singleOrError()
                .subscribe(disposableObserver);
        } else {
            relay
                .singleOrError()
                .subscribe(disposableObserver);
        }
    }

    //Отписка из массива
    public void disposeCallback(String disposableKey) {
        if (disposableArrayMap.containsKey(disposableKey)) { //Если в массиве подписчиков уже есть такой
            //Мы его отписываем и удаляем
            Disposable d = disposableArrayMap.get(disposableKey);
            if (d != null) d.dispose();
            disposableArrayMap.remove(disposableKey);
        }
    }

    //Метод очистки Rx!
    public void clearRx() {
        for (Map.Entry<String, Disposable> entry : disposableArrayMap.entrySet()) {
            entry.getValue().dispose();
        }
        disposableArrayMap.clear();
    }

    public static void acceptMainThread(@NonNull MainThreadRun mainThreadRun) {
        Observable.just(mainThreadRun)
            .singleOrError()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(new Consumer<MainThreadRun>() {
                @Override
                public void accept(MainThreadRun mainThreadRun) {
                    mainThreadRun.run();
                }
            })
            .subscribe(MyDisposableObserver.<MainThreadRun>create());
    }
}
