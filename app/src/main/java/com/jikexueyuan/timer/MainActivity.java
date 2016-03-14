package com.jikexueyuan.timer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private RemindAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.remind_list);
        adapter = new RemindAdapter(this);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                RemindBean remindBean = adapter.getItem(position);
                //从数据库中删除
                RemindUtil.canelRemind(MainActivity.this, remindBean.getId(), remindBean.getContent());
                //刷新列表
                refreshRemind();

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshRemind();
    }

    private void refreshRemind(){

        adapter.addRemindList(RemindUtil.getRemindList());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("添加记事本");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, NoteActivity.class));
        return super.onOptionsItemSelected(item);
    }

}
