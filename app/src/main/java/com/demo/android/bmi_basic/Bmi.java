package com.demo.android.bmi_basic;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Bmi extends AppCompatActivity {

    private Button calc_button;
    private EditText field_height;
    private EditText field_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        findViews();
        setListeners();
    }

    private void findViews() {
        calc_button = (Button) findViewById(R.id.submit);
        field_height = (EditText) findViewById(R.id.height);
        field_weight = (EditText) findViewById(R.id.weight);
    }

    private void setListeners() {
        calc_button.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Bmi.this, Report.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT", field_height.getText().toString());
            bundle.putString("KEY_WEIGHT", field_weight.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void openOptionsDialog() {
        new AlertDialog.Builder(Bmi.this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_button_confirm, null)
                .setNeutralButton(R.string.homepage_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                // Home Page
                                Uri uri = Uri.parse("http://tw.yahoo.com/");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(R.string.dialog_button_cancel, null)
                .show();
    }

    protected static final int MENU_ABOUT = Menu.FIRST;
    protected static final int MENU_QUIT = Menu.FIRST + 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ABOUT, 0, R.string.menu_about)
                .setIcon(android.R.drawable.ic_menu_help)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0, MENU_QUIT, 0, R.string.menu_quit)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ABOUT:
                openOptionsDialog();
                break;
            case MENU_QUIT:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
