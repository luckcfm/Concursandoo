/*
package com.example.luckcfm.concursando;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class testeDAO extends Activity {
    private ConcursosDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_dao);
        final ListView  lista = (ListView) findViewById(R.id.lista);
        Button inserir = (Button) findViewById(R.id.add);
        Button delete = (Button) findViewById(R.id.delete);
        dataSource = new ConcursosDataSource(this);
        try{
            dataSource.open();
        }catch(SQLException e){

        };
        List<Concursos> values = dataSource.getAllConcursos();

        ArrayAdapter<Concursos> adapter = new ArrayAdapter<Concursos>(this, android.R.layout.simple_list_item_1, values);
        lista.setAdapter(adapter);

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter<Concursos> adapter = (ArrayAdapter) lista.getAdapter();
                Concursos concurso = null;

                switch(view.getId()){
                    case R.id.add:
                        String[] comments = new String[] {"Arvoredo", "Legalzedo", "Odiei"};
                        int nextInt = new Random().nextInt(3);

                        concurso = dataSource.createConcurso(comments[nextInt]);
                        adapter.add(concurso);
                        break;
                    case R.id.delete:
                        if(lista.getAdapter().getCount() > 0){
                            concurso = (Concursos) lista.getAdapter().getItem(0);
                            dataSource.deleteConcurso(concurso);
                            adapter.remove(concurso);
                        }
                        break;
                }

                adapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter<Concursos> adapter = (ArrayAdapter) lista.getAdapter();
                Concursos concurso = null;

                switch(view.getId()){
                    case R.id.add:
                        String[] comments = new String[] {"Arvoredo", "Legalzedo", "Odiei"};
                        int nextInt = new Random().nextInt(3);

                        concurso = dataSource.createConcurso(comments[nextInt]);
                        adapter.add(concurso);
                        break;
                    case R.id.delete:
                        if(lista.getAdapter().getCount() > 0){
                            concurso = (Concursos) lista.getAdapter().getItem(0);
                            dataSource.deleteConcurso(concurso);
                            adapter.remove(concurso);
                        }
                        break;
                }

                adapter.notifyDataSetChanged();
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teste_dao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
*/
