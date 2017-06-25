package com.apress.gerber.diary_proj;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arduino on 2017-05-30.
 *
 * 2017-06-4 : 한글저장시 텍스트파일에서 한글깨짐현상 발생. 달력과 리스트뷰를 통해 일기 보는 부분을 구현해야함.
 *
 */

public class NormalDiaryActivity extends AppCompatActivity {


    //Api23부터 permission함수와 ask함수가 자바파일에도 있어야  외부메모리에 파일쓰기 권한을 가질 수있다.
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };

        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }



    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_normal_diary);
        final EditText diaryEditText = (EditText) findViewById(R.id.diaryEditText);
        Button saveButton = (Button) findViewById(R.id.saveButton);


        Button dateButton = (Button) findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");

                SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");

                SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");

                EditText yearText = (EditText) findViewById(R.id.yearText);
                EditText monthText = (EditText) findViewById(R.id.monthText);
                EditText dayText = (EditText) findViewById(R.id.dayText);
                Toast.makeText(getApplicationContext(),CurYearFormat.format(date),Toast.LENGTH_SHORT).show();
                yearText.setText(CurYearFormat.format(date));
                monthText.setText(CurMonthFormat.format(date));
                dayText.setText(CurDayFormat.format(date));
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener(){   //저장버튼 눌렀을 시 동작부
            @Override
            public void onClick(View v){

                //텍스트로부터 정보를 받아와서 다이어리객체를 만든다.



                EditText yearText = (EditText) findViewById(R.id.yearText);
                EditText monthText = (EditText) findViewById(R.id.monthText);
                EditText dayText = (EditText) findViewById(R.id.dayText);
                EditText titleText = (EditText) findViewById(R.id.titleText);
                Toast.makeText(getApplicationContext(),"저장이 되려나...",Toast.LENGTH_SHORT).show();
                Diary diary = new Diary(getApplicationContext(), diaryEditText.getText().toString());
                diary.setYear(Integer.parseInt(yearText.getText().toString()));
                diary.setMonth(Integer.parseInt(monthText.getText().toString()));
                diary.setDay(Integer.parseInt(dayText.getText().toString()));
                diary.setTitle(titleText.getText().toString());

                //디렉터리가 없으면 디렉터리를 만든다.
                File dir = new File(String.format(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/%s/%s",diary.getYear(),
                                                                                 diary.getMonth()));
                if(!dir.exists()) dir.mkdirs();




                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/" + diary.getYear() +
                                                                     "/" + diary.getMonth() +
                                                                     "/" + diary.getDay() +"일  "+ diary.getTitle() + "  .txt");

                FileWriter fw = null;
                BufferedWriter bufw = null;
                if(shouldAskPermissions()){
                    askPermissions();
                }
                try {
                    bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()),"UTF8"));
                    bufw.write(diary.getDiaryText());  //여기서 텍스트 내용에 제목을 쓰게 되어 있었음... 그걸 이제야찾았네
                    Toast.makeText(getApplicationContext(),"저장완료!",Toast.LENGTH_SHORT).show();
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),"File Error : "+ex.toString(),Toast.LENGTH_SHORT).show();
                }
                try{
                    if(bufw !=null) bufw.close();
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),"File Error : "+ex.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        }); //저장버튼 동작부 끝
    }
}









