package com.ali.question.inner;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.main.MainActivity;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */

public class AdapterCardViewInnerPage extends RecyclerView.Adapter<ViewHolderInner> {

    Context context;
    LayoutInflater layoutInflater;

    //////i'm create this constroctor
    public AdapterCardViewInnerPage(Context context)
    {
        this.context=context;
        layoutInflater=layoutInflater.from(context);
    }
    @Override
    public ViewHolderInner onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.card_view_inner,parent,false);
        ViewHolderInner viewHolder =new ViewHolderInner(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolderInner holder, final int position) {

        ///borkan inja meshghabli
        holder.number.setText(position+1+"");
        Log.i("posion1",position+"");
        holder.soal.setText(ActivityInnerPage.soal.get(position).getQustion());
        holder.r1.setText(ActivityInnerPage.soal.get(position).getG1());
        holder.r2.setText(ActivityInnerPage.soal.get(position).getG2());
        holder.r3.setText(ActivityInnerPage.soal.get(position).getG3());
        holder.r4.setText(ActivityInnerPage.soal.get(position).getG4());

        Typeface typeface = Typeface.createFromAsset(MainActivity.context.getAssets(),"font/BNazanin.ttf");
        holder.soal.setTypeface(typeface);
        holder.r1.setTypeface(typeface);
        holder.r2.setTypeface(typeface);
        holder.r3.setTypeface(typeface);
        holder.r4.setTypeface(typeface);
        holder.number.setTypeface(typeface);
        ////
        if(ActivityInnerPage.tab.equals("answer")) {
            if (ActivityInnerPage.soal.get(position).getAnswer().equals("1")) {
                holder.r1.setChecked(true);
            } else if (ActivityInnerPage.soal.get(position).getAnswer().equals("2")) {
                holder.r2.setChecked(true);
            } else if (ActivityInnerPage.soal.get(position).getAnswer().equals("3")) {
                holder.r3.setChecked(true);
            } else if (ActivityInnerPage.soal.get(position).getAnswer().equals("4")) {
                holder.r4.setChecked(true);
            }
        }



        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            private RadioButton radioButton;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb1:
                        Toast.makeText(MainActivity.context, "::jgj:"+1+"__"+position, Toast.LENGTH_SHORT).show();
                        ActivityInnerPage.javab.get(position).setAnswer("1");

                        break;
                    case R.id.rb2:
                        Toast.makeText(MainActivity.context, ":::"+2+"__"+position, Toast.LENGTH_SHORT).show();
                        ActivityInnerPage.javab.get(position).setAnswer("2");
                        break;
                    case R.id.rb3:
                        Toast.makeText(MainActivity.context, ":::"+3+"__"+position, Toast.LENGTH_SHORT).show();
                        ActivityInnerPage.javab.get(position).setAnswer("3");
                        break;
                    case R.id.rb4:
                        Toast.makeText(MainActivity.context, ":::"+4+"__"+position, Toast.LENGTH_SHORT).show();
                        ActivityInnerPage.javab.get(position).setAnswer("4");
                        break;
                }


                // Toast.makeText(MainActivity.context, ":::"+//value+"__"+position, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return ActivityInnerPage.soal.size();
        //return 3;
    }

}
