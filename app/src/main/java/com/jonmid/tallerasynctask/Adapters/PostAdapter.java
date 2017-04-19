package com.jonmid.tallerasynctask.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonmid.tallerasynctask.Models.Post;
import com.jonmid.tallerasynctask.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-7 on 19/04/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    List<Post> myPost = new ArrayList<>();
    Context context;

    public PostAdapter(Context context, List<Post> myPost){
        this.myPost = myPost;
        this.context = context;
    }

    //Vincula mi vista (item) con la vista principal (activity_main.xml):
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inyecta mi layout (item.xml) en la vista principal

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        //Crear un objeto de la subclase para manejar y asignar los componentes del layout (item.xlm)
        ViewHolder viewHolder = new ViewHolder(itemView);


        return viewHolder;
    }

    //Lanza la vista en mi layout principal (activity_main):
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Establecer el valor de mi TextView, con el valor del JSON
        holder.myText.setText(myPost.get(position).getTitle());

        holder.myText.setText(myPost.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return myPost.size();
    }

    //hace una inyección de la vista al RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView myImagen;
        TextView myText;
        //TextView myTextB;

        //Constructor de mi subclase
        public ViewHolder(View item) {
            super(item);
            myImagen = (ImageView) item.findViewById(R.id.logo);
            myText = (TextView) item.findViewById(R.id.txt_item);
            //myTextB = (TextView) item.findViewById(R.id.txt_body);
        }
    }
}
