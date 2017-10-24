package com.example.roshan.appybites.RecyclerView_Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.roshan.appybites.Chef.Chef_description;
import com.example.roshan.appybites.Item_description;
import com.example.roshan.appybites.Model_Class.Chef_class;
import com.example.roshan.appybites.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by roshan on 1/5/17.
 */

public class Chef_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private View view;
    private LayoutInflater inflater;
    List<Chef_class> chef_list= Collections.emptyList();

    public Chef_Adapter(Context context, List<Chef_class> chef_list) {
        this.context=context;
        if (context !=null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            // inflater= LayoutInflater.from(context);
        }
        this.chef_list=chef_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.fragment_chef, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
// Get current position of item in recyclerview to bind data and assign values from list
        final Chef_Adapter.MyHolder myHolder = (Chef_Adapter.MyHolder) holder;
        final Chef_class current = chef_list.get(position);
        System.out.println("chef_name "+current.getChef_name());
        myHolder.tvName.setText(current.getChef_name());
        myHolder.tvChefid.setText(current.getChef_id());
        myHolder.tvChefdesc.setText(current.getChef_description());

        System.out.println("image "+current.getChef_image());
        // load image into imageview using glide
        Picasso.with(context).load(current.getChef_image())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(myHolder.imageicon);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Chef_class current=chef_list.get(position);
                Intent i=new Intent(context, Chef_description.class);
                i.putExtra("chef_name",current.getChef_name());
                i.putExtra("chef_id",current.getChef_id());
                i.putExtra("chef_description",current.getChef_description());
                i.putExtra("chef_image",current.getChef_image());
                Bundle bundle=new Bundle();
                bundle.putString("IMAGE",current.getChef_image());
                i.putExtras(bundle);
                System.out.println("Image file: "+bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chef_list.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView imageicon;
        private TextView tvName,tvChefid,tvChefdesc;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            imageicon=(ImageView)itemView.findViewById(R.id.chef_image);
            tvName=(TextView)itemView.findViewById(R.id.txt_chef_name);
            tvChefdesc=(TextView)itemView.findViewById(R.id.chef_desc);
            tvChefid=(TextView)itemView.findViewById(R.id.chef_id);
        }

    }

}
