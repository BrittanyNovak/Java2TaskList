package com.example.student.java2tasklist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.Menu;

/**
 * Created by bnovak on 8/3/16.
 */
public class DisplayTask extends ActionBarActivity {
    int from_where_i_am_coming = 0;
    private DBHelper mydb;

    TextView name;
    TextView is_completed;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        name = (TextView) findViewById(R.id.etTask);
        is_completed = (TextView) findViewById(R.id.swIS_COMPLETED);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.TASKS_COLUMN_TASK));
                String is_complete = rs.getString(rs.getColumnIndex(DBHelper.TASKS_COLUMN_IS_COMPLETED));

                if (!rs.isClosed()) {
                    rs.close();
                }

                Button b = (Button) findViewById(R.id.btnSave);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);

                is_completed.setText((CharSequence) is_complete);
                is_completed.setFocusable(false);
                is_completed.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.display_task, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.Edit_Task:
                Button b = (Button) findViewById(R.id.btnSave);
                b.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                is_completed.setEnabled(true);
                is_completed.setFocusableInTouchMode(true);
                is_completed.setClickable(true);

                return true;
            case R.id.Delete_Task:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteTask(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deletion Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateTask(id_To_Update, name.getText().toString(), is_completed.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertTask(name.getText().toString(), is_completed.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Finished", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}




