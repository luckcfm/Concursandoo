package com.example.luckcfm.concursando;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
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


public class CadastroConcurso extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastro, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return false;
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
            final View rootView = inflater.inflate(R.layout.fragment_cadastro, container, false);
            final long umaSemana;
            final long umMes;
            final long doisMeses;

            Button botaoCadastro =              (Button) rootView.findViewById(R.id.botaoConfirmar);
            Button botaoCancelar =              (Button) rootView.findViewById(R.id.botaoCancelar);
            final EditText nomeConcurso =       (EditText) rootView.findViewById(R.id.cadastroConcursoNome);
            final EditText concursoData =       (EditText) rootView.findViewById(R.id.datePicker);
            final EditText concursoSite =       (EditText) rootView.findViewById(R.id.cadastrarConcursoSite);
            final EditText concursoInsituicao = (EditText) rootView.findViewById(R.id.instituicaoConcurso);
            final EditText concursoCargo =      (EditText) rootView.findViewById(R.id.cargoConcurso);
            final Spinner avisarEm =            (Spinner) rootView.findViewById(R.id.spinner);
            final String texto = "Há campos não inseridos, tente novamente.";
            final int duracao = Toast.LENGTH_LONG;



                botaoCadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(nomeConcurso.getText().toString().isEmpty() ||
                            concursoSite.getText().toString().isEmpty())
                    {
                        Toast.makeText(rootView.getContext(), texto, duracao).show();
                    }else
                    {
                        ConcursosDataSource dataSource = new ConcursosDataSource(rootView.getContext());
                        try{
                            dataSource.open();
                        }catch(SQLException E)
                        {

                        }

                        String data = concursoData.getText().toString();
                        Concursos novo = dataSource.createConcurso(
                                nomeConcurso.getText().toString(),
                                data,
                                concursoCargo.getText().toString(),
                                concursoInsituicao.getText().toString(),
                                concursoSite.getText().toString(),
                                avisarEm.getSelectedItem().toString()
                                );
                        scheduleNotification(getNotification("Seu concurso "+ nomeConcurso.getText() + "está chegando!",novo),5000, novo);
                        Toast.makeText(rootView.getContext(), "Registro adicionado com sucesso!", Toast.LENGTH_LONG);
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

            String adapterSpinner[] = new String[] {"Uma semana antes",
                    "um mes antes",
                    "2 meses antes"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, adapterSpinner);

            Spinner spin = (Spinner) rootView.findViewById(R.id.spinner);
            spin.setAdapter(adapter);
            return rootView;
        }

        private void scheduleNotification(Notification notification, int delay, Concursos concursos) {

            Intent notificationIntent = new Intent(getActivity(), NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, pendingIntent);

        }

        private Notification getNotification(String content, Concursos concurso) {
            Notification.Builder builder = new Notification.Builder(getActivity());

            Intent notificationIntent = new Intent(getActivity(), DetalhesConcursos.class);
            notificationIntent.putExtra("Concursos", concurso);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getActivity().getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle("Concursos chegando");
            builder.setContentText(content);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.drawable.ic_launcher);

            PendingIntent i= PendingIntent.getActivity(getActivity(), 0,
                    new Intent(getActivity().getApplicationContext(), DetalhesConcursos.class),
                    0);
            return builder.build();
        }
     }

}
