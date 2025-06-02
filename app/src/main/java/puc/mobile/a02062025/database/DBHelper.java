package puc.mobile.a02062025.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import puc.mobile.a02062025.model.CepData;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "buscacep.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HISTORICO = "historico";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CEP = "cep";
    private static final String COLUMN_LOGRADOURO = "logradouro";
    private static final String COLUMN_BAIRRO = "bairro";
    private static final String COLUMN_LOCALIDADE = "localidade";
    private static final String COLUMN_UF = "uf";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HISTORICO = "CREATE TABLE " + TABLE_HISTORICO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CEP + " TEXT, " +
                COLUMN_LOGRADOURO + " TEXT, " +
                COLUMN_BAIRRO + " TEXT, " +
                COLUMN_LOCALIDADE + " TEXT, " +
                COLUMN_UF + " TEXT)";
        db.execSQL(CREATE_TABLE_HISTORICO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // se tiver upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORICO);
        onCreate(db);
    }

    public boolean insertHistorico(CepData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CEP, data.getCep());
        values.put(COLUMN_LOGRADOURO, data.getLogradouro());
        values.put(COLUMN_BAIRRO, data.getBairro());
        values.put(COLUMN_LOCALIDADE, data.getLocalidade());
        values.put(COLUMN_UF, data.getUf());

        long result = db.insert(TABLE_HISTORICO, null, values);
        db.close();
        return result != -1;
    }

    public List<CepData> getAllHistorico() {
        List<CepData> lista = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORICO + " ORDER BY " + COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String cep = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEP));
                String logradouro = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGRADOURO));
                String bairro = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BAIRRO));
                String localidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCALIDADE));
                String uf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UF));

                CepData data = new CepData(id, cep, logradouro, bairro, localidade, uf);
                lista.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}
