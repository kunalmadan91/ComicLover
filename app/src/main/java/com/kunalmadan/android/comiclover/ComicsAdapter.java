package com.kunalmadan.android.comiclover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KUNAL on 03-09-2016.
 */
public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.MyViewHolder> {

    private ArrayList<Comic> comicArrayList ;
    private Context mContext;

    public ComicsAdapter(Context context, ArrayList<Comic> data) {
        comicArrayList = new ArrayList<>();
        comicArrayList = data;
        mContext = context;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price;

        public MyViewHolder(View view) {

            super(view);
            Log.v("inviewholder","inviewholder");
            title = (TextView) view.findViewById(R.id.titleText);
            price = (TextView) view.findViewById(R.id.price);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_strips, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
              final Comic comicObj =   comicArrayList.get(position);
        Log.v("price","price"+comicObj.getPrice());
        if(comicObj.getPrice() == "0") {
            holder.price.setText(mContext.getResources().getString(R.string.text_not_available));

        }

        else {
            holder.price.setText(mContext.getResources().getString(R.string.currency)+comicObj.getPrice());
        }

        Log.v("hellloo","hellloo"+comicObj.getTitle()+"price"+comicObj.getPrice());
        holder.title.setText(comicObj.getTitle());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ComicDetail.class);

                intent.putExtra("COMIC_OBJ",comicObj);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

                //intent.putParcelableArrayListExtra("",mdata);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v("size","size"+comicArrayList.size());

        return comicArrayList.size();
    }
}
