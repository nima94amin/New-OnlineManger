package com.ali.question.profile;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.question.R;
import com.ali.question.databaseAndSever.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profile extends Activity {
    LinearLayout lyMoshkhast;
    TextView tvUsername,tvMoblie,tvName,tvFamiliy,tvFathername,tvCode,tvEmail,tvOstan, tvCity,tvAddress;


    private final String urlselectInformationUesr = "http://aliexamination.ir/selectImformaion.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        lyMoshkhast = (LinearLayout) findViewById(R.id.ly_moshakhast);
        lyMoshkhast.setVisibility(View.VISIBLE);

        tvFathername = (TextView)findViewById(R.id.tv_father_mskst);
        tvUsername   = (TextView)findViewById(R.id.tv_username_mskst);
        tvMoblie     = (TextView)findViewById(R.id.tv_moblie__mskst);
        tvName       = (TextView)findViewById(R.id.tv_name_mskst);
        tvFamiliy    = (TextView)findViewById(R.id.tv_family_mskst);
        tvCode       = (TextView)findViewById(R.id.tv_code__mskst);
        tvOstan      = (TextView)findViewById(R.id.tv_ostan_mskst);
        tvCity       = (TextView)findViewById(R.id.tv_city_mskst);
        tvAddress    = (TextView)findViewById(R.id.tv_address_mskst);
        tvEmail      = (TextView)findViewById(R.id.tv_email_mskst);

        Bundle extra = getIntent().getExtras();
        Log.i("alladk",extra.getString("username_start_p"));


    }

    public class class_selectInformationUser extends AsyncTask {   ////select from javab dade ha...

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray =null;
        String username;
        String city,mobile,name,family,fathername,code,email,ostan,address;



        public class_selectInformationUser(String username){
            this.username=username;
            Log.i("give usernamesescore",username);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            Log.i("comcomcom",city);
            tvUsername.setText(username);
            tvMoblie.setText(mobile);
            tvName.setText(name);
            tvFamiliy.setText(family);
            tvFathername.setText(fathername);
            tvCode.setText(code);
            tvEmail.setText(email);
            tvOstan.setText(ostan);
            tvCity.setText(city);
            tvAddress.setText(address);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.i("select_usersifnfo","jskjsd");

            HashMap<String,String> param=new HashMap<String , String>();
            param.put("username",username);

            Log.i("select_scoerinfor","aliii="+username);
            JSONObject jsonObject =jsonParser.makeHttpRequest(urlselectInformationUesr,"POST",param);   //receive information form sever and put into jsonObject...

            try {

                int t=jsonObject.getInt("t");

                Log.i("alialialiselectfrscore","jskjsd="+t);
                if(t==1){

                    jsonArray= jsonObject.getJSONArray("information");    /// input json response["travel"] in php code ;  and give me length...

                    for(int i=0 ; i<jsonArray.length();i++){

                        JSONObject temp=jsonArray.getJSONObject(i);

                        name = temp.getString("name");
                        family = temp.getString("family");
                        fathername = temp.getString("fathername");
                        code = temp.getString("code");
                        email = temp.getString("email");
                        city = temp.getString("city");
                        ostan = temp.getString("ostan");
                        address = temp.getString("address");
                        mobile = temp.getString("mobile");


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
}


