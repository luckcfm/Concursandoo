package com.example.luckcfm.concursando;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;


public class EditarConcurso extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_concurso);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editar_concurso, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            final Concursos concurso = (Concursos) intent.getSerializableExtra("Concurso");

            final View rootView = inflater.inflate(R.layout.fragment_editar_concurso, container, false);
            Button          botaoCadastro = (Button) rootView.findViewById(R.id.botaoConfirmar);
            Button          botaoCancelar = (Button) rootView.findViewById(R.id.botaoCancelar);
            final EditText  nomeConcurso = (EditText) rootView.findViewById(R.id.cadastroConcursoNome);
            final EditText  concursoData = (EditText) rootView.findViewById(R.id.datePicker);
            final EditText  concursoSite = (EditText) rootView.findViewById(R.id.cadastrarConcursoSite);
            final EditText  concursoInsituicao = (EditText) rootView.findViewById(R.id.instituicaoConcurso);
            final EditText  concursoCargo = (EditText) rootView.findViewById(R.id.cargoConcurso);
            final Spinner   avisarEm = (Spinner) rootView.findViewById(R.id.spinner);
            final String    texto = "Há campos não inseridos, tente novamente.";
            final int duracao = Toast.LENGTH_LONG;

            String adapterSpinner[] = new String[] {"Uma semana antes",
                    "um mes antes",
                    "2 meses antes"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, adapterSpinner);

            Spinner spin = (Spinner) rootView.findViewById(R.id.spinner);
            spin.setAdapter(adapter);

            nomeConcurso.setText(concurso.getNomeConcurso());
            concursoData.setText(concurso.getDataConcurso());
            concursoSite.setText(concurso.getSiteConcurso());
            concursoInsituicao.setText(concurso.getInstituicaoConcurso());
            concursoCargo.setText(concurso.getCargoConcurso());
            ArrayAdapter spiner = (ArrayAdapter) avisarEm.getAdapter();
            int position = spiner.getPosition(concurso.getAvisarEm());
            avisarEm.setSelection(position);




            botaoCadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(nomeConcurso.getText().toString().isEmpty() ||
                            concursoSite.getText().toString().isEmpty())
                    {
                        Toast.makeText(rootView.getContext(), texto, duracao).show();
                    }else
                    {
                        concurso.setNomeConcurso(nomeConcurso.getText().toString());
                        concurso.setDataConcurso(concursoData.getText().toString());
                        concurso.setInstituicaoConcurso(concursoInsituicao.getText().toString());
                        concurso.setAvisarEm(avisarEm.getSelectedItem().toString());
                        concurso.setSiteConcurso(concursoSite.getText().toString());
                        concurso.setCargoConcurso(concursoCargo.getText().toString());

                        ConcursosDataSource dataSource = new ConcursosDataSource(rootView.getContext());
                        try{
                            dataSource.open();
                        }catch(SQLException E)
                        {

                        }

                        dataSource.updateConcurso(concurso);
                        dataSource.close();
                        Toast.makeText(rootView.getContext(), "Registro editado com sucesso!", Toast.LENGTH_LONG);
                        Intent intent = new Intent(rootView.getContext(), MyActivity.class);
                        startActivity(intent);
                        // gerarNotificacao("teste", rootView, novo);


                    }
                }
            });




            botaoCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), MyActivity.class);
                    startActivity(intent);
                }
            });


            return rootView;
        }
    }
}
