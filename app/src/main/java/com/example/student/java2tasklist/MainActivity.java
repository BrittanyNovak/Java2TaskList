package com.example.student.java2tasklist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ListView lvTasks;

    private ArrayList<String> array_list = new ArrayList<String>() {{
        array_list.add("New Pictures");
        array_list.add("Rub Clit");
        array_list.add("Do not Cum");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter arrayAdapter=new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                array_list
        );

        lvTasks = (ListView)findViewById(R.id.lvTasks);
        lvTasks.setAdapter(arrayAdapter);
//        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                int id_To_Search = arg2 + 1;
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", id_To_Search);
//
//                Intent intent = new Intent(getApplicationContext(), DisplayTasks.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
