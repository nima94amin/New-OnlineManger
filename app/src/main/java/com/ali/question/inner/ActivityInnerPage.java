package com.ali.question.inner;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.databaseAndSever.JSONParser;
import com.ali.question.databaseAndSever.dbConnector;
import com.ali.question.main.MainActivity;
import com.ali.question.start.Start;
import com.ali.question.structure.Answer;
import com.ali.question.structure.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityInnerPage extends Activity {

    //*************    ArrayList   *************//

    public static ArrayList<Structure> soal = new ArrayList<Structure>();
    public static ArrayList<Answer> javab = new ArrayList<Answer>();

    //*************    database   *************//
    dbConnector db2;
   // dbServer dbCk,dbUser;

    //*************    button   *************//

    private Button btnsave;

    //*************    listView   *************//

    //*************    text view   *************//

    private TextView txtresult;

    //*************   variables    *************//

    private int layoutId;
    private int id;

    private String name;
    public static String tab;

    Boolean flagRegister = false;

    //*************   context    *************//

    //*************    sharedPreferences   *************//
    SharedPreferences sharedPreferences ;
    public String useInInnerPage;
   //*************    url   *************//
    private final String urlInsertAnswerUser2 ="http://aliexamination.ir/appUser/insertAnswerUser.php";
    private final String urlInsertAnswerUser  ="http://aliexamination.ir/appUser/insertAnswerUser.php";
    private final String urlselectRegisterUser ="http://aliexamination.ir/appUser/selectRegisterUser.php";
    private final String urlInsertAnswerTypeUser ="http://aliexamination.ir/appUser/insertAnswerTypeUser.php";


    //******************  RecyclerView  **************//

    private RecyclerView recyclerView;


    //******************  collapsingToolbarLayout  **************//

    private CollapsingToolbarLayout collapsingToolbarLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(251,192,45));
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_inner_page);
        btnsave   = (Button) findViewById(R.id.btnsave);
        txtresult = (TextView)findViewById(R.id.txt_result);
        AdapterCardViewInnerPage adapterCardViewInnerPage = new AdapterCardViewInnerPage(MainActivity.context);
        recyclerView.setAdapter(adapterCardViewInnerPage);
        recyclerView.setLayoutManager(new LinearLayoutManager((MainActivity.context)));

        Bundle extra = getIntent().getExtras();
        //dbCk = new dbServer(this,"test", null, 1);
        //dbUser = new dbServer(this, "test", null, 1);

        if (extra != null) {
            id = Integer.parseInt(extra.getString("id"));
            tab=extra.getString("tab");


        }
        if(tab.equals("qustion")){
            name = Start.noo.get(id);                ///mi famim ke kdom noo minbashad...
            selectQustion(name);
            selectAnswer(name);                      ////    braye kilc ha va sabte javaba lazam ast...
            Log.i("extra", name);
            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Ckliv222",check(name).toString());

                    //////////////////////chck  this is register or no...

                    sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                    String username=  sharedPreferences.getString("username","ali");
                     useInInnerPage= sharedPreferences.getString("username","ali");

                    //******************  ?????  sever need for check register user **************//  1_sever

                   new class_selectRegisterUser(username).execute();

                }
            });

        }else if(tab.equals("answer")){
            name = Start.sloved.get(id);                ///mi famim ke kdom noo minbashad...
            selectQustion(name);
            selectAnswer(name);       ////    fek konam inja lazem nadarom vali barsi kon...????????????
            Log.i("extra", name);
            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    txtresult.setText("جواب های شما ثبت شده است");

                }
            });

        }
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));




    }


    //******************  this function for find question form dbPhone for use in ?????   **************//
    private void selectQustion(String name) {
        soal.clear();
        Log.i("succcali", "seccc");

        try {
            db2 = new dbConnector(this, "soal", null, 1);
            Cursor cursor = null;
            String query = "SELECT * FROM 'soalat'  WHERE type='" + name + "'" ;

            cursor = db2.select(query);

            /*Log.i("destpath", "" + database + "::" + DestinationFile);

            Cursor cursor = database.rawQuery("SELECT * FROM 'soalat'  WHERE type='" + name + "'", null);*/

            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex("id"));                        ////shenase har soal ast...   if select from jadval javab mintonam javab hara ham pida konam...
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String qustion = cursor.getString(cursor.getColumnIndex("qustion"));
                String g1 = cursor.getString(cursor.getColumnIndex("g1"));
                String g2 = cursor.getString(cursor.getColumnIndex("g2"));
                String g3 = cursor.getString(cursor.getColumnIndex("g3"));
                String g4 = cursor.getString(cursor.getColumnIndex("g4"));

                String answer = (cursor.getString(cursor.getColumnIndex("answer")));   //taghir bede...az jadavle javab...

                Structure structure = new Structure(type, id, qustion, g1, g2, g3, g4, answer);
                structure.setType(type);
                structure.setId(id);
                structure.setQustion(qustion);
                structure.setG1(g1);
                structure.setG2(g2);
                structure.setG3(g3);
                structure.setG4(g4);
                structure.setAnswer(answer);

                soal.add(structure);
                Log.i("xsoaInner", "t: " + soal);
                Log.i("succc", "seccc");
            }
            Log.i("succc2", "seccc");
        } catch (Exception e) {
            Log.i("succc3", "seccc");
            e.printStackTrace();
        }

    }

    //******************  this function for find answer form dbPhone for use in ?????  **************//
    private void selectAnswer(String name) {     ///put id's in list javab..

        javab.clear();

        try {
            db2 = new dbConnector(this, "soal", null, 1);
            Cursor cursor = null;
            String query = "SELECT id , answer FROM 'soalat'  WHERE type='" + name + "'" ;

            cursor = db2.select(query);



            //Cursor cursor = database.rawQuery("SELECT id , answer FROM 'soalat'  WHERE type='" + name + "'", null);

            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex("id"));
                String answer = cursor.getString(cursor.getColumnIndex("answer"));

                Answer answer1 = new Answer(id, answer);
                answer1.setId(id);
                answer1.setAnswer(answer);

                javab.add(answer1);

                Log.i("xjavab", "t: " + javab.get(0).getId().toString());
                Log.i("succc-anssss", "seccc");
            }
            Log.i("succc-anssss", "seccc");
        } catch (Exception e) {
            Log.i("succc3-anssss", "seccc");
            e.printStackTrace();
        }

    }

    //******************  this function for check weather user answer type or no    ????? need to server or no
    //                    if history currect upolad in phone check not need to sever connect for find answer   **************//
    private Boolean check(String name){
        db2 = new dbConnector(this, "soal", null, 1);
        Cursor c = null;

        String query = "SELECT type FROM 'type' WHERE type= '"+name+"' and slove='1' ";
        c = db2.select(query);

        while(c.moveToNext())
        {
            String type  = c.getString(c.getColumnIndex("type"));
            return true;
        }
        return false;

    }

    //******************  for set answer in sever and phone dataBase  **************//
    private void setAnswer(String username,String type) {


        if(isNetworkConnected()){ // check internet connection
            /*String ans2 = ActivityInnerPage.javab.get(0).getAnswer().toString();
            String id2 = ActivityInnerPage.javab.get(0).getId().toString();
            new class_insertAnswerUser(username,type,ans2,id2).execute();*/
            try {

                for(int i = 0; i < ActivityInnerPage.javab.size(); i++) {
                    String ans = ActivityInnerPage.javab.get(i).getAnswer().toString();
                    String id = ActivityInnerPage.javab.get(i).getId().toString();

                   //   new class_insertAnswerUser(username,type,ans,id).execute();
                    new class_insertAnswerUser(username,type,id,ans).execute();
                    Log.i("setAnswelog:", ans+":"+username+":"+id);

                }

                db2 = new dbConnector(this, "soal", null, 1);

                Log.i("setAnswer...:", "ALLLLLLLLLLLLLLLIIII");


                for (int i = 0; i < ActivityInnerPage.javab.size(); i++) {
                    String ans = ActivityInnerPage.javab.get(i).getAnswer().toString();
                    String id = ActivityInnerPage.javab.get(i).getId().toString();


                    String KEY_ID = "id";
                    ContentValues values2 = new ContentValues();
                    values2.put("answer" , ans);
                    Boolean cursor = db2.update("soalat", values2,KEY_ID +" = ?",new String[] {id} );
                    Log.i("ansss333333", ActivityInnerPage.javab.get(i).getId().toString());
                    Log.i("ansss2223", cursor.toString()+":"+id);

                }


                //******************  ?????  sever need for set answer **************//  2_sever

            } catch (Exception e) {
                Log.i("succc333333", "seccc");
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
        }

    }
    //******************  collapsingToolbarLayout  **************//
    private void setQustionSlove(String username,String type){

        Log.i("uuuuusername2",useInInnerPage);

        if(isNetworkConnected()){ // check internet connection

                  new class_insertAnswerTypeUser(username,type).execute();
            try {
                db2 = new dbConnector(this, "soal", null, 1);

                Log.i("setAnswer...2:", "ALLLLLLLLLLLLLLLIIII");

                String KEY_ID = "type";
                ContentValues values2 = new ContentValues();
                values2.put("slove" , 1);
                Boolean cursor = db2.update("type", values2,KEY_ID +" = ?",new String[] {name} );

                Log.i("ansss2223", cursor.toString());

                //******************  ?????  sever need for set answer **************//  3_sever



            } catch (Exception e) {
                Log.i("succc333333", "seccc");
                e.printStackTrace();
            }
             }else{
            Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);

        }
        txtresult.setText("جواب های شما ثبت شد");

    }

    //****************** function for check whether user connect to internet  or no ? and +  post this class   **************//

    private boolean isNetworkConnected() { // check internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

        //if(isNetworkConnected()){ // check internet connection

    }


    //****************** this class for add answer user in server ...   **************//


    public class class_insertAnswerTypeUser extends AsyncTask {   /////baraye jostejooie moshakhasate dostan...   ////test...

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String type , username ;


        public class_insertAnswerTypeUser(String username,String type){     //// kmel nashode
            this.type=type;
            this.username=username;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("addAnsert",type+":"+username);

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);
            param.put("type",type);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlInsertAnswerTypeUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("seclect--qusersjton",type+username+"="+t);
                if(t==1){



                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    //****************** this class for add answer user in server ...   **************//


    public class class_insertAnswerUser extends AsyncTask {   /////baraye jostejooie moshakhasate dostan...   ////test...

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String type , username ,ans,idq;


        public class_insertAnswerUser(String username,String type,String idq,String ans){     //// kmel nashode
            this.type=type;
            this.username=username;
            this.idq=idq;
            this.ans=ans;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("addAnsert",type+":"+username);

            HashMap<String,String> param=new HashMap<String , String>();
           param.put("username",username);
            param.put("type",type);
            param.put("idq",idq);
            param.put("ans",ans);


            Log.i("seclect**qusersjton2",type+username+"="+idq+"="+ans);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlInsertAnswerUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("seclect***qusersjton",type+username+"="+t);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    //****************** function for find whether user is registers or no and post this class...   **************//

    public class class_selectRegisterUser extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username;


        public class_selectRegisterUser(String username){
            this.username=username;

            Log.i("usernameInRegister",username);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if(flagRegister){
                /////////////////////////////////////
                if(!check(name)){       //// in ccke ra  ham az server bepors dadad  ??????
                    Log.i("uuuuusername",useInInnerPage);
                    setAnswer(useInInnerPage,name);
                    setQustionSlove(useInInnerPage,name);      ///that save soveld in type table set is true...


                }else{
                    txtresult.setText("جواب های شما ثبت شده است");
                }

            }else{
                txtresult.setText("لطفا ثبت نام کنید ");
                Toast.makeText(getApplicationContext(),"لطفا ثبت نام کنید ",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_registers2","come here");

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectRegisterUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("jsonInRegister","="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("register");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String status= temp.getString("register");
                        if(status.equals("1"))
                            flagRegister=true;
                        else
                            flagRegister=false;

                    }

                }else{
                    flagRegister=false;

                    // Toast.makeText(MainActivity.context,"no imfomainion", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

    }

    //******************  collapsingToolbarLayout  **************//

    //******************  collapsingToolbarLayout  **************//
}
