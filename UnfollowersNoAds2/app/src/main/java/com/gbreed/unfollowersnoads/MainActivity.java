package com.gbreed.unfollowersnoads;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myData = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        myData.execSQL("CREATE TABLE IF NOT EXISTS users(user VARCHAR, pass VARCHAR, followers VARCHAR, following VARCHAR)");

        Cursor c = myData.rawQuery("SELECT * FROM users", null);
        int nameIndex = c.getColumnIndex("user");
        int passIndex = c.getColumnIndex("pass");
        int followerIndex = c.getColumnIndex("followers");
        int followingIndex = c.getColumnIndex("following");
        c.moveToFirst();

        if(c.isAfterLast())
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else
        {

        }

        c.close();
        myData.close();
        finish();
    }
}
