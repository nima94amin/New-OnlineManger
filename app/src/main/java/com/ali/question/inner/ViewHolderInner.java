package com.ali.question.inner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ali.question.R;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */

public class ViewHolderInner extends RecyclerView.ViewHolder {
    TextView number;
    TextView soal;
    RadioButton r1,r2,r3,r4;
    RadioGroup radioGroup;

    public ViewHolderInner(View itemView) {
        super(itemView);
        number=(TextView) itemView.findViewById(R.id.tv_number);
        soal=(TextView)   itemView.findViewById(R.id.txt_qustion);
        r1=(RadioButton)  itemView.findViewById(R.id.rb1);
        r2=(RadioButton)  itemView.findViewById(R.id.rb2);
        r3=(RadioButton)  itemView.findViewById(R.id.rb3);
        r4=(RadioButton)  itemView.findViewById(R.id.rb4);
        radioGroup=(RadioGroup) itemView.findViewById(R.id.radioGrop);

    }
}
