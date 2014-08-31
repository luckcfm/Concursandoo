package com.example.luckcfm.concursando;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by luckcfm on 19/08/14.
 */
public class MySqlHelper extends SQLiteOpenHelper {

    public static final String table_concursos = "concursos";
    public static final String column_id = "_id";
    public static final String column_nomeConcurso = "nomeConcurso";
    public static final String column_dataConcurso = "dataConcurso";
    public static final String column_siteConcurso = "siteConcurso";
    public static final String column_instituicaoConcurso = "instituicaoConcurso";
    public static final String column_cargoConcurso = "cargoConcurso";
    public static final String column_avisarQuando = "avisarQuando";

    public static final String DATABASE_NAME = "concursando.db";
    public static final int DATABASE_VERSION = 2;

    //declaração da criação do banco
    private static final String DATABASE_CREATE = "create table "
            + table_concursos + "( "
            + column_id                     + " integer primary key autoincrement, "
            + column_nomeConcurso           + " text not null, "
            + column_dataConcurso           + " text not null, "
            + column_siteConcurso           + " text, "
            + column_instituicaoConcurso    + " text not null, "
            + column_cargoConcurso          + " text not null, "
            + column_avisarQuando           + " text not null );";

    public MySqlHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(MySqlHelper.class.getName(),
                "Atualizando database da versao " + oldVersion + " para "
                        + newVersion + ", o que vai destruir todos os dados");
        db.execSQL("DROP TABLE IF EXISTS " + table_concursos);
        onCreate(db);
    }
}
