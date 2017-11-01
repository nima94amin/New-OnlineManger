package com.ali.question.start;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.question.R;
import com.ali.question.main.MainActivity;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */

public class AdapterCardViewQustion extends RecyclerView.Adapter<ViewHolder>  {

    Context context;
    LayoutInflater layoutInflater;

    ImageView avatar;
    TextView tittle;
    //LinearLayout cardAdapter;

    //////i'm create this constroctor
    public AdapterCardViewQustion(Context context)
    {
        this.context=context;
        layoutInflater=layoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.adapter_card_view,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);

        avatar=(ImageView) view.findViewById(R.id.avatar);
        tittle=(TextView)  view.findViewById(R.id.tittle);
        //cardAdapter =(LinearLayout) view.findViewById(R.id.card_adapter);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tittle.setText(Start.noo.get(position));

        Typeface typeface = Typeface.createFromAsset(MainActivity.context.getAssets(),"font/BNazanin.ttf");
        holder.tittle.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return Start.noo.size();
    }

}