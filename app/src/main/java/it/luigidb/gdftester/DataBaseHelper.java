package it.luigidb.gdftester;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



public class DataBaseHelper extends SQLiteAssetHelper{

    private static String DB_NAME = "question.db";
    private static final int  DATABASE_VERSION = 1;
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context activity context
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        Log.v("DB", "constructor");
    }



    public void openDataBase() throws SQLException {
        Log.v("DB", "open db");
        //Open the database
        myDataBase = getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        Log.v("DB", "close db");
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }


    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public void get_tables() {
        Log.v("DB", "get_tables");
        Cursor c = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type = \"table\"", null);
        Log.v("TABLES", Integer.toString(c.getCount()));
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Log.v("TABLES", c.getString(c.getColumnIndex("name")));
        }
        c.close();
    }

    public int get_num_question() {
        Log.v("DB", "get_num_question");
        Cursor c = myDataBase.rawQuery("select count(distinct(_id)) as col from domande", null);
        Log.v("QUERY[get_n_question]", "Blocco A");
        c.moveToFirst();
        int temp = c.getInt(c.getColumnIndex("col"));
        Log.v("QUERY[get_n_question]", "numero di righe ->" + temp);
        c.close();
        return temp;
    }

    public int get_type(int position) {
        Log.v("DB", "get_type");
        /* tipi
        * 1 - generale
        * 2 - sunto
        * 3 - comprensione_a, testo senza domande
        * 4 - comprensione_b, domande relative al testo di tipo 3
         * */
        Cursor c = myDataBase.rawQuery("select tipo from domande where rowid == ?", new String[]{Integer.toString(position)});
        Log.v("QUERY[get_type]","query OK");
        c.moveToFirst();
        String tipo = c.getString(c.getColumnIndex("tipo"));
        int res = -1;
        switch (tipo) {
            case "generale":
                res = 1;
                break;
            case "sunto":
                res = 2;
                break;
            case "comprensione_a":
                res = 3;
                break;
            case "comprensione_b":
                res = 4;
                break;
        }
        c.close();
        return res;
    }


    public String get_id(int position) {
        Log.v("DB", "get_id");
        Cursor c = myDataBase.rawQuery("select _id from domande where rowid == ?", new String[]{Integer.toString(position)});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("_id"));
    }


    public Cursor get_normal_question(String cod) {
        Log.v("DB", "get_normal_question");
        Cursor c = myDataBase.rawQuery("select * from domande where _id == ?", new String[]{cod});
        return c;
    }


    public Cursor get_comprensione(String cod) {
        Log.v("DB", "get_comprensione " + cod);
        String temp = cod.substring(0, 5) + "%";
        Log.v("get_comprensione", "Stringa: " + temp);
        Cursor res =  myDataBase.rawQuery("select * from domande where _id LIKE ?", new String[]{temp});

        Log.v("get_comprensione", String.format("rows: %d", res.getCount()));

        return res;
    }

}
