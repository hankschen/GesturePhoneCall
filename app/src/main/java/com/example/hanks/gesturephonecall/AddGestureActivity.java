package com.example.hanks.gesturephonecall;

import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class AddGestureActivity extends AppCompatActivity{
    EditText etPhoneNumber,etGestureName;
    GestureOverlayView overlayView;
    Button btnAddGesture,btnClearGesture;
    String gesName,phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgesture);
        findviews();
    }

    void findviews(){
        etPhoneNumber=(EditText)findViewById(R.id.editText);
        etGestureName=(EditText)findViewById(R.id.editText2);
        overlayView=(GestureOverlayView)findViewById(R.id.gestureOverlayView2);
        overlayView.addOnGestureListener(gestureListener);
        btnAddGesture=(Button)findViewById(R.id.button3);
        btnClearGesture=(Button)findViewById(R.id.button4);
    }

    //以OnGestureListener判斷該輸入的都有輸入,就可儲存
    GestureOverlayView.OnGestureListener gestureListener=new GestureOverlayView.OnGestureListener() {
        @Override
        //開始畫手勢就執行此方法
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            gesName=etGestureName.getText().toString();
            phoneNum=etPhoneNumber.getText().toString();
            if(gesName.equals("")){
                Toast.makeText(AddGestureActivity.this,"請輸入手勢名稱",Toast.LENGTH_SHORT).show();
                return;
            }
            if(phoneNum.equals("")){
                Toast.makeText(AddGestureActivity.this,"請輸入電話號碼",Toast.LENGTH_SHORT).show();
                return;
            }
            btnAddGesture.setEnabled(true); //讓新增手勢按鈕有作用

        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {

        }

        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    };

    public void addGesture(View v){
        //先設定手勢資料庫檔案儲存的路徑及檔案名稱
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file=new File(path,"gesture");
        //從GestureLibraries設定檔案後,得到GestureLibrary型別的物件
        GestureLibrary library= GestureLibraries.fromFile(file);

        //要先將手勢資料Load到記憶體內,下次再Save時才不會蓋掉原來的手勢檔;這樣就會產生另一個手勢檔,否則只會有一個
        //先不管有沒有手勢檔,先Load出來,判斷Library.load() is true
        if(library.load()){
            //若有之前的手勢檔則先讀取後再儲存，否則只會儲入最後一個手勢
            library.addGesture(gesName,overlayView.getGesture());
            library.save();
            Toast.makeText(AddGestureActivity.this,"手勢新增成功",Toast.LENGTH_SHORT).show();
            overlayView.clear(true);
            etGestureName.setText("");
            etPhoneNumber.setText("");
        }else{
            //第一次沒有檔案的做法
            library.addGesture(gesName,overlayView.getGesture());
            library.save();
            Toast.makeText(AddGestureActivity.this,"手勢新增成功",Toast.LENGTH_SHORT).show();
            overlayView.clear(true);
            etGestureName.setText("");
            etPhoneNumber.setText("");
        }

        //加入電話號碼code
        SharedPreferences phoneBook=getSharedPreferences("phoneBook",MODE_PRIVATE);
        SharedPreferences.Editor editor=phoneBook.edit();
        editor.putString(gesName,phoneNum);
        editor.commit();

    }

    public void clearGesture(View v){
        overlayView.clear(true);
    }
}
