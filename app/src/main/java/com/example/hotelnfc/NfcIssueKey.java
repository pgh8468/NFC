package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class NfcIssueKey extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private final int MY_PERMISSION_NFC_CODE = 1000;
    private final int MY_PERMISSION_PHONE_NUMBER = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_issue_key);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "nfc 를 안켰음", Toast.LENGTH_LONG).show();
            startActivity(new Intent("android.settings.NFC_SETTINGS"));

        } else {
            String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.e("android id", android_id);

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Log.e("코드동작 테스트",android_id);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED){

                onRequestPermissionsResult(MY_PERMISSION_PHONE_NUMBER, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, new int[] {ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_NUMBERS)});
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }

            if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {

            }
            String phone_number = tm.getLine1Number();
            if( phone_number == null){
                Toast.makeText(this, "USIM이 존재하지 않는 거 같다.", Toast.LENGTH_LONG).show();
            }
            else{

                Log.e("핸드폰 번호", phone_number);
            }


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(MY_PERMISSION_NFC_CODE == requestCode && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            //퍼미션이 이미 되어있음
            if(ContextCompat.checkSelfPermission(NfcIssueKey.this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED){

            }
            // 그렇지 않은 경우 재요청
            else{
                ActivityCompat.requestPermissions(NfcIssueKey.this, new String[]{Manifest.permission.NFC}, MY_PERMISSION_NFC_CODE);
            }

        }
        else { //MY_PERMISSION_PHONE_NUMBER == requestCode

            if(MY_PERMISSION_PHONE_NUMBER == requestCode && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //퍼미션이 이미 되어있음
                if(ContextCompat.checkSelfPermission(NfcIssueKey.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED){

                }
                // 그렇지 않은 경우 재요청
                else{
                    ActivityCompat.requestPermissions(NfcIssueKey.this, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, MY_PERMISSION_PHONE_NUMBER);
                }
            }

        }
    }

}
