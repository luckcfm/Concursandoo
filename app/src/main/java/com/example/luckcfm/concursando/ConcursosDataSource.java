package com.example.luckcfm.concursando;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luckcfm on 19/08/14.
 */
public class ConcursosDataSource {
    private SQLiteDatabase database;
    private MySqlHelper dbHelper;
    private String[] allColumns = {
            MySqlHelper.column_id,
            MySqlHelper.column_nomeConcurso,
            MySqlHelper.column_dataConcurso,
            MySqlHelper.column_cargoConcurso,
            MySqlHelper.column_instituicaoConcurso,
            MySqlHelper.column_siteConcurso,
            MySqlHelper.column_avisarQuando
    };

    public ConcursosDataSource(Context context){
        dbHelper = new MySqlHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }


    public Concursos createConcurso(String concursoNome,String concursoData, String concursoCargo,String concursoInstituicao, String concursoSite, String concursoAvisarEm){
        ContentValues values = new ContentValues();

        values.put(MySqlHelper.column_nomeConcurso, concursoNome);
        values.put(MySqlHelper.column_dataConcurso, concursoData);
        values.put(MySqlHelper.column_cargoConcurso, concursoCargo);
        values.put(MySqlHelper.column_instituicaoConcurso, concursoInstituicao);
        values.put(MySqlHelper.column_siteConcurso, concursoSite);
        values.put(MySqlHelper.column_avisarQuando, concursoAvisarEm);

        long insertId = database.insert(MySqlHelper.table_concursos, null,
                values);
        Cursor cursor = database.query(MySqlHelper.table_concursos, allColumns,
                MySqlHelper.column_id + " = " + insertId, null,
                null,null,null);
        cursor.moveToFirst();
        Concursos newConcurso = cursorToConcursos(cursor);
        cursor.close();
        database.close();
        return newConcurso;
    }
    public void updateConcurso(Concursos concurso)
    {
        long id = concurso.getId();
        String idString = Long.toString(id);
        ContentValues values = new ContentValues();

        Log.v("ATUALIZAR", concurso.getNomeConcurso());

        values.put(MySqlHelper.column_nomeConcurso, concurso.getNomeConcurso());
        values.put(MySqlHelper.column_dataConcurso, concurso.getDataConcurso());
        values.put(MySqlHelper.column_cargoConcurso, concurso.getCargoConcurso());
        values.put(MySqlHelper.column_instituicaoConcurso, concurso.getInstituicaoConcurso());
        values.put(MySqlHelper.column_siteConcurso, concurso.getSiteConcurso());
        values.put(MySqlHelper.column_avisarQuando, concurso.getAvisarEm());
        database.update(MySqlHelper.table_concursos, values , MySqlHelper.column_id +" = " + id, null);
        database.close();
    }

    public void deleteConcurso(Concursos concurso){
        long id = concurso.getId();
        Log.v("[ID]", ""+id);
       // System.out.println("Comentário deletado com a id "+ id);
        database.delete(MySqlHelper.table_concursos, MySqlHelper.column_id + " = " + id, null);
        database.close();
    }
    public Concursos getConcurso(int id)
    {
        Concursos c = new Concursos();
        String query = "SELECT * FROM "+MySqlHelper.table_concursos+" WHERE id = "+id+" ;";
        Cursor cursor= database.rawQuery(query, null);
        c = cursorToConcursos(cursor);
        return c;
    }
    public void deleteTodos()
    {
        database.execSQL("delete from "+ MySqlHelper.table_concursos);
    }

    public List<Concursos> getAllConcursos()
    {
        List<Concursos> concurso = new ArrayList<Concursos>();

        Cursor cursor = database.query(MySqlHelper.table_concursos, allColumns,
                null,null,null,null,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Concursos concurs = cursorToConcursos(cursor);
            concurso.add(concurs);
            cursor.moveToNext();
        }
        cursor.close();
        return concurso;
    }

    private Concursos cursorToConcursos(Cursor cursor)
    {
        for(int i = 0; i <= 6; i++)
        {
            Log.v("Cursor "+i+":", cursor.getString(i));
        }
        Concursos concurso = new Concursos();
        concurso.setId(cursor.getLong(0));
        concurso.setNomeConcurso(cursor.getString(1));
        concurso.setDataConcurso(cursor.getString(2));
        concurso.setSiteConcurso(cursor.getString(5));
        concurso.setInstituicaoConcurso(cursor.getString(4));
        concurso.setCargoConcurso(cursor.getString(4));
        concurso.setAvisarEm(cursor.getString(6));
        return concurso;
    }


}
