package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量
    private TodoContract() {
    }

    public static class Entry implements BaseColumns{
        public static final String TABLE_NAME = "Note";
        public static final String DATE = "date";
        public static final String STATE = "state";
        public static final String CONTENT = "content";
        public static final String PRIORITY = "priority";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ Entry.TABLE_NAME + "(" + Entry._ID + " INTERGER PRIMARY KEY AUTOINCREMENT, "
            + Entry.DATE + " INTERGER, " + Entry.STATE + " INTERGER, " + Entry.CONTENT + " TEXT, " + Entry.PRIORITY + " INTERGER)";

    public static final String SQL_DELETE_ENTRIES = "ALTER TABLE " +Entry.TABLE_NAME + " ADD " + Entry.PRIORITY + " INTERGER";
}