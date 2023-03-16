package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1 , e2;
    ContentValues contentValues = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.edit1);
        e2 = (EditText) findViewById(R.id.edit2);
    }
    public void save_data(View view){
        contentValues.put("emp_name" , e1.getText().toString());
        contentValues.put("role" , e2.getText().toString());

        Uri uri = getContentResolver().insert(CompanyProvider.content_uri , contentValues);
        Toast.makeText(this , uri.toString() , Toast.LENGTH_SHORT).show();
    }

    public void load_data(View view){
        Cursor cr = getContentResolver().query(CompanyProvider.content_uri , null,null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();
        while(cr.moveToNext()){
        int id = cr.getInt(0);
        String s1 = cr.getString(1);
        String s2 = cr.getString(2);
        stringBuilder.append(id+" "+s1+" "+s2+"\n");
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }
}