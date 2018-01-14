package com.demo.android.bmi_basic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Report extends AppCompatActivity {

    private TextView view_result;
    private TextView view_suggest;
    private Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        findViews();
        setListeners();
            try {
                double BMI = calcBMI();
                showResult(BMI);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(Report.this, R.string.input_error,
                        Toast.LENGTH_SHORT).show();
            }
    }

    private void findViews() {
        view_result = (TextView) findViewById(R.id.result);
        view_suggest = (TextView) findViewById(R.id.suggest);
        button_back = (Button) findViewById(R.id.report_back);
    }

    private void setListeners() {
        button_back.setOnClickListener(backMain);
    }

    private Button.OnClickListener backMain = new Button.OnClickListener() {
        public void onClick(View v) {

            Report.this.finish();
        }
    };

    private double calcBMI() {
        Bundle bunde = this.getIntent().getExtras();
        double height = Double.parseDouble(bunde.getString("KEY_HEIGHT"))/100;
        double weight = Double.parseDouble(bunde.getString("KEY_WEIGHT"));
        return weight / (height * height);
    }

    private void showResult(double BMI) {
        DecimalFormat nf = new DecimalFormat("0.00");
        view_result.setText("Your BMI is " + nf.format(BMI));
        if (BMI > 25) {
            showNotification (BMI);
            view_suggest.setText(R.string.advice_heavy);
        } else if (BMI < 20) {
            view_suggest.setText(R.string.advice_light);
        } else {
            view_suggest.setText(R.string.advice_average);
        }
    }

    //API 16以上建議使用
    protected void showNotification (double BMI) {
        NotificationManager barManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, Bmi.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification barMsg = new Notification.Builder(this)
                // 通知訊息在狀態列的文字
                .setTicker("歐，你過重囉！")
                // 通知訊息在訊息面板的標題
                .setContentTitle("您的 BMI 值過高")
                // 通知訊息在訊息面板的內容文字
                .setContentText("通知監督人")
                // 通知訊息的圖示
                .setSmallIcon(R.mipmap.ic_launcher)
                // 點擊訊息面版後會自動移除狀態列上的通知訊息
                .setAutoCancel(true)
                // 等待使用者向下撥動狀態列後點擊訊息面版上的訊息才會開啟指定Activity的畫面
                .setContentIntent(pendingIntent)
                // API Level 16開始支援build()，並建議不要使用getNotification()
                .build();
        barManager.notify(0, barMsg);
    }
}
