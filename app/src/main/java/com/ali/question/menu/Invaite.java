package com.ali.question.menu;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ali.question.R;
import com.ali.question.main.MainActivity;
import com.ali.question.sms.SmsDeliveredReceiver;
import com.ali.question.sms.SmsSentReceiver;

import java.util.ArrayList;

public class Invaite extends Activity {

    //*************    ArrayList   *************//


    //*************    database   *************//


    //*************    button   *************//

    Button sendSms ,tempSms;
    ImageButton imgbtnContact;



    //*************    listView   *************//

    //*************    editText   *************//

    EditText etnubmer , etbody;

    //*************   variables    *************//

    private static final int PICK_CONTACT = 1;

    private static final int PICK_CONTACT_SUBACTIVITY = 2;

    private String contactID;     // contacts unique ID.

    //*************   context    *************//

    //*************    sharedPreferences   *************//


    //*************    url   *************//

    //******************    **************//






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invaite);

        sendSms = (Button)findViewById(R.id.btn_send_sms);
        tempSms = (Button)findViewById(R.id.btn_temp_sms);

        etnubmer =(EditText) findViewById(R.id.et_number_sms);
        etbody   = (EditText)findViewById(R.id.et_body_sms);

        imgbtnContact =(ImageButton)findViewById(R.id.imgbtn_contact);
        imgbtnContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);


                ///
                Uri uri = Uri.parse("content://contacts/people");
                // Here in this normally we pass number e.g. Uri.encode("987") but i want to pass name as filter is it possible?
                // I have also tried
                //uri = Uri.withAppendedPath(android.provider.ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode("pra"));
                //uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode("pra"));
                //uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode("UTF-8"));

              /* Intent intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, 1);*/
            }
        });
        tempSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etbody.setText("لطفا از بازار برنامه را نصب کنید.");
            }
        });
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber= etnubmer.getText().toString();
                if(!phoneNumber.equals("")){
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", phoneNumber, null));
                    sendIntent.putExtra("sms_body",etbody.getText().toString() );
                    startActivity(sendIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"شماره تلفن را وارد کنید ",Toast.LENGTH_SHORT).show();

                }

            }
        });
        etnubmer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    etnubmer.setText("");
                   /* Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);*/
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE },
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        int type = c.getInt(1);
                        // showSelectedNumber(type, number);
                        Log.i("aliph", "Contact ID: " + number);
                        etnubmer.setText(number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }


    //////////////////////////////////

    private void sendSMS() {

        String phoneNumber = "+989196113387";
        String message = "Welcome to sms";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void sendSMS(String phoneNumber, String message) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(MainActivity.context, 0,
                new Intent(MainActivity.context, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(MainActivity.context, 0,
                new Intent(MainActivity.context, SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }
}


