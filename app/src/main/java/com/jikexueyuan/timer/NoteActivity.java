package com.jikexueyuan.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText note_time;
    private EditText note_content;
    private Button note_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        note_time = (EditText) findViewById(R.id.note_time);
        note_content = (EditText) findViewById(R.id.note_content);
        note_submit = (Button) findViewById(R.id.note_submit);

        note_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.判读时间和内容逻辑
                if (TextUtils.isEmpty(note_time.getText()) || TextUtils.isEmpty(note_content.getText())) {
                    Toast.makeText(NoteActivity.this, "时间或内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //设置提醒
                RemindUtil.addRemind(NoteActivity.this, Integer.parseInt(note_time.getText().toString()), note_content.getText().toString());
            }
        });
    }
}
