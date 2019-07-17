package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.util.Date;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private TodoDbHelper dbHelper;
    private SQLiteDatabase db;
    private RadioGroup radioGroup;
    private RadioButton rBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        dbHelper = new TodoDbHelper(this);
        db = dbHelper.getWritableDatabase();
        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);
        radioGroup = findViewById(R.id.radioGroup);
        rBtn = findViewById(R.id.lBtn);
        rBtn.setChecked(true);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(),setPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbHelper.close();
        db = null;
        dbHelper = null;
    }

    private Priority setPriority(){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.lBtn:
                return Priority.LOW;
            case R.id.mBtn:
                return Priority.MIDDLE;
            case R.id.hBtn:
                return Priority.HIGH;
            default:
                return Priority.LOW;
        }
    }

    private boolean saveNote2Database(String content, Priority priority) {
        // TODO 插入一条新数据，返回是否插入成功
        if (db == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoContract.Entry.CONTENT,content);
        values.put(TodoContract.Entry.STATE,State.TODO.intValue);
        values.put(TodoContract.Entry.DATE,System.currentTimeMillis());
        values.put(TodoContract.Entry.PRIORITY,priority.intValue);
        long newRowId = db.insert(TodoContract.Entry.TABLE_NAME,null,values);

        return newRowId != -1;
    }

}
