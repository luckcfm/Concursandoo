package com.example.luckcfm.concursando;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class DetalhesConcursos extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_concursos);
        Bundle b = getIntent().getExtras();
        final Concursos concurso = (Concursos) b.getSerializable("Concursos");

        TextView nome = (TextView) findViewById(        R.id.concursoNome);
        TextView cargo = (TextView) findViewById(       R.id.concursoCargo);
        TextView data = (TextView) findViewById(        R.id.concursoData);
        TextView site = (TextView) findViewById(        R.id.concursoSite);
        TextView alertarEm = (TextView) findViewById(   R.id.concursoAlerta);
        TextView instituicao = (TextView) findViewById( R.id.concursoInsituicao);
//        Log.v("[SITE]", concurso.getSiteConcurso());

        nome.setText(concurso.getNomeConcurso().toString());
        cargo.setText(concurso.getCargoConcurso().toString());
        data.setText(concurso.getDataConcurso().toString());
        site.setText(concurso.getSiteConcurso());
        alertarEm.setText(concurso.getAvisarEm().toString());
        instituicao.setText(concurso.getInstituicaoConcurso().toString());

        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button button = (Button) findViewById(R.id.btnDeletar);
        Button buttonEditar = (Button) findViewById(R.id.btnEditar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConcursosDataSource data = new ConcursosDataSource(getApplicationContext());
                try
                {
                    data.open();
                }catch(Exception e)
                {

                }
                data.deleteConcurso(concurso);
                ListView lista = (ListView) findViewById(R.id.mostraConcursos);
                Intent intent = new Intent(DetalhesConcursos.this, mostraDados.class);
                startActivity(intent);
            }
        });
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditarConcurso.class);
                intent.putExtra("Concurso", concurso);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalhes_concursos, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
