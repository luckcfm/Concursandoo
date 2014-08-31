package com.example.luckcfm.concursando;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;


public class mostraDados extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_dados);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        android.support.v7.app.ActionBar action = getSupportActionBar();
        getMenuInflater().inflate(R.menu.mostra_dados, menu);
        action.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_delete:
                ConcursosDataSource concurso = new ConcursosDataSource(getApplicationContext());
                try{
                    concurso.open();
                    ListView lista = (ListView) findViewById(R.id.mostraConcursos);
                    ArrayAdapter<Concursos> adapter = (ArrayAdapter) lista.getAdapter();
                    Concursos deletar = adapter.getItem(info.position);
                    concurso.deleteConcurso(deletar);
                    adapter.clear();
                    adapter.addAll(concurso.getAllConcursos());
                    adapter.notifyDataSetChanged();
                    lista.refreshDrawableState();
                    concurso.close();
                }catch(Exception e)
                {

                }

                return true;
            case R.id.action_editar:
                Log.v("[ITEM]" , ""+info.position);
                ListView lista = (ListView) findViewById(R.id.mostraConcursos);
                ArrayAdapter<Concursos> adapter = (ArrayAdapter) lista.getAdapter();
                Concursos editar = adapter.getItem(info.position);
                Intent intent = new Intent(mostraDados.this, EditarConcurso.class);
                intent.putExtra("Concurso", editar);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
        if(id == R.id.removeTodos)
        {
            ConcursosDataSource concurso = new ConcursosDataSource(getApplicationContext());
            try{
                concurso.open();
                concurso.deleteTodos();
                ListView lista = (ListView) findViewById(R.id.mostraConcursos);
                ArrayAdapter<Concursos> adapter = (ArrayAdapter) lista.getAdapter();

                adapter.clear();
                adapter.addAll(concurso.getAllConcursos());
                adapter.notifyDataSetChanged();
                lista.refreshDrawableState();
                concurso.close();
            }catch(Exception e)
            {

            }


        }
        /*if(id == R.id.action_search)
        {
            Log.v("[ACHO]", "ACHO ACHO ACHO");
        }*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_mostra_dados, container, false);
            ConcursosDataSource dataSource = new ConcursosDataSource(rootView.getContext());
            final AutoCompleteTextView complete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteConcurso);
            registerForContextMenu(rootView.findViewById(R.id.mostraConcursos));
            try{
                dataSource.open();
            }catch(SQLException e){

            };
            List<Concursos> values = dataSource.getAllConcursos();
            final ArrayAdapter<Concursos> adapter = new ArrayAdapter<Concursos>(rootView.getContext(), android.R.layout.simple_list_item_1, values);
            final ListView lista = (ListView) rootView.findViewById(R.id.mostraConcursos);
            complete.setAdapter(adapter);

            lista.setAdapter(adapter);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     lista.setSelection(complete.getListSelection());
                }
            });
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(rootView.getContext(), DetalhesConcursos.class);
                        intent.putExtra("Concursos", adapter.getItem(i));
                        startActivity(intent);
                }
            });


            
            return rootView;
        }
    }
}
