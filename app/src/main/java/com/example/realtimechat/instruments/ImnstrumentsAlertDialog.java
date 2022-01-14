package com.example.realtimechat.instruments;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.realtimechat.R;
import com.example.realtimechat.baseInterfaces.MainThreadRun;
import com.example.realtimechat.baseRX.RxController;
import com.example.realtimechat.datalayer.datamanager.RxData;

public class ImnstrumentsAlertDialog {


    private static DialogInterface.OnClickListener getDismissClicker() {
        return (dialog, which) -> dialog.dismiss();
    }
    private static void showDialog(final AlertDialog.Builder builder) {
        RxController.acceptMainThread(new MainThreadRun() {
            @Override
            public void run() {
                builder.show();
            }
        });
    }

    //Диалоговое окно с двумя кнопками - ДА  НЕТ
    public static void showDialogTwoButtons(String title, String message,
                                     String textRight, String textLeft,
                                     DialogInterface.OnClickListener positiveClick,
                                     DialogInterface.OnClickListener negativeClick) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RxData.activityBehaviorRelay.getValue())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_warning)
                .setPositiveButton(textRight, positiveClick != null ?
                        positiveClick
                        :
                        getDismissClicker())
                .setNegativeButton(textLeft, negativeClick != null ?
                        negativeClick
                        :
                        getDismissClicker());
        showDialog(builder);
    }
}
