package com.ali.question.main;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.databaseAndSever.JSONParser;
import com.ali.question.databaseAndSever.dbConnector;
import com.ali.question.start.Start;
import com.ali.question.structure.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //*************    ArrayList   *************//

    public static ArrayList<Structure> soalH   = new ArrayList<Structure>();

    public static ArrayList<String>   answersUser     =new ArrayList<String>();

    public static ArrayList<String> noo_user = new ArrayList<String>();   //// for history fill this list  and show this in <<qustion tab>>...

    //*************    database   *************//

    SQLiteDatabase database;
    dbConnector db, dbH , dbH2;

    //*************    button   *************//
    Button btnEnter;


    //*************    Dialog   *************//
    private ProgressDialog nDialog;

    //*************    editText   *************//
    EditText txtUsername,txtMobile;
    TextView guide;

    //*************    TextInputLayout   *************//
    android.support.design.widget.TextInputLayout usernameText;
    android.support.design.widget.TextInputLayout mobileText;


    //*************    variables   *************//

    public static String useInInnerPage;
    //String username ,moblie;
    String DestinationFile;
    Boolean flagM=false, flagU=false,flagUM=false;
    String username,mobile;
    Boolean flagRegister = false ;
    public static Boolean flagEnter=true;

    public  int counterStart=0;




    //*************   context    *************//
    public static Context context;

    //*************    sharedPreferences   *************//
    SharedPreferences sharedPreferences ;
    SharedPreferences shairMain;

    //*************    font   *************//
    public static Typeface typeface;


    //*************    url   *************//

    private final String urlselectUM ="http://aliexamination.ir/appUser/selectUsername.php";
    private final String urlInsertFirstUser="http://aliexamination.ir/appUser/insertFristUser.php";
    private final String urlselectQuestions="http://aliexamination.ir/appUser/selectQuestions.php";
    private final String urlselectAnswersUsers="http://aliexamination.ir/appUser/selectAnswersUser.php";
    private final String urlselectAnsewerTypeUser="http://aliexamination.ir/appUser/selectAnswerTypeUser.php";
    private final String urlselectRegisterUser ="http://aliexamination.ir/appUser/selectRegisterUser.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flagEnter=true;
        typeface = Typeface.createFromAsset(MainActivity.this.getAssets(),"font/BNazanin.ttf");

        context=getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(26,35,126));
        }




        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        ///insert username and mob in server brather

        txtUsername = (EditText) findViewById(R.id.txtusername);
        txtMobile    = (EditText) findViewById(R.id.txtmoble);


        //*************    insetfont   *************//

        guide =(TextView)findViewById(R.id.textView2);
        usernameText =(android.support.design.widget.TextInputLayout)findViewById(R.id.input_layout_usename);
        usernameText.setTypeface(typeface);
        mobileText =(android.support.design.widget.TextInputLayout)findViewById(R.id.input_layout_mobile);
        mobileText.setTypeface(typeface);
        guide.setTypeface(typeface);



        useInInnerPage =txtUsername.getText().toString();       ////use in innerpage for chck username is register or no...   or user form shprfrensees...


        btnEnter    = (Button)findViewById(R.id.btn_enter);


        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = txtUsername.getText().toString();
                mobile = txtMobile.getText().toString();
                /////here connect to sever and check username and moblie ...
                Log.i("username2",username+":"+mobile);


                /*Intent intent=new Intent(MainActivity.context, Start.class);     ////send username and mobliel number to start and send form to  regidsters...
                intent.putExtra("username_main",username);
                intent.putExtra("mob_main",mobile);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.context.startActivity(intent);*/

                if(flagEnter){

                    Log.i("Enternet","nok:"+flagEnter);

                    new NetCheck().execute();

                    flagEnter=false;



                }else{
                    Log.i("elseStart",flagEnter.toString());
                }



            }
        });
    }

    @Override
    public void onBackPressed() {
        flagEnter=true;
        super.onBackPressed();
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

    //****************** function for check exists  username and mobile user in sever or no ?   **************//

    public class class_selectUm extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username,mobile;

        public class_selectUm(String username,String moblie){
            this.username=username;
            this.mobile  =moblie;

        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            Log.i("flagU+M+UM", "t:flagU =   " + flagU + "::flagM=" + flagM + "::flagMu=" + flagUM);
            nDialog.dismiss();
            postUM();


        }


        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("go selectUM","come to select um ");

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);     /////ch
            param.put("mobile",mobile);

            Log.i("selctUM",username+":"+mobile);
            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectUM,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("jsonSelectUm","="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("umCheck");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String check = temp.getString("check");
                        if(check.equals("um")){
                            flagM=false;
                            flagU=false;
                            flagUM=true;
                        }
                        if(check.equals("m")){
                            flagM=true;
                            flagU=false;
                            flagUM=false;
                        }
                        if(check.equals("u")){
                            flagM=false;
                            flagU=true;
                            flagUM=false;
                        }
                        Log.i("string_check",check);
                    }

                }else{
                    flagM=false;
                    flagU=false;
                    flagUM=false;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
    }

    private void postUM() {

        //******************  if user is exist or not exists  com to  blow if   **************//


        /*

        1. um =t   ,shu =u and shm=m     ----> start defult
        2. um= t   , shu !=u and shm!=m  ---->  history load form sever
        3. um =f   , shu =u and shm=m     ---->   // impossible
        4. um= f   , shu !=u and shm!=m  ---->   1. add to firstUser in server    2. start first one and copy from assets...

         */
        // String shUsername = sharedPreferences.getString("username","ali");
        // String shMoblie   = sharedPreferences.getString("mobile","ali");

       /* if((shUsername.equals("username")&&shMoblie.equals("mobile")) &&flagUM==true  ){



        }
        else if((!shUsername.equals("username")&& !shMoblie.equals("mobile")) && flagUM==true ){




        }else if((!shUsername.equals("username")&& !shMoblie.equals("mobile")) && flagUM==false ){

        }*/

        if(flagUM==true||(flagM==false && flagU==false)){


            /////// add in sever username and mobile if not exists  first work    .......  1

            String shUsername = sharedPreferences.getString("username","ali");
            String shMoblie   = sharedPreferences.getString("mobile","ali");


            Log.i("mainStartfirstif","com +"+shUsername+shMoblie);

            //******************  this if for history if user exist and first enters ...  **************//

            if((shUsername.equals("ali")&&shMoblie.equals("ali")) &&flagUM==true  ){       ///register user and first enter com to here
                Log.i("mainStart232","com hser11");
                if(isNetworkConnected()){ // check internet connection

                    new class_selectRegisterUser(username).execute();

                }else{
                    Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
                }



            }
            else if((shUsername.equals("ali") && shMoblie.equals("ali")) || (shUsername.equals(username)&&shMoblie.equals(mobile))){

                ///etlaate hamin karbar ra nemayesh bede va kar digary na kon    else    jadval ra delete kon....
                Log.i("mainStart2if22","com hser3333222252");

                if(shMoblie.equals("ali")){        //////this add later check this
                    Log.i("mainStart2if","com hser3333222252");
                    if(isNetworkConnected()){ // check internet connection

                        new class_insertFirstUser(username,mobile).execute();

                    }
                }

                Intent intent=new Intent(MainActivity.context, Start.class);     ////send username and mobliel number to start and send form to  regidsters...
                intent.putExtra("username_main",username);
                intent.putExtra("mob_main",mobile);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.context.startActivity(intent);


            }else {    //// histori ra inja minevishim age reg =1 boad histori  vagar na hamon ade...    || new user come here

                if(isNetworkConnected()){ // check internet connection

                    new class_selectRegisterUser(username).execute();

                }else{
                    Toast.makeText(getApplicationContext(),"اینترنت" +
                            " متصل نیست",Toast.LENGTH_LONG);
                }

                Log.i("mainStart2else","com hser");

            }

            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("username",username);
            editor.putString("mobile",mobile);
            editor.apply();

            ////////////////// azafe kardan first_user va baz kardane start mitavanad inja ham bashad ...lazem nist be post bebarim..


        }
        else if(flagM==true && flagU==false){
            flagEnter=true;

            Toast.makeText(getApplicationContext(),"شماره موبایل موجود است",Toast.LENGTH_LONG).show();


        }else if(flagM==false && flagU==true){
            flagEnter=true;

            Toast.makeText(getApplicationContext(),"نام کاربری موجود می باشد",Toast.LENGTH_LONG).show();

        }else if(flagUM==false){
            flagEnter=true;
            Toast.makeText(getApplicationContext(),"نام کاربری و شماهره موبایل دیگری را انتخاب کنید",Toast.LENGTH_LONG).show();
        }
    }

    //****************** function for insert user in firstUser    **************//


    public class class_insertFirstUser extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username,mobile;
        Boolean flag = false;


        public class_insertFirstUser(String username,String mobile){
            this.username=username;
            this.mobile =mobile;

            Log.i("usernameseinsertuser",username+"::"+mobile);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("insert_user ","come here");

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);
            param.put("mobile",mobile);
            param.put("register" ,"0");


            JSONObject jsonObject =jsonParser.makeHttpRequest(urlInsertFirstUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("jsonInsertUser","="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("register");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);


                    }

                }else{

                    // Toast.makeText(MainActivity.context,"no imfomainion", Toast.LENGTH_SHORT).show();
                }

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
            postRegisters();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_registers","come here");

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

    private void postRegisters(){

        Log.i("flagRegisters", "t: " + flagRegister);
        ///////////////////
        if (flagRegister) {    ////find history for users...

            int counter;
            db = new dbConnector(context, "soal", null, 1);
            Boolean c ;
            String query = "drop table type" ;
            String query2 = "drop table soalat" ;

            c = db.exec(query);
            c = db.exec(query2);

            //// az sever histori ra byar...


            noo_user.clear();
            if(isNetworkConnected()){ // check internet connection

                new class_selectAnsewerdTypeUser(username).execute();       //// find type for history
            }else{
                Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
            }


        }else{ ////else start default  ...
            shairMain = getSharedPreferences("frist", MODE_PRIVATE);   /// inja hamon ade ast...

            SharedPreferences.Editor edit = shairMain.edit();
            edit.putString("db", "ali");   //////db besaz....
            edit.apply();


            db = new dbConnector(context, "soal", null, 1);
            Boolean c ;
            String query = "drop table type" ;
            String query2 = "drop table soalat" ;

            c = db.exec(query);
            c = db.exec(query2);
            Log.i("delect...230","filecreat"+"::"+c);
            if(isNetworkConnected()){ // check internet connection

                new class_insertFirstUser(username,mobile).execute();

            }else{
                Toast.makeText(getApplicationContext(),"اینترنت" +
                        " متصل نیست",Toast.LENGTH_LONG);
            }

            Intent intent=new Intent(MainActivity.context, Start.class);     ////send username and mobliel number to start and send form to  regidsters...
            intent.putExtra("username_main",username);
            intent.putExtra("mob_main",mobile);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.context.startActivity(intent);
        }
    }

    //****************** function for find types that user answered this for find history  and post this class ...   **************//

    public class class_selectAnsewerdTypeUser extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username ;



        public class_selectAnsewerdTypeUser(String username){
            this.username=username;

            Log.i("usernameInTypeUser",username);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            postAnswerTypeUser();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("typeUser","come here");

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectAnsewerTypeUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("jsonTypeUser","="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("types");    /// input json response["types"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String type = temp.getString("type");


                        noo_user.add(type);

                        //firend_user.get(i).setUsername();
                        Log.i("listNoo_user", "t: " + noo_user);

                    }

                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    private void postAnswerTypeUser(){

        for (int i = 0; i < noo_user.size(); i++) {
            Log.i("xnooUser_server2ii", "t: " + noo_user.get(i).toString());


            /////////////////////this  code find questions in table question in server...
            if(isNetworkConnected()){ // check internet connection

                new class_selectQuestoinsUsers(noo_user.get(i).toString()).execute();

            }else{
                Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
            }



            //****************** insert every type in database phone...   **************//

            Log.i("after1Inpostanswe","1");

           /* dbH = new dbConnector(context, "soal", null, 1);       /////inja mymanad...  ///move to post selec quesyion

            ContentValues valuesQ = new ContentValues();

            valuesQ.put("type" , noo_user.get(i).toString());
            valuesQ.put("count" , answersUser.size());
            valuesQ.put("slove" , 1);

            Boolean status = dbH.insert("type", valuesQ);
            Log.i("soalH.size(", soalH.size()+":::seccc:"+answersUser.size());*/
        }

        ///////////////copy in db phone

        //****************** ????? check weather copy all qusion or noo...   **************//


        dbH2 = new dbConnector(context, "soal", null, 1);
        ContentValues valuesQ = new ContentValues();
        for(int i=0;i<soalH.size();i++){

            valuesQ.put("id" , soalH.get(i).getId().toString());
            valuesQ.put("type" , soalH.get(i).getType().toString());
            valuesQ.put("qustion" , soalH.get(i).getQustion().toString());
            valuesQ.put("g1" , soalH.get(i).getG1().toString());
            valuesQ.put("g2" , soalH.get(i).getG2().toString());
            valuesQ.put("g3" , soalH.get(i).getG3().toString());
            valuesQ.put("g4" , soalH.get(i).getG4().toString());
            valuesQ.put("answer",soalH.get(i).getAnswer().toString());
            Boolean status = dbH2.insert("soalat", valuesQ);
            Log.i("statusQ",status+"");
        }

    }

    //****************** function for find qustion that user answered this for find history...   **************//


    public class class_selectQuestoinsUsers extends AsyncTask {   /////baraye jostejooie moshakhasate dostan...   ////test...

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String type;


        public class_selectQuestoinsUsers(String type){     //// kmel nashode
            this.type=type;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            answersUser.clear();
            if(isNetworkConnected()){ // check internet connection

                new class_selectAnswersUsers(username,type).execute();   ///find answer for any type befor find qustion

            }else{
                Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            /////////////////////for put answer for user next...

            if(counterStart==noo_user.size()) {
                new class_insertFirstUser(username, mobile).execute();       //////????? braye chee

                Intent intent = new Intent(MainActivity.context, Start.class);     ////send username and mobile number to start and send form to  regidsters...
                intent.putExtra("username_main", username);
                intent.putExtra("mob_main", mobile);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.context.startActivity(intent);  //move to post type answer
            }
            dbH = new dbConnector(context, "soal", null, 1);       /////inja mymanad...  ///move to post selec quesyion

            ContentValues valuesQ = new ContentValues();

            valuesQ.put("type" , type);
            valuesQ.put("count" , answersUser.size());
            valuesQ.put("slove" , 1);

            Boolean status = dbH.insert("type", valuesQ);
            Log.i("soalH.size(", soalH.size()+":::seccc:"+answersUser.size());

        }

        @Override
        protected Object doInBackground(Object[] params) {
            counterStart++;

            Log.i("quaertion",type+":"+counterStart);

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("type",type);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectQuestions,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("seclect qusersjton",type+"="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("questions");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String id=temp.getString("id");
                        String type=temp.getString("type");
                        String question =temp.getString("question");
                        String g1 =temp.getString("g1");
                        String g2=temp.getString("g2");
                        String g3 =temp.getString("g3");
                        String g4=temp.getString("g4");
                        String answer = answersUser.get(i).toString();

                        Structure soal_severH = new Structure(id, type, question, g1, g2, g3, g4,answer);
                        soal_severH.setType(type);
                        soal_severH.setId(id);
                        soal_severH.setQustion(question);
                        soal_severH.setG1(g1);
                        soal_severH.setG2(g2);
                        soal_severH.setG3(g3);
                        soal_severH.setG4(g4);
                        soal_severH.setAnswer(answer);


                        soalH.add(soal_severH);
                        Log.i("xsoalH_server", "t: " + soalH);

                    }

                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

    //****************** function for find answers that user answered this for find history...   **************//


    public class class_selectAnswersUsers extends AsyncTask {   /////baraye jostejooie moshakhasate dostan...   ////test...

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String type,username;


        public class_selectAnswersUsers(String username,String type){
            this.type=type;
            this.username=username;

        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }
        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("type+user+in+answers",type+":"+username);

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("type",type);
            param.put("username",username);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectAnswersUsers,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("jsonanswerusers",type+"="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("answers");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String answers=temp.getString("answer");

                        answersUser.add(answers);

                        Log.i("alialialiinfo2score",answersUser.toString());

                    }

                }else{
                    //Toast.makeText(MainActivity.context,"no imfomainion", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }


    }



    private class NetCheck extends AsyncTask<String,String,Boolean>
    {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(MainActivity.this);
            nDialog.setTitle("بررسی اتصال اینترنت ");
            nDialog.setMessage("Loading..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);

            nDialog.show();
        }
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
        @Override
        protected Boolean doInBackground(String... args){

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){

                ////enter your code ...


                new class_selectUm(username, mobile).execute();
                Toast.makeText(getApplicationContext(), "Enter succes", Toast.LENGTH_SHORT).show();


            }
            else{
                flagEnter=true;
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error in Network Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

