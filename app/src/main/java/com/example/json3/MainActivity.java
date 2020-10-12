package com.example.json3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//public class Naisen{
//    public String
//}
public class MainActivity extends AppCompatActivity {

    //widget宣言
    TextView txt01;
    Button btn01;
    TextView txt02;
    Button callbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Widget初期化
        txt01 = findViewById(R.id.txt01);
        btn01 = findViewById(R.id.btn01);
        txt02 = findViewById(R.id.txt02);
        callbutton = findViewById(R.id.callbutton);


        txt01.setText("こさい");

//        try{
//            JSONArray arr = new JSONArray("[{aaa:1},{aaa:2}]");
//            for (int i=0; i < arr.length(); i++) {
//                JSONObject o = arr.getJSONObject(i);
//                int b = o.getInt("aaa");
//                int c = 0;
//                JSONArray arr = new JSONArray("[{seq:1,floor:1F,busho:FR,number:110,name:テクノ}]");
//                for (int i=0; i < arr.length(); i++) {
//                    JSONObject o = arr.getJSONObject(i);
//                    int seq = o.getInt("seq");
//                    String floor = o.getString("floor");
//                    String busho = o.getString("busho");
//                    String number = o.getString("number");
//                    String name = o.getString("name");
//                    int m =0;
//                    txt01.setText(seq +" "+ floor +" "+ busho +" "+ number +" "+ name);
//
//                }
//            int a = 0;
//        }catch(Exception e){
//            Log.e("Hoge",e.getMessage());
//        }





        //httpリクエスト
//        try{
//            //okhttpを利用するカスタム関数（下記）
////            httpRequest("https://13.112.245.185/wakarumon/naisen/ajax/get_naisen.php");
//            httpRequest("http://13.112.245.185:8000/wakarumon/naisen/ajax/get_naisen.php");
////            httpRequest("https://google.com");
//        }catch(Exception e){
//            Log.e("Hoge",e.getMessage());
//        }

        //ボタンクリック
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //httpリクエスト
                try{
                    //okhttpを利用するカスタム関数（下記）
//                    httpRequest("https://13.112.245.185/wakarumon/naisen/ajax/get_naisen.php");
                    httpRequest("http://13.112.245.185:8000/wakarumon/naisen/ajax/get_naisen.php");
//                    httpRequest("https:google.com");
                }catch(Exception e){
                    Log.e("Hoge",e.getMessage());
                }

            }
        });
    }

    void httpRequest(String url) throws IOException{

        //OkHttpClinet生成
        OkHttpClient client = new OkHttpClient();

        //request生成
        Request request = new Request.Builder()
                .url(url)
                .build();

        //非同期リクエスト
        client.newCall(request)
                .enqueue(new Callback() {

                    //エラーのとき
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("Hoge",e.getMessage());
                    }

                    //正常のとき
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        //response取り出し
                        final String jsonStr = response.body().string();
                        Log.d("Hoge","jsonStr=" + jsonStr);

                        //JSON処理
                        try{
                            //jsonパース
//                            JSONObject json = new JSONObject(jsonStr);
//                            final String seq = json.getString("seq");
//                            final String floor = json.getString("floor");
//                            final String busho = json.getString("busho");
//                            final String number = json.getString("number");
//                            final String name = json.getString("name");
                            JSONArray arr = new JSONArray(jsonStr);
                            for (int i=0; i < arr.length(); i++) {
                                JSONObject o = arr.getJSONObject(i);
                                final int seq = o.getInt("seq");
                                final String floor = o.getString("floor");
                                final String busho = o.getString("busho");
                                final String number = o.getString("number");
                                final String name = o.getString("name");


                                //親スレッドUI更新
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt01.setText(seq +" "+ floor +" "+ busho +" "+ number +" "+ name);
                                        txt02.setText(number);
                                    }
                                });
                            }


                        }catch(Exception e){
                            Log.e("Hoge",e.getMessage());
                        }

                    }
                });
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+txt02);
                Intent i = new Intent(Intent.ACTION_CALL,uri);
                startActivity(i);
            }
        });
    }


}