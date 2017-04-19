package com.jonmid.tallerasynctask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jonmid.tallerasynctask.Adapters.PostAdapter;
import com.jonmid.tallerasynctask.Models.Post;
import com.jonmid.tallerasynctask.Parser.Json;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar cargador;
    Button boton;
    TextView texto;
    List<Post> myPost;
    RecyclerView recyclerView;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargador = (ProgressBar) findViewById(R.id.cargador);
        boton = (Button) findViewById(R.id.boton);
        texto = (TextView) findViewById(R.id.texto);
        recyclerView =(RecyclerView) findViewById(R.id.myRecycler);
        //Permite manejar los componentes en un RecyclerView:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //Establecer la orientación en vertical:
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //Asignar orientación a mi RecyclerView:
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public Boolean isOnLine(){
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    public void onClickButton(View view){
        if (isOnLine()){
            MyTask task = new MyTask();
            task.execute("https://jsonplaceholder.typicode.com/posts");
        }else {
            Toast.makeText(this, "Sin conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarDatos(){
        // texto.append("JSON cargado correctamente.");
        /*if (myPost != null){
            for (Post post:myPost){
                texto.append(post.getTitle() + "\n");
            }
        }*/

        //Crear un objeto de tipo "PostAdapter" y retorna el item de mi layout (item.xml)
        adapter = new PostAdapter(getApplicationContext(), myPost);
        recyclerView.setAdapter(adapter);
    }

    private class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargador.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = null;
            try {
                content = HttpManager.getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                myPost = Json.getData(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cargarDatos();
            cargador.setVisibility(View.GONE);
        }
    }
}
