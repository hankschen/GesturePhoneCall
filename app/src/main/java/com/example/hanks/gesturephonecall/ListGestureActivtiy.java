package com.example.hanks.gesturephonecall;

import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ListGestureActivtiy extends AppCompatActivity {
    ListView lv;
    GestureLibrary library;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findviews();
    }

    void findviews(){
        lv=(ListView)findViewById(R.id.listView);
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file=new File(path,"gesture");
        library=GestureLibraries.fromFile(file);

        SharedPreferences phoneBook=getSharedPreferences("phoneBook",MODE_PRIVATE);

        //將手勢資料放到ArrayList及Set<String>內
        //Set<String>集合的特性是會將同名稱的項目,只放一個進來
        ArrayList<HashMap<String,Object>> data=new ArrayList<>();
        if(library.load()){
            Set<String> gesNames=library.getGestureEntries();
            for(String gesName:gesNames ){
                ArrayList<Gesture> gestures=library.getGestures(gesName);
                for(Gesture ges:gestures){
                    HashMap<String,Object> items=new HashMap<>();
                    items.put("pic",ges.toBitmap(80,80,5, Color.BLUE));
                    items.put("name",gesName);
                    items.put("phone",phoneBook.getString(gesName,"Null"));

                    data.add(items);
                }
            }
        }


        //將ArrayAdapter設定到ListView上顯示
        GestureAdapter gesAdapter=new GestureAdapter(this,data);
        lv.setAdapter(gesAdapter);
    }
}
