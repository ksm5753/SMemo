package com.example.pita.smemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        SharedPreferences sharedPreferences = getSharedPreferences("first", MODE_PRIVATE);
        String savedString = sharedPreferences.getString("sampleString", "");
        editText.setText(savedString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("SAVE");
                builder.setMessage("저장하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("first", MODE_PRIVATE);
                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

                        // 데이터를 기록한다.
                        sharedPreferencesEditor.putString("sampleString", editText.getText().toString());
                        sharedPreferencesEditor.apply();
                        Toast.makeText(MainActivity.this, editText.getText() + "이(가) 저장되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
                return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                sendIntent.setType("text/plan");
                sendIntent.createChooser(sendIntent, "");
                startActivity(sendIntent);
                return true;

            case R.id.option:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setIcon(R.drawable.ic_settings_black_24dp);
                alertBuilder.setTitle("어느 색으로 하시겠습니까?");

                // List Adapter 생성
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.select_dialog_singlechoice);
                adapter.add("RED");
                adapter.add("GREEN");
                adapter.add("BLUE");
                adapter.add("기본값 설정");

                // 버튼 생성
                alertBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                alertBuilder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                LinearLayout activity_main = (LinearLayout) findViewById(R.id.activity_main);
                                switch (id){
                                    case 0: editText.setTextColor(Color.rgb(255,0,0)); break;
                                    case 1: editText.setTextColor(Color.rgb(0,255,0)); break;
                                    case 2: editText.setTextColor(Color.rgb(0,0,255)); break;
                                    case 4: editText.setTextColor(Color.rgb(0,0,0)); break;
                                }
                                // AlertDialog 안에 있는 AlertDialog
                                String strName = adapter.getItem(id);
                            }
                        });
                alertBuilder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
