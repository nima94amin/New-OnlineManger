package com.ali.question.start;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.inner.ActivityInnerPage;
import com.ali.question.main.MainActivity;


/**
 * Created by Ali_Najafi on 12/02/2017.     ///////1 ma az inja seda zadim
 */
public class PageFragment extends Fragment {
    private  int mPage;
    public static final String ARG_PAGE="ARG_PAGE";

    TextView title;

    RecyclerView recyclerView;

    AdatpterCardViewSlove adatpterCardViewSlove;
    static AdapterCardViewQustion adapterCardView;



    public static PageFragment newInstance(int page){

        Bundle args=new Bundle();
        args.putInt(ARG_PAGE,page);
        PageFragment fragment =new PageFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);
        //title=(TextView)findViewById(R.id.tittle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view =inflater.inflate(R.layout.frag_layout,container,false);

        //final String ti=title.getText().toString();
        // Log.i("ali_log",ti);
        if(mPage==2) {
            view =inflater.inflate(R.layout.frag_layout,container,false);

            recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
            adapterCardView=new AdapterCardViewQustion(MainActivity.context);
            recyclerView.setAdapter(adapterCardView);
            recyclerView.setLayoutManager(new LinearLayoutManager((MainActivity.context)));
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.context, recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //Movie movie = movieList.get(position);
                    Toast.makeText(MainActivity.context,  position+" is selected!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.context, ActivityInnerPage.class);
                    // intent.putExtra("name",ti);
                    intent.putExtra("id",position+"");
                    intent.putExtra("tab","qustion");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.context.startActivity(intent);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            Log.i("Aval","soalat");

        }else{
            view =inflater.inflate(R.layout.frag_layout,container,false);

            recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
            adatpterCardViewSlove =new AdatpterCardViewSlove(MainActivity.context);
            recyclerView.setAdapter(adatpterCardViewSlove);
            recyclerView.setLayoutManager(new LinearLayoutManager((MainActivity.context)));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
           // recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.context, LinearLayoutManager.VERTICAL));
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.context, recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //Movie movie = movieList.get(position);
                    Toast.makeText(MainActivity.context,  position+" is selected!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.context, ActivityInnerPage.class);
                    // intent.putExtra("name",ti);
                    intent.putExtra("id",position+"");
                    intent.putExtra("tab","answer");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.context.startActivity(intent);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }




        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        if(adatpterCardViewSlove!=null)
            adatpterCardViewSlove.notifyDataSetChanged();


    }
}

