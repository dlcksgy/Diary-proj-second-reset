package com.apress.gerber.diary_proj;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arduino on 2017-06-05.
 */

public class DiaryListActivity extends Activity {

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
    //월을 나타내는 디렉토리 목록을 저장할 문자열 배열
    private ArrayList<String> monthList = new ArrayList<String>();
    private int month;
    //년도를 나타낼 디렉토리 목록을 저장할 문자열 배열
    private ArrayList<String> yearList = new ArrayList<String>();
    private int year;

    //일기 리스트를 저장할 문자열 배열
    private ArrayList<String> diaryList = new ArrayList<String>();



    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_diary_list);

        final Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        final Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

        final ListView list = (ListView) findViewById(R.id.diaryList);

        Button setListButton = (Button) findViewById(R.id.setListButton);

        File diaryDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries");

        File temp[] = diaryDir.listFiles();

        for(int i = 0; i < temp.length; i++){
            this.yearList.add(temp[i].getName());
        }
        //year spinner 설정
        ArrayAdapter yearAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, this.yearList);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        if(shouldAskPermissions()){
            askPermissions();
        }
        File monthDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/2017/6");
        File temp2[] = monthDir.listFiles();
        for(int i = 0; i < temp2.length; i++){
            diaryList.add(temp2[i].getName());
        }



        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                monthList.clear();  //기존에있던 monthList를 비워준다
                year = Integer.parseInt(yearList.get(position));
                File yeardir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/" + yearList.get(position));

                File temp[] = yeardir.listFiles();
                for(int i = 0; i < temp.length; i++){
                    monthList.add(temp[i].getName());  //monthList에 새로운 값들을 넣어준다.
                }
                //월 스피너에 값들을 넣어준다.
                ArrayAdapter monthAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, monthList);
                monthSpinner.setAdapter(monthAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        //month spinner 설정
        ArrayAdapter monthAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                diaryList.clear();
                month = Integer.parseInt(monthList.get(position));
 /*
                File monthDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/" + year + "/" + monthList.get(position));
                File temp[] = monthDir.listFiles();
                for(File file:temp){
                    diaryList.add(file.getName());
                }
                ArrayAdapter diaryAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, diaryList);
                list.setAdapter(diaryAdapter);
*/


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        setListButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        File monthDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/" + year + "/" + month );
                        File temp[] = monthDir.listFiles();
                for(File file:temp){
                    diaryList.add(file.getName());
                }

                ArrayAdapter diaryAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, diaryList);
                list.setAdapter(diaryAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id){
                        String diaryName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Diaries/" + year + "/" + month +"/"+ (String) parent.getItemAtPosition(position); //경로가 없어서 파일을 찾지 못했었음.
                        Intent intent = new Intent(getApplicationContext(), NormalDiaryViewActivity.class);
                        intent.putExtra("diaryName",diaryName);
                        startActivity(intent);

                    }
                });

            }
        });



    }
}
