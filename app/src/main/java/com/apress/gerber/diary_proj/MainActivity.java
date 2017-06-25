package com.apress.gerber.diary_proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button diaryListButton = (Button) findViewById(R.id.diaryListButton);
        diaryListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),DiaryListActivity.class);
                startActivity(intent);
            }
        });


        Button normalDiaryButton = (Button) findViewById(R.id.normalDiaryButton);
        normalDiaryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),NormalDiaryActivity.class);
                startActivity(intent);
            }
        });

        Button pictureDiaryButton = (Button) findViewById(R.id.pictureDiaryButton);
        pictureDiaryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //사진일기 레이어 호출

            }
        });

        Button shareDiaryButton = (Button) findViewById(R.id.shareDiaryButton);
        shareDiaryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //교환일기 레이어 호출

            }
        });




        Button calendarButton = (Button) findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("정말로 종료하시겠습니까?");
                builder.setTitle("종료알림창")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("종료알림창");
                alert.show();

            }
        });





    }

}
