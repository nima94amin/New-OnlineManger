package com.ali.question.start;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.app;
import com.ali.question.databaseAndSever.JSONParser;
import com.ali.question.databaseAndSever.dbConnector;
import com.ali.question.main.MainActivity;
import com.ali.question.menu.BuilderManager;
import com.ali.question.menu.Invaite;
import com.ali.question.menu.Register;
import com.ali.question.profile.Profile;
import com.ali.question.setting.SettingActivity;
import com.ali.question.structure.Soal_sever;
import com.ali.question.structure.Structure;
import com.ali.question.structure.Type_server;
import com.nightonke.boommenu.Animation.OrderEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Start extends AppCompatActivity {

    //*************    ArrayList   *************//
    public static ArrayList<String> noo   = new ArrayList<String>();   ////// in dota noo va count ra az class typeServer astefade kon...
    public static ArrayList<String> count   = new ArrayList<String>();
    public static ArrayList<Type_server> nootemp=new ArrayList<Type_server>();  ///for give noo from db phone for moghyese ke betavaim list ra noo ra beroz konim list temp zeyad nashvad....
    public static ArrayList<Structure> soal = new ArrayList<Structure>();
    public static ArrayList<String> sloved   = new ArrayList<String>();
    public static ArrayList<Type_server> typeServer = new ArrayList<Type_server>();  ///for refresh qustions form seveers...
    public static ArrayList<Soal_sever> qustionServer = new ArrayList<Soal_sever>();  ///for put list of qustoinserver for move to phon db


    //*************    database   *************//
    dbConnector db;
    dbConnector db2;
    //dbServer db3;  ////for give noo form server...
    SQLiteDatabase database;


    //*************    button   *************//
    Button btnrigister;
    Button btnrefresh;
    Button btninvate;


    //*************    listView   *************//

    //*************    editText   *************//

    TextView txtHeaderUsername;
    TextView txtHeaderMobile;
    TextView txtHeaderGauid;
    //*************   variables    *************//

    String  destpath="";
    String DestinationFile;
    String username,moblie,username_pro;

    Boolean falgRefrsh =true;


    //*************   menu    *************//
    BoomMenuButton bmb;

    //*************    sharedPreferences   *************//
    SharedPreferences share;

    //*************    url   *************//
    private final String urlselectTypeServer ="http://aliexamination.ir/appUser/selectTypeServer.php";
    private final String urlselectQuestions="http://aliexamination.ir/appUser/selectQuestions.php";



    //*************    navigation   *************//

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerview;
    ImageView hamberger;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_start);

        //Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/BNazanin.ttf");
       // Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Arial.ttf");

        //final Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/your_font_name");
       // Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/your font.ttf");
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        Typeface typeface = Typeface.createFromAsset(MainActivity.context.getAssets(),"font/BNazanin.ttf");

        db = new dbConnector(this, "soal", null, 1);

        setContentView(R.layout.navagation_activty_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(21,101,192));
        }

        //*************    boomMenu   *************//

             boomMenu();

        //*************    navigation   *************//

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.navagation_view);
        hamberger=(ImageView)findViewById(R.id.hamderger);

         headerview = navigationView.getHeaderView(0);

        txtHeaderUsername =(TextView)headerview.findViewById(R.id.txt_header_username);
        txtHeaderMobile =(TextView) headerview.findViewById(R.id.txt_header_mobile);
        txtHeaderGauid =(TextView) headerview.findViewById(R.id.txt_guid_header);



        txtHeaderUsername.setTypeface(typeface);
        txtHeaderMobile.setTypeface(typeface);
        txtHeaderGauid.setTypeface(typeface);

        Bundle extra = getIntent().getExtras();
        username_pro=extra.getString("username_main");
        txtHeaderUsername.setText(extra.getString("username_main"));
        txtHeaderMobile.setText(extra.getString("mob_main"));


        hamberger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id=item.getItemId();
                if(id== R.id.about_us) {
                    Toast.makeText(getApplicationContext(),"درباره ما",Toast.LENGTH_SHORT).show();


                }else if(id==R.id.call_us){
                    Toast.makeText(getApplicationContext(),"تماس با ما",Toast.LENGTH_SHORT).show();

                }else if(id==R.id.profile){

                    Toast.makeText(getApplicationContext(),"پروفایل",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(Start.this,Profile.class);
                    intent.putExtra("username_start_p",username_pro);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    Start.this.startActivity(intent);

                }else if(id==R.id.setting){
                    Toast.makeText(getApplicationContext(),"تنطیمات",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(Start.this,SettingActivity.class);
                   Start.this.startActivity(intent);

                }

                return true;
            }
        });




        //******************  SURVEY user inter for use form application for first or any   **************//

        share = getSharedPreferences("frist", MODE_PRIVATE);

        String start  = share.getString("db", "ali");    ////// baraye avalin bar.



        Log.i("start23...",start);

        if(start.equals("ali")){       //// bayad database asli ra por konim braye avalin bar

            Log.i("else","Com222");


            //for call data base...  in oncreate ...
            try {

                Context Context = getApplicationContext();
                DestinationFile = Context.getFilesDir().getPath() + File.separator + app.main.databseNamePhone;
                Log.i("Log2",DestinationFile);
                File file = new File(DestinationFile);

                if (!file.exists()) {     ////ahtmatln injja aslan nayayad...
                    file.mkdirs();
                    file.createNewFile();
                    Log.i("filestart","filecreat");
                    copyDB(getBaseContext().getAssets().open("soal.sqlite"), new FileOutputStream(DestinationFile+ "/soal.sqlite"));

                }
                else{
                    copyDB(getBaseContext().getAssets().open("soal.sqlite"), new FileOutputStream(DestinationFile+ "/soal.sqlite"));

                }
            } catch (FileNotFoundException e) {
                Log.i("Cannot 1","not connect to db1");
                e.printStackTrace();

            } catch (IOException e) {
                Log.i("Cannot 2","not connect to db2");
                e.printStackTrace();
            }

            selectTypeTemp();
            selectQustion();
            copy();


            SharedPreferences.Editor edit = share.edit();
            edit.putString("db", "false");
            edit.apply();

        }
        else{    ///  inja dighar az data base asli astefade mikonim

            selectType();
            selectSloved();     ////for use in tap seloved

            Log.i("else","Com");
            // selectQustion();
             //copy();

        }

        intit();
    }



    private void boomMenu() {
        bmb = (BoomMenuButton) findViewById(R.id.bmb);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(BuilderManager.imageResources[i])
                    .normalText(BuilderManager.imageText[i])
                    .rotateImage(true)
                    .textSize(14)
                    .maxLines(2)
                    .textHeight(100)
                    .typeface(Typeface.DEFAULT_BOLD)
                    .buttonRadius(Util.dp2px(40))
                    .textPadding(new Rect(0, 10, 0, 0))

                    ;

            bmb.addBuilder(builder);
        }
        bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Center); //Button Place Alignments
        bmb.setOrderEnum(OrderEnum.REVERSE);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) //addBuilder();

            // Use OnBoomListenerAdapter to listen part of methods
            bmb.setOnBoomListener(new OnBoomListenerAdapter() {
                @Override
                public void onBoomWillShow() {
                    super.onBoomWillShow();
                    // logic here
                }
            });
        // Use OnBoomListener to listen all methods
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
                //ali2.setText("index"+index);
                Toast.makeText(getApplicationContext(),index+"",Toast.LENGTH_LONG).show();
                if(index==0){     //invaite

                    Intent intent=new Intent(MainActivity.context, Invaite.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.context.startActivity(intent);

                }else if(index==1){  //register

                    Bundle extra = getIntent().getExtras();

                    if (extra != null) {
                        username = extra.getString("username_main");
                        moblie =extra.getString("mob_main");

                        Log.i("bundel111",username+"::"+moblie);
                    }
                    Intent intent=new Intent(MainActivity.context, Register.class);
                    intent.putExtra("username_start",username);
                    intent.putExtra("mobile_start",moblie);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.context.startActivity(intent);

                }else if(index==2){  //refresh

                    typeServer.clear();

                    ///batyad noo va typeServer ra baham moghayese konim avalan bayad typeServer ra por konim...   2

                    //selectTypeServer();      /////2 ta az en tabe darim....
                    if(falgRefrsh){
                        qustionServer.clear();

                        new class_selectTypeServer().execute();
                        falgRefrsh=false;


                    }

                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }

        });

    }

    @Override
    public void onBackPressed() {
        sloved.clear();
        MainActivity.flagEnter=true;
        if(drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else
            super.onBackPressed();
    }
    //******************  initial for call AdpterFragment and inner function its for show Start page  **************//

    private void intit() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new AdapterFragment (getSupportFragmentManager()));

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    //******************  for copy database in phon memory .  **************//

    private  void copyDB(InputStream inputStream , OutputStream outputStream )throws IOException{
        byte [] buffer=new byte[1024];
        int length;

        while ((length= inputStream.read(buffer)) > 0){
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();
        Log.i("come","come to copydb2");
    }

    //******************  for copy database in phon memory .  **************//

    private void selectTypeTemp(){
        noo.clear();
        nootemp.clear();
        try {

            Context Context = getApplicationContext();
            DestinationFile = Context.getFilesDir().getPath() + File.separator + app.main.databseNamePhone;
            database = SQLiteDatabase.openOrCreateDatabase(DestinationFile + "soal.sqlite",null);

            Log.i("destpath",""+database+"::"+DestinationFile);

            Cursor cursor=database.rawQuery("SELECT * FROM 'type' ",null);

            while(cursor.moveToNext()){


                String type=cursor.getString(cursor.getColumnIndex("type"));
                String co=cursor.getString(cursor.getColumnIndex("count"));

                noo.add(type);
                count.add(co);

                ///*************************for server
                Type_server type_server=new Type_server(type,Integer.parseInt(co));
                type_server.setCount(Integer.parseInt(co));
                type_server.setType(type);

                nootemp.add(type_server);

                Log.i("xnoo","t: "+noo+co);
            }
            Log.i("size",noo.size()+"");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******************  for copy database in phon memory .  **************//

    private void selectQustion() {

        soal.clear();

        try {
            Context Context = getApplicationContext();
            DestinationFile = Context.getFilesDir().getPath() + File.separator + app.main.databseNamePhone;
            database = SQLiteDatabase.openOrCreateDatabase(DestinationFile + "soal.sqlite",null);

            Log.i("destpath1",""+database+"::"+DestinationFile);

            Cursor cursor2 = database.rawQuery("SELECT * FROM 'soalat'", null);

            while (cursor2.moveToNext()) {


                String id = cursor2.getString(cursor2.getColumnIndex("id"));                        ////shenase har soal ast...   if select from jadval javab mintonam javab hara ham pida konam...
                String type = cursor2.getString(cursor2.getColumnIndex("type"));
                String qustion = cursor2.getString(cursor2.getColumnIndex("qustion"));
                String g1 = cursor2.getString(cursor2.getColumnIndex("g1"));
                String g2 = cursor2.getString(cursor2.getColumnIndex("g2"));
                String g3 = cursor2.getString(cursor2.getColumnIndex("g3"));
                String g4 = cursor2.getString(cursor2.getColumnIndex("g4"));

                String answer=cursor2.getString(cursor2.getColumnIndex("answer"));   //taghir bede...az jadavle javab...

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
                Log.i("xsoal", "t: " + soal);
                Log.i("succc", "seccc");
            }
            Log.i("succc2", "seccc");
        } catch (Exception e) {
            Log.i("succc3", "seccc");
            e.printStackTrace();
        }

    }

    //******************  for copy database in phon memory .  **************//

    private void copy(){
        db = new dbConnector(this, "soal", null, 1);

        ContentValues values = new ContentValues();
        for(int i=0;i<soal.size();i++){

            values.put("id" , soal.get(i).getId().toString());
            values.put("type" , soal.get(i).getType().toString());
            values.put("qustion" , soal.get(i).getQustion().toString());
            values.put("g1" , soal.get(i).getG1().toString());
            values.put("g2" , soal.get(i).getG2().toString());
            values.put("g3" , soal.get(i).getG3().toString());
            values.put("g4" , soal.get(i).getG4().toString());
            //  if(soal.get(i).getAnswer().toString().equals(null))
            //    values.put("answer" ,"0" );
            //else
            values.put("answer" ,soal.get(i).getAnswer().toString() );

            Boolean status = db.insert("soalat", values);

        }
        ContentValues values2 = new ContentValues();
        for(int i=0;i<noo.size();i++){
            values2.put("type" , noo.get(i).toString());
            values2.put("count" , count.get(i));
            values2.put("slove" ,0);
            Boolean status2 = db.insert("type", values2);

        }



    }

    //******************  for copy database in phon memory .  **************//

    private void selectType(){
        nootemp.clear();
        noo.clear();
        try {

            db2 = new dbConnector(this, "soal", null, 1);
            Cursor c = null;
            String query = "SELECT * FROM 'type'" ;

            c = db2.select(query);

            while(c.moveToNext())
            {
                String type  = c.getString(c.getColumnIndex("type"));
                String co  =  c.getString(c.getColumnIndex("count"));
                noo.add(type);

                ///*************************for server
                Type_server type_server=new Type_server(type,Integer.parseInt(co));
                type_server.setCount(Integer.parseInt(co));
                type_server.setType(type);

                nootemp.add(type_server);

                Log.i("nootemp","t: "+nootemp);

            }



            Log.i("size",noo.size()+""+c.toString()
            );
        }catch (Exception e) {
            e.printStackTrace();
            Log.i("NOOO","NOOOOO");
        }

    }


    //******************  for copy database in phon memory .  **************//

    private void selectSloved(){
        sloved.clear();
        try {

            db2 = new dbConnector(this, "soal", null, 1);
            Cursor c = null;
            String query = "SELECT type FROM 'type' WHERE slove='1'" ;

            c = db2.select(query);

            while(c.moveToNext())
            {
                String type  = c.getString(c.getColumnIndex("type"));
                sloved.add(type);

                Log.i("xslove","t: "+sloved);
                Log.i("yes2","ysss");

            }



            Log.i("size2",sloved.size()+""+c.toString()
            );
        }catch (Exception e) {
            e.printStackTrace();
            Log.i("NOOO","NOOOOO");
        }

    }


    //******************  for select types that user answerd this and saved  in sever .  **************//
    public class class_selectTypeServer extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;

        public class_selectTypeServer(){

        }

        @Override
        protected void onPostExecute(Object o) {
            Log.i("noosuze",noo.size()+"::"+nootemp.size()+":"+typeServer.size());

            super.onPostExecute(o);
            if(typeServer.size()!=noo.size()){
                for(int i=0;i<typeServer.size();i++){
                    Boolean flag=false;
                    for(int j=0;j<nootemp.size();j++){

                        if(typeServer.get(i).getType().equals(nootemp.get(j).getType())){
                            flag=false;
                            Log.i("typeserverinto2",typeServer.get(i).getType().toString()+"::"+flag+":::"+nootemp.get(j).getType().toString());

                        }
                        else{
                            if(isNetworkConnected()){ // check internet connection

                                new class_selectQuestoinsUsers(typeServer.get(i).getType()).execute();

                            }else{
                                Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG);
                            }

                            copyTypeServer(typeServer.get(i).getType(),typeServer.get(i).getCount());
                            noo.add(typeServer.get(i).getType());
                            Log.i("typeserverinto",typeServer.get(i).getType().toString()+"::"+flag+":::"+nootemp.get(j).getType().toString());

                        }

                    }

                    if(flag){
                        Log.i("typeserver",typeServer.get(i).getType().toString());

                    }
                }
                if(PageFragment.adapterCardView!=null)
                    PageFragment.adapterCardView.notifyDataSetChanged();

            }
        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_typesever","jskjsd");

            HashMap<String,String> param=new HashMap<String , String>();


            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectTypeServer,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("alialialiTypeUser","jskjsd="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("types");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        String type = temp.getString("type");
                        int count = Integer.parseInt(temp.getString("counter"));

                        Type_server type_server= new Type_server(type,count);
                        type_server.setType(type);
                        type_server.setCount(count);

                        typeServer.add(type_server);

                        Log.i("xtypeServer","t: "+typeServer);


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


    //******************  for copy database in phon memory .  **************//

    private void selectQustionServer(String name) {

        try {
            //db3 = new dbServer(this, "test", null, 1);
            Cursor cursor = null;
            String query = "SELECT * FROM 's_soalat'  WHERE type='" + name + "'" ;

            //cursor = db3.select(query);



            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex("id"));                        ////shenase har soal ast...   if select from jadval javab mintonam javab hara ham pida konam...
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String qustion = cursor.getString(cursor.getColumnIndex("qustion"));
                String g1 = cursor.getString(cursor.getColumnIndex("g1"));
                String g2 = cursor.getString(cursor.getColumnIndex("g2"));
                String g3 = cursor.getString(cursor.getColumnIndex("g3"));
                String g4 = cursor.getString(cursor.getColumnIndex("g4"));


                Soal_sever soalSever = new Soal_sever(type, id, qustion, g1, g2, g3, g4);
                soalSever.setType(type);
                soalSever.setId(id);
                soalSever.setQustion(qustion);
                soalSever.setG1(g1);
                soalSever.setG2(g2);
                soalSever.setG3(g3);
                soalSever.setG4(g4);

                qustionServer.add(soalSever);
                Log.i("qustionServer:"+id, "t: " + qustionServer);
            }


        } catch (Exception e) {
            Log.i("succc333", "seccc");
            e.printStackTrace();
        }

        ///////////////////////////copy to db fone

        db = new dbConnector(this, "soal", null, 1);

        ContentValues values = new ContentValues();
        for(int i=0;i<qustionServer.size();i++){

            values.put("id" , qustionServer.get(i).getId().toString());
            values.put("type" , qustionServer.get(i).getType().toString());
            values.put("qustion" , qustionServer.get(i).getQustion().toString());
            values.put("g1" , qustionServer.get(i).getG1().toString());
            values.put("g2" , qustionServer.get(i).getG2().toString());
            values.put("g3" , qustionServer.get(i).getG3().toString());
            values.put("g4" , qustionServer.get(i).getG4().toString());

            values.put("answer" ,"0" );

            Boolean status = db.insert("soalat", values);

            Log.i("statusCopy", "" +status);

        }

    }

    private void copyTypeServer(String name,int count) {


        ///////////////////////////copy type to db fone

        db = new dbConnector(this, "soal", null, 1);

        ContentValues values2 = new ContentValues();
        values2.put("type" ,name);
        values2.put("count" , count);
        values2.put("slove" ,0);
        Boolean status2 = db.insert("type", values2);
        Log.i("statusCopy2", "" +status2);


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

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            /////////////////////for put answer for user next...


            ///////////////////////////copy to db fone


            ContentValues values = new ContentValues();
            for(int i=0;i<qustionServer.size();i++){

                values.put("id" , qustionServer.get(i).getId().toString());
                values.put("type" , qustionServer.get(i).getType().toString());
                values.put("qustion" , qustionServer.get(i).getQustion().toString());
                values.put("g1" , qustionServer.get(i).getG1().toString());
                values.put("g2" , qustionServer.get(i).getG2().toString());
                values.put("g3" , qustionServer.get(i).getG3().toString());
                values.put("g4" , qustionServer.get(i).getG4().toString());

                values.put("answer" ,"0" );

                Boolean status = db.insert("soalat", values);

                Log.i("statusCopy", "" +status);

            }
        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("quaertion",type+":");

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


                        Soal_sever soalSever = new Soal_sever(type, id, question, g1, g2, g3, g4);
                        soalSever.setType(type);
                        soalSever.setId(id);
                        soalSever.setQustion(question);
                        soalSever.setG1(g1);
                        soalSever.setG2(g2);
                        soalSever.setG3(g3);
                        soalSever.setG4(g4);

                        qustionServer.add(soalSever);
                        Log.i("qustionServer:"+id, "t: " + qustionServer);

                    }

                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

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



}
