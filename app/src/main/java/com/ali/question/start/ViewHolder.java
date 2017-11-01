package com.ali.question.start;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.question.R;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView avatar;
    TextView tittle;

    public ViewHolder(View itemView) {
        super(itemView);
        avatar=(ImageView) itemView.findViewById(R.id.avatar);
        tittle=(TextView)  itemView.findViewById(R.id.tittle);

    }
}