package com.ali.question.menu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.databaseAndSever.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Register extends Activity {


    //*************    ImageView   *************//
    ImageView imgUser;


    //*************    database   *************//

   // dbServer db2;

    //*************    button   *************//

    Button saveRegister;
    Button btnGallery , btnCamera;


    //*************    listView   *************//

    //*************    editText   *************//

    EditText et_username,et_name,et_family,et_fathername,et_code,et_ostan,et_city,et_address,et_moblie,et_email;

    //*************   variables    *************//

    Boolean flagRegister;
    String defImage ="http://aliexamination.ir/appUser/imagesUser/";


    //*************   context    *************//

    //*************    sharedPreferences   *************//


    //*************    url   *************//



    private final String urlInsertusers ="http://aliexamination.ir/appUser/insertUserinformation.php";
    private final String urlUpdateUser ="http://aliexamination.ir/appUser/updateUserRegister.php";
    String urlInsertImageUser ="http://aliexamination.ir/appUser/InsertImage.php";

    //******************    image form  gallry and camera **************//

    private int my_requestCode_gallery = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et_address = (EditText)findViewById(R.id.et_reg_address);
        et_username = (EditText)findViewById(R.id.et_reg_uername);
        et_city  = (EditText)findViewById(R.id.et_reg_city);
        et_code   =(EditText)findViewById(R.id.et_reg_code);
        et_ostan  =(EditText)findViewById(R.id.et_reg_ostan);
        et_moblie  =(EditText)findViewById(R.id.et_reg_mobile);
        et_name   =(EditText)findViewById(R.id.et_reg_name);
        et_family  =(EditText)findViewById(R.id.et_reg_name);
        et_fathername =(EditText)findViewById(R.id.et_reg_fathername);
        et_email      =(EditText)findViewById(R.id.et_reg_email);

        imgUser =(ImageView)findViewById(R.id.img_user);


        Bundle extra = getIntent().getExtras();   ////raccive userneme and moblie form mainActivity...main--->start--->register...

        if (extra != null) {
            String username = extra.getString("username_start");
            String mob =extra.getString("mobile_start");

            Log.i("bundel112",username+"::"+mob);
            et_username.setText(username);
            et_moblie.setText(mob);
            et_username.setEnabled(false);
            et_moblie.setEnabled(false);
        }






        saveRegister =(Button)findViewById(R.id.btn_save_reg);
        btnGallery   =(Button)findViewById(R.id.btnGallery);
        btnCamera    =(Button)findViewById(R.id.btnCamera);

        saveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkConnected()){


                    ///add image befor add any username:
                    Bitmap image = ( (BitmapDrawable) imgUser.getDrawable() ).getBitmap();


                    new class_insertUser(et_username.getText().toString(),et_name.getText().toString(),
                            et_family.getText().toString(), et_fathername.getText().toString(),
                            et_code.getText().toString(),et_email.getText().toString(),
                            et_ostan.getText().toString(), et_city.getText().toString(),
                            et_address.getText().toString(),et_moblie.getText().toString()).execute();
                    new class_insertImage(image,et_username.getText().toString(),et_moblie.getText().toString()).execute();

                }else{
                    Log.i("Enternet","nok2");
                    Toast.makeText(getApplicationContext(),"اینترنت متصل نیست",Toast.LENGTH_LONG).show();
                }




            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCamera();

            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();

            }
        });



    }

    //******************    select image form camera **************//
    private int my_requestCode = 1;
    private Bitmap my_bitmap;
    private String my_final_image;

    private void selectCamera (){
        Log.i("camera","comhere");
       //R.string.no_camera_error
        if( getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_ANY ) )
        {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(i, my_requestCode);
        }
        else
        {
            Toast.makeText( getApplicationContext() ,
                    "no camra" ,
                    Toast.LENGTH_LONG ).show();
        }

    }

    //******************    select image form gallery  **************//

    private  void selectGallery(){

        Intent i = new Intent( Intent.ACTION_PICK ,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );

        startActivityForResult( i , my_requestCode_gallery );
    }

    //******************    image form  gallry and camera **************//


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( requestCode == my_requestCode && resultCode == RESULT_OK )
        {
            Bundle e = data.getExtras();

            my_bitmap = (Bitmap) e.get( "data" );

            show_captured_image();
        }
        else if( requestCode == my_requestCode_gallery && resultCode == RESULT_OK )
        {
            Uri image = data.getData();

            show_internal_image( image );
        }
        else
        {
            Toast.makeText( getApplicationContext() ,
                    getString( R.string.get_image_error ) ,
                    Toast.LENGTH_LONG ).show();
        }
    }

    public void show_captured_image()
    {
        try
        {
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);

            LayoutInflater inflater = (LayoutInflater)
                    this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.full_screen_image,
                    (ViewGroup) findViewById(R.id.full_img_layout_root));

            ImageView bigImage = (ImageView) layout.findViewById(R.id.full_img_img);

            bigImage.setImageBitmap(my_bitmap);

            TextView imgTitle = (TextView) layout.findViewById(R.id.full_img_title);

            imgTitle.setText(R.string.captured_img_title);

            imageLoader.setView(layout);

            imageLoader.setCancelable(false);

            imageLoader.setPositiveButton(R.string.captured_img_btn_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            imgUser.setImageBitmap(my_bitmap);

                            //selected_img.setImageBitmap( my_bitmap );

                            //selected_img_txt.setText( R.string.captured_img_is_true );

                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.setNegativeButton(R.string.captured_img_btn_again,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            selectCamera();
                        }
                    }
            );

            imageLoader.setNeutralButton(R.string.btn_Back_to_home,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.create();

            imageLoader.show();
        }
        catch ( Exception e )
        {
            /*
             * Log.i( "MatiMessage" , "error 1 -> " + e.toString() );
             */
        }
    }

    public void show_internal_image( final Uri imageUri )
    {
        try
        {
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);

            LayoutInflater inflater = (LayoutInflater)
                    this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.full_screen_image,
                    (ViewGroup) findViewById(R.id.full_img_layout_root));

            ImageView bigImage = (ImageView) layout.findViewById(R.id.full_img_img);

            bigImage.setImageURI(imageUri);

            TextView imgTitle = (TextView) layout.findViewById(R.id.full_img_title);

            imgTitle.setText(R.string.captured_img_title);

            imageLoader.setView(layout);

            imageLoader.setCancelable(false);

            imageLoader.setPositiveButton(R.string.captured_img_btn_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            imgUser.setImageURI(imageUri);

                           // selected_img_txt.setText( R.string.captured_img_is_true );

                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.setNegativeButton(R.string.captured_img_btn_again,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            selectGallery();
                        }
                    }
            );

            imageLoader.setNeutralButton(R.string.btn_Back_to_home,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.create();

            imageLoader.show();
        }
        catch ( Exception e )
        {
            /*
             * Log.i( "MatiMessage" , "error 1 -> " + e.toString() );
             */
        }
    }




    //******************    image form  gallry and camera **************//


    public class class_insertUser extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username,name,family,fathername,code,email,ostan,city,address,mobile;

        public class_insertUser(String username,String name,String family,String fathername,String code,String email,String ostan,String city,String address,String mobile){

            this.username=username;
            this.name=name;
            this.family=family;
            this.fathername=fathername;
            this.code=code;
            this.ostan=ostan;
            this.email=email;
            this.city=city;
            this.address=address;
            this.mobile=mobile;

        }

        @Override
        protected void onPostExecute(Object o) {


            super.onPostExecute(o);
            new class_UpdateUserregister(username).execute();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_type_name","jskjsd");

            HashMap<String,String> param=new HashMap<String , String>();

            param.put("username",username);
            param.put("name",name);
            param.put("family",family);
            param.put("fathername",fathername);
            param.put("email",email);
            param.put("code",code);
            param.put("ostan",ostan);
            param.put("city",city);
            param.put("mobile",mobile);
            param.put("address",address);
            param.put("imageAdress",defImage+username+"_"+mobile+".jpg");
            JSONObject jsonObject =jsonParser.makeHttpRequest(urlInsertusers,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("alialialiTypeUser1","jskjsd="+t);
                if(t==1){

                    flagRegister=true;
                    Log.i("status3_user",flagRegister+"");


                }else{
                    flagRegister=false;
                    Log.i("status3_user",flagRegister+"");

                    // Toast.makeText(MainActivity.context,"no imfomainion", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }


    }

    public class class_insertImage extends AsyncTask {

        JSONParser jsonParser=new JSONParser();

        Bitmap image;


        String username,mobile;


        public class_insertImage(Bitmap image,String username,String mobile){
            this.image=image;
            this.username=username;
            this.mobile=mobile;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(getApplicationContext(),"عکس آپلود شد",Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Log.i("select_socore_registers","jskjsd"+username+mobile);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            image.compress( Bitmap.CompressFormat.JPEG , 10 , outStream );

            String encodedImage = Base64.encodeToString(
                    outStream.toByteArray() , Base64.DEFAULT
            );

            HashMap<String,String> param=new HashMap<String , String>();

            param.put("image",encodedImage);
            param.put("username",username);
            param.put("mobile",mobile);
            //Log.i("select_socoreters2",encodedImage.toString());

            /*JSONObject jsonObject =jsonParser.makeHttpRequest(urlInsertImageUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                String t=jsonObject.getString("error");

                Log.i("alialialiinsetinmage","="+t);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"عکس آپلود نشد",Toast.LENGTH_LONG).show();
            }*/


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
    //******************    for update register user before register **************//

    public class class_UpdateUserregister extends AsyncTask {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username;

        public class_UpdateUserregister(String username){

            this.username=username;

        }

        @Override
        protected void onPostExecute(Object o) {


            super.onPostExecute(o);
            Toast.makeText(getApplicationContext(),"ثبت نام انجام شد",Toast.LENGTH_LONG).show();


        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_typeinsetre","jskjsd");

            HashMap<String,String> param=new HashMap<String , String>();

            param.put("username",username);

            JSONObject jsonObject =jsonParser.makeHttpRequest(urlUpdateUser,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                    Log.i("alialialiTypeUserre","jskjsd="+t);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }


    }



}
