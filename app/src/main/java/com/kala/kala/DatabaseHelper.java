package com.kala.kala;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edo on 2/22/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Kala";

    // Table Names
    private static final String TABLE_USER = "users";
    private static final String TABLE_REMINDER = "reminders";
    private static final String TABLE_GROUP = "groups";
    private static final String TABLE_PHOTO = "photos";
    private static final String TABLE_COMMENT = "comments";
    private static final String TABLE_NOTE = "notes";
    private static final String TABLE_MAP = "maps";
    private static final String TABLE_ALBUM = "albums";
    private static final String TABLE_GROUP_USER = "group_users";
    private static final String TABLE_REMINDER_USER = "reminder_users";
    private static final String TABLE_REMINDER_PHOTO = "reminder_photos";
    private static final String TABLE_REMINDER_NOTE = "reminder_notes";
    private static final String TABLE_REMINDER_ALBUM = "reminder_album";
    private static final String TABLE_ALBUM_PHOTO = "album_photo";
    private static final String TABLE_REMINDER_USER_COMMENT = "reminder_user_comments";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_TIME = "created_time";

    // USERS Table - column names
    private static final String KEY_USER_FULLNAME = "fullname";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PHONE_NUMBER = "phone_number";
    private static final String KEY_USER_PICTURE = "picture";
    private static final String KEY_USER_THUMBNAIL = "thumbnail";
    private static final String KEY_USER_UPDATED_TIME = "updated_time";
    private static final String KEY_USER_STATUS = "status";

    // REMINDERS Table - column names
    private static final String KEY_REMINDER_TITLE = "title";
    private static final String KEY_REMINDER_DUE_DATE = "due_date";
    private static final String KEY_REMINDER_UPDATED_TIME = "updated_time";

    // GROUPS Table - column names
    private static final String KEY_GROUP_NAME = "name";
    private static final String KEY_GROUP_PICTURE = "picture";
    private static final String KEY_GROUP_THUMBNAIL = "thumbnail";
    private static final String KEY_GROUP_UPDATED_TIME = "updated_time";

    // PHOTOS Table - column names
    private static final String KEY_PHOTO_PATH = "path";
    private static final String KEY_PHOTO_THUMBNAIL = "thumbnail";
    private static final String KEY_PHOTO_UPDATED_TIME = "updated_time";

    // COMMENTS Table - column names
    private static final String KEY_COMMENT_MESSAGE = "message";

    // NOTE Table - column names
    private static final String KEY_NOTE_CONTENT = "content";
    private static final String KEY_NOTE_UPDATED_TIME = "updated_time";

    // MAP Table - column names
    private static final String KEY_MAP_LONGITUDE = "longitude";
    private static final String KEY_MAP_LATITUDE = "latitude";
    private static final String KEY_MAP_UPDATED_TIME = "updated_time";

    // ALBUM Table - column names
    private static final String KEY_ALBUM_UPDATED_TIME = "updated_time";

    // GROUP_USERS Table = column names
    private static final String KEY_GROUP_USER_GROUP_ID = "group_id";
    private static final String KEY_GROUP_USER_USER_ID = "user_id";

    // REMINDER_USERS Table - column names
    private static final String KEY_REMINDER_USER_USER_ID = "user_id";
    private static final String KEY_REMINDER_USER_REMINDER_ID = "reminder_id";

    // REMINDER_PHOTOS Table - column names
    private static final String KEY_REMINDER_PHOTO_REMINDER_ID = "reminder_id";
    private static final String KEY_REMINDER_PHOTO_PHOTO_ID = "photo_id";

    // REMINDER_NOTES Table = column names
    private static final String KEY_REMINDER_NOTE_REMINDER_ID = "reminder_id";
    private static final String KEY_REMINDER_NOTE_NOTE_ID = "note_id";

    // REMINDER_ALBUMS Table = column names
    private static final String KEY_REMINDER_ALBUM_REMINDER_ID = "reminder_id";
    private static final String KEY_REMINDER_ALBUM_ALBUM_ID = "album_id";

    // ALBUM_PHOTOS Table = column names
    private static final String KEY_ALBUM_PHOTO_ALBUM_ID = "album_id";
    private static final String KEY_ALBUM_PHOTO_PHOTO_ID = "photo_id";

    // REMINDER_USER_COMMENT = column names
    private static final String KEY_REMINDER_USER_COMMENT_REMINDER_USER_ID = "reminder_user_id";
    private static final String KEY_REMINDER_USER_COMMENT_COMMENT_ID = "comment_id";

    // Table Create Statements
    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_FULLNAME  + " VARCHAR(100),"
            + KEY_USER_USERNAME + " VARCHAR(100),"
            + KEY_USER_EMAIL + " VARCHAR(100),"
            + KEY_USER_PHONE_NUMBER + " VARCHAR(100),"
            + KEY_USER_PICTURE + " BLOB,"
            + KEY_USER_THUMBNAIL + " BLOB,"
            + KEY_USER_STATUS + " INTEGER,"
            + KEY_CREATED_TIME + " TIMESTAMP,"
            + KEY_USER_UPDATED_TIME + " TIMESTAMP"
            + ")";

    // Reminder table create statement
    private static final String CREATE_TABLE_REMINDER = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_REMINDER_TITLE  + " VARCHAR(100)," +
            KEY_REMINDER_DUE_DATE  + " DATETIME," +
            KEY_REMINDER_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // Group table create statement
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE IF NOT EXISTS " + TABLE_GROUP +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_GROUP_NAME  + " VARCHAR(100)," +
            KEY_GROUP_PICTURE  + " BLOB," +
            KEY_GROUP_THUMBNAIL + " BLOB," +
            KEY_GROUP_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // Photo table create statement
    private static final String CREATE_TABLE_PHOTO = "CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_PHOTO_PATH  + " VARCHAR(100)," +
            KEY_PHOTO_THUMBNAIL  + " BLOB," +
            KEY_PHOTO_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // Comment table create statement
    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMENT
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_COMMENT_MESSAGE  + " VARCHAR(100),"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // Note table create statement
    private static final String CREATE_TABLE_NOTE = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NOTE_CONTENT  + " VARCHAR(100)," +
            KEY_NOTE_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // Map table create statement
    private static final String CREATE_TABLE_MAP = "CREATE TABLE IF NOT EXISTS " + TABLE_MAP +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_MAP_LONGITUDE  + " VARCHAR(100)," +
            KEY_MAP_LATITUDE  + " VARCHAR(100)," +
            KEY_MAP_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // Album table create statement
    private static final String CREATE_TABLE_ALBUM = "CREATE TABLE IF NOT EXISTS " + TABLE_ALBUM +
            "( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_ALBUM_UPDATED_TIME + " DATETIME," +
            KEY_CREATED_TIME + " DATETIME" +
            ")";

    // group_user table create statement
    private static final String CREATE_TABLE_GROUP_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_GROUP_USER
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_GROUP_USER_GROUP_ID  + "INTEGER,"
            + KEY_GROUP_USER_USER_ID  + "INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // reminder_user table create statement
    private static final String CREATE_TABLE_REMINDER_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER_USER
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REMINDER_USER_REMINDER_ID  + " INTEGER,"
            + KEY_REMINDER_USER_USER_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // reminder_photo table create statement
    private static final String CREATE_TABLE_REMINDER_PHOTO = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER_PHOTO
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REMINDER_PHOTO_REMINDER_ID + " INTEGER,"
            + KEY_REMINDER_PHOTO_PHOTO_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // reminder_note table create statement
    private static final String CREATE_TABLE_REMINDER_NOTE = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER_NOTE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REMINDER_NOTE_REMINDER_ID + " INTEGER,"
            + KEY_REMINDER_NOTE_NOTE_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // reminder_album table create statement
    private static final String CREATE_TABLE_REMINDER_ALBUM = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER_ALBUM
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REMINDER_ALBUM_REMINDER_ID + " INTEGER,"
            + KEY_REMINDER_ALBUM_ALBUM_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // album_photo table create statement
    private static final String CREATE_TABLE_ALBUM_PHOTO = "CREATE TABLE IF NOT EXISTS " + TABLE_ALBUM_PHOTO
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ALBUM_PHOTO_ALBUM_ID + " INTEGER,"
            + KEY_ALBUM_PHOTO_PHOTO_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    // reminder_user_comment table create statement
    private static final String CREATE_TABLE_REMINDER_USER_COMMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDER_USER_COMMENT
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REMINDER_USER_COMMENT_REMINDER_USER_ID  + " INTEGER,"
            + KEY_REMINDER_USER_COMMENT_COMMENT_ID  + " INTEGER,"
            + KEY_CREATED_TIME + " DATETIME"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REMINDER);
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_COMMENT);
        db.execSQL(CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_MAP);
        db.execSQL(CREATE_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_GROUP_USER);
        db.execSQL(CREATE_TABLE_REMINDER_USER);
        db.execSQL(CREATE_TABLE_REMINDER_PHOTO);
        db.execSQL(CREATE_TABLE_REMINDER_NOTE);
        db.execSQL(CREATE_TABLE_REMINDER_ALBUM);
        db.execSQL(CREATE_TABLE_ALBUM_PHOTO);
        db.execSQL(CREATE_TABLE_REMINDER_USER_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_ALBUM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUM_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_USER_COMMENT);
        // create new tables
        onCreate(db);
    }

    // ------------------------ "users" table methods -----------------//
    /*
     * Creating user
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FULLNAME, user.getFullname());
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PHONE_NUMBER, user.getPhone_number());
        values.put(KEY_USER_PICTURE, user.getPicture());
        values.put(KEY_USER_THUMBNAIL, user.getThumbnail());
        values.put(KEY_USER_STATUS, user.getStatus());
        values.put(KEY_CREATED_TIME, getDateTime());

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);

        return user_id;
    }

    /*
     * get single User
     */
    public User getUser(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setFullname(c.getString(c.getColumnIndex(KEY_USER_FULLNAME)));
        user.setUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
        user.setEmail(c.getString(c.getColumnIndex(KEY_USER_EMAIL)));
        user.setPhone_number(c.getString(c.getColumnIndex(KEY_USER_PHONE_NUMBER)));
        user.setPicture(c.getString(c.getColumnIndex(KEY_USER_PICTURE)));
        user.setThumbnail(c.getString(c.getColumnIndex(KEY_USER_THUMBNAIL)));
        user.setStatus(c.getInt(c.getColumnIndex(KEY_USER_STATUS)));
        user.setUpdatedTime(c.getString(c.getColumnIndex(KEY_USER_UPDATED_TIME)));
        user.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

        return user;
    }

    /*
     * get single User by Reminder
     */
    public long getUserByFullname(String fullname) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USER_FULLNAME + " = '" + fullname + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        long user_id = c.getLong(c.getColumnIndex(KEY_ID));

        return user_id;
    }

    /*
     * get single User by Reminder
     */
    public User getUserByReminder(long reminder_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " u, "
                + TABLE_REMINDER + " r, " + TABLE_REMINDER_USER + " ru WHERE r."
                + KEY_ID + " = '" + reminder_id + "'" + " AND r." + KEY_ID
                + " = " + "ru." + KEY_REMINDER_USER_REMINDER_ID + " AND u."
                + KEY_ID + " = " + "ru." + KEY_REMINDER_USER_USER_ID;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setFullname(c.getString(c.getColumnIndex(KEY_USER_FULLNAME)));
        user.setUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
        user.setEmail(c.getString(c.getColumnIndex(KEY_USER_EMAIL)));
        user.setPhone_number(c.getString(c.getColumnIndex(KEY_USER_PHONE_NUMBER)));
        user.setPicture(c.getString(c.getColumnIndex(KEY_USER_PICTURE)));
        user.setThumbnail(c.getString(c.getColumnIndex(KEY_USER_THUMBNAIL)));
        user.setStatus(c.getInt(c.getColumnIndex(KEY_USER_STATUS)));
        user.setUpdatedTime(c.getString(c.getColumnIndex(KEY_USER_UPDATED_TIME)));
        user.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

        return user;
    }

    /*
     * getting all users
     * */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                user.setFullname(c.getString(c.getColumnIndex(KEY_USER_FULLNAME)));
                user.setUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
                user.setEmail(c.getString(c.getColumnIndex(KEY_USER_EMAIL)));
                user.setPhone_number(c.getString(c.getColumnIndex(KEY_USER_PHONE_NUMBER)));
                user.setPicture(c.getString(c.getColumnIndex(KEY_USER_PICTURE)));
                user.setThumbnail(c.getString(c.getColumnIndex(KEY_USER_THUMBNAIL)));
                user.setStatus(c.getInt(c.getColumnIndex(KEY_USER_STATUS)));
                user.setUpdatedTime(c.getString(c.getColumnIndex(KEY_USER_UPDATED_TIME)));
                user.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

                // adding to User list
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    /*
     * Updating a user
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FULLNAME, user.getFullname());
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PHONE_NUMBER, user.getPhone_number());
        values.put(KEY_USER_PICTURE, user.getPicture());
        values.put(KEY_USER_THUMBNAIL, user.getThumbnail());
        values.put(KEY_USER_STATUS, user.getStatus());
        values.put(KEY_USER_UPDATED_TIME, getDateTime());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    /*
     * Deleting a User
     */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(user_id)});
    }

    // ------------------------ "reminders" table methods -----------------//
    /*
     * Creating reminder
     */
    public long createReminder(Reminder reminder, long[] note_ids, long[] user_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_TITLE, reminder.getTitle());
        values.put(KEY_REMINDER_DUE_DATE, reminder.getDueDate());
        values.put(KEY_CREATED_TIME, getDateTime());
        values.put(KEY_REMINDER_UPDATED_TIME, reminder.getUpdatedTime());

        // insert row
        long reminder_id = db.insert(TABLE_REMINDER, null, values);

        // assigning note to reminder
        for (long note_id : note_ids) {
            createReminderNote(reminder_id, note_id);
        }

        // assigning user to reminder
        for (long user_id : user_ids) {
            createReminderUser(reminder_id, user_id);
        }

        return reminder_id;
    }

    /*
     * get single Reminder
     */
    public Reminder getReminder(long reminder_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_REMINDER + " WHERE "
                + KEY_ID + " = " + reminder_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Reminder reminder = new Reminder();
        reminder.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        reminder.setTitle(c.getString(c.getColumnIndex(KEY_REMINDER_TITLE)));
        reminder.setDuedate(c.getString(c.getColumnIndex(KEY_REMINDER_DUE_DATE)));
        reminder.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));
        reminder.setUpdatedTime(c.getString(c.getColumnIndex(KEY_REMINDER_UPDATED_TIME)));

        return reminder;
    }

    /*
     * getting all reminders
     * */
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();
        String selectQuery = "SELECT  * FROM " + TABLE_REMINDER;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                reminder.setTitle(c.getString(c.getColumnIndex(KEY_REMINDER_TITLE)));
                reminder.setDuedate(c.getString(c.getColumnIndex(KEY_REMINDER_DUE_DATE)));
                reminder.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));
                reminder.setUpdatedTime(c.getString(c.getColumnIndex(KEY_REMINDER_UPDATED_TIME)));

                // adding to Reminder list
                reminders.add(reminder);
            } while (c.moveToNext());
        }

        return reminders;
    }

    /*
     * Updating an reminder
     */
    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_TITLE, reminder.getTitle());
        values.put(KEY_REMINDER_DUE_DATE, reminder.getDueDate());
        values.put(KEY_REMINDER_UPDATED_TIME, getDateTime());
        Log.e("updated value", values.getAsString(KEY_REMINDER_TITLE));

        // updating row
        return db.update(TABLE_REMINDER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getId()) });
    }

    /*
     * Deleting an reminder
     */
    public void deleteReminder(long reminder_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDER, KEY_ID + " = ?",
                new String[]{String.valueOf(reminder_id)});
    }

    // ------------------------ "notes" table methods -----------------//
    /*
     * Creating note
     */
    public long createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_CONTENT, note.getContent());
        values.put(KEY_CREATED_TIME, getDateTime());

        // insert row
        long note_id = db.insert(TABLE_NOTE, null, values);

        return note_id;
    }

    /*
     * get single Note
     */
    public Note getNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE "
                + KEY_ID + " = " + note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setContent(c.getString(c.getColumnIndex(KEY_NOTE_CONTENT)));
        note.setUpdatedTime(c.getString(c.getColumnIndex(KEY_NOTE_UPDATED_TIME)));
        note.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

        return note;
    }

    /*
     * get single Note by Reminder
     */
    public Note getNoteByReminder(long reminder_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NOTE + " n, "
                + TABLE_REMINDER + " r, " + TABLE_REMINDER_NOTE + " rn WHERE r."
                + KEY_ID + " = '" + reminder_id + "'" + " AND r." + KEY_ID
                + " = " + "rn." + KEY_REMINDER_NOTE_REMINDER_ID + " AND n."
                + KEY_ID + " = " + "rn." + KEY_REMINDER_NOTE_NOTE_ID;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setContent(c.getString(c.getColumnIndex(KEY_NOTE_CONTENT)));
        note.setUpdatedTime(c.getString(c.getColumnIndex(KEY_NOTE_UPDATED_TIME)));
        note.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

        return note;
    }

    /*
     * getting all notes by reminder id
     */
    public List<Note> getAllNotesByReminderId(long reminder_id) {
        List<Note> notes = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTE + " n, "
                + TABLE_REMINDER + " r, " + TABLE_REMINDER_NOTE + " rn WHERE r."
                + KEY_ID + " = '" + reminder_id + "'" + " AND r." + KEY_ID
                + " = " + "rn." + KEY_REMINDER_NOTE_REMINDER_ID + " AND n."
                + KEY_ID + " = " + "rn." + KEY_REMINDER_NOTE_NOTE_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setContent(c.getString(c.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setUpdatedTime(c.getString(c.getColumnIndex(KEY_NOTE_UPDATED_TIME)));
                note.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }

    /*
     * getting all notes
     */
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setContent(c.getString(c.getColumnIndex(KEY_NOTE_CONTENT)));
                note.setUpdatedTime(c.getString(c.getColumnIndex(KEY_NOTE_UPDATED_TIME)));
                note.setCreatedTime(c.getString(c.getColumnIndex(KEY_CREATED_TIME)));

                // adding to tags list
                notes.add(note);
            } while (c.moveToNext());
        }
        return notes;
    }

    /*
     * Updating a note
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_CONTENT, note.getContent());
        values.put(KEY_CREATED_TIME, getDateTime());

        // updating row
        return db.update(TABLE_NOTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId())});
    }

    /*
     * Deleting an reminder
     */
    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_ID + " = ?",
                new String[]{String.valueOf(note_id)});
    }

    // ------------------------ "comment" table methods -----------------//
    /*
     * Creating comment
     */
    public long createComment (Comment comment, long reminder_user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMMENT_MESSAGE, comment.getMessage());
        values.put(KEY_CREATED_TIME, getDateTime());

        // insert row
        long comment_id = db.insert(TABLE_COMMENT, null, values);

        // assigning reminder_users to comment
        createReminderUserComment(reminder_user_id, comment_id);

        return comment_id;
    }

    /*
     * getting all comments
     * */
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMMENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Comment comment = new Comment();
                comment.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                comment.setLeft(true);
                comment.setMessage(c.getString(c.getColumnIndex(KEY_COMMENT_MESSAGE)));

                // adding to User list
                comments.add(comment);
            } while (c.moveToNext());
        }

        return comments;
    }


    // ------------------------ "reminder user" table methods -----------------//
    /**
     * Creating a reminder user
     */
    public long createReminderUser(long reminder_id, long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_USER_REMINDER_ID, reminder_id);
        values.put(KEY_REMINDER_USER_USER_ID, user_id);
        values.put(KEY_CREATED_TIME, getDateTime());

        long id = db.insert(TABLE_REMINDER_USER, null, values);

        return id;
    }

    /**
     * get a reminder user id
     */
    public long getReminderUserId(long reminder_id, long user_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_REMINDER_USER + " WHERE "
                + KEY_REMINDER_USER_REMINDER_ID + " = '" + reminder_id + "'" + " AND " + KEY_REMINDER_USER_USER_ID
                + " = '" + user_id + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        long reminder_user_id = c.getInt(c.getColumnIndex(KEY_ID));

        return reminder_user_id;
    }


    /**
     * Updating a reminder user
     */
    public long updateReminderUser(long id, long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_USER_USER_ID, user_id);

        return db.update(TABLE_REMINDER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Deleting a reminder user
     */
    public void deleteReminderUser(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDER, KEY_ID + " = ?",
                new String[] { String.valueOf(id)});
    }

    // ------------------------ "reminder note" table methods -----------------//
    /**
     * Creating a reminder note
     */
    public long createReminderNote(long reminder_id, long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_NOTE_REMINDER_ID, reminder_id);
        values.put(KEY_REMINDER_NOTE_NOTE_ID, note_id);
        values.put(KEY_CREATED_TIME, getDateTime());

        long id = db.insert(TABLE_REMINDER_NOTE, null, values);

        return id;
    }

    /**
     * Updating a reminder note
     */
    public long updateReminderNote(long id, long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_NOTE_NOTE_ID, note_id);

        return db.update(TABLE_REMINDER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Deleting a reminder note
     */
    public void deleteReminderNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDER, KEY_ID + " = ?",
                new String[] { String.valueOf(id)});
    }

    // ------------------------ "reminder user comment" table methods -----------------//
    /**
     * Creating a reminder user comment
     */
    public long createReminderUserComment(long reminder_user_id, long comment_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMINDER_USER_COMMENT_REMINDER_USER_ID, reminder_user_id);
        values.put(KEY_REMINDER_USER_COMMENT_COMMENT_ID, comment_id);
        values.put(KEY_CREATED_TIME, getDateTime());

        long id = db.insert(TABLE_REMINDER_USER_COMMENT, null, values);

        return id;
    }


    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
