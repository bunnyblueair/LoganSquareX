package io.logansquarex.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.logansquarex.core.LoganSquare;
import io.logansquarex.core.LoganSquareX;
import io.logansquarex.demo.androidlib.LibBean;
import io.logansquarex.demo.model.HelloBean;
import io.logansquarex.demo.model.HelloList;

public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        HelloList helloList=new HelloList();
        List<HelloBean> list=new ArrayList<>();
        list.add(new HelloBean());
        list.add(new HelloBean());
        helloList.setCode(0);
        helloList.setData(list);
        try {
            Log.e("debug", LoganSquare.serialize(helloList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("debug", LoganSquareX.serializeListSimple(list));
        LibBean li=new LibBean();
        li.setData("libtest");
        Log.e("debug", LoganSquareX.serialize(li));
    }
}
