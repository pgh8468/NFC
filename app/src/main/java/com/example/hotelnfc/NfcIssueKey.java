package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NfcIssueKey extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private final int MY_PERMISSION_NFC_CODE = 1000;
    private final int MY_PERMISSION_PHONE_NUMBER = 1001;
    private String phone_number;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_issue_key);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        int phone_state = readPhonePermission();

        if( phone_state != 1){
            Toast.makeText(this, "폰 상태 읽기 퍼미션 필요", Toast.LENGTH_LONG).show();
        }

        else{

            String check = USIMInfoCheck();

            Log.e("check check", check);

            if(check.equals("error")){
//            Snackbar.make(getWindow().getDecorView().getRootView(), "스마트키 발급 버튼을 다시 눌러주세요.", 3000).show();
                Toast.makeText(this, "USIM 이 존재해야 이용할 수 있는 서비스 입니다.", Toast.LENGTH_LONG).show();
//            finish();
            }
            else if(check.equals(null)){
                Snackbar.make(getWindow().getDecorView().getRootView(), "다시 시도하세요", 3000).show();
            }

            else{
                if (nfcAdapter == null) {
                    Toast.makeText(this, "NFC 를 지원하지 않는 모델입니다.", Toast.LENGTH_LONG).show();

                } else {
                    //여기부턴 nfc는 다들 있는 핸드폰이라는 것이여

                    int nfc_permission_check = NFCPermissionCheck();

                    if(nfc_permission_check != 0){

                        tv1.setText(""+nfc_permission_check+"");

                    }



                    //허가 확인 하고 startActivity(new Intent("android.settings.NFC_SETTINGS"));

//            Snackbar.make(getWindow().getDecorView().getRootView(), "여기까진 잘 넘어옴.", 2000).show();

                }
            }

        }


    }

    private int readPhonePermission(){
        int result = 0;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_NUMBERS);

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_NUMBERS}, MY_PERMISSION_PHONE_NUMBER);

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED){

                result = 1;
            }

        }

        else{
            result = 1;

        }

        return result;
    }

    private int NFCPermissionCheck(){
        int result = 0;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.NFC);

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.NFC}, MY_PERMISSION_NFC_CODE);

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.NFC) == PackageManager.PERMISSION_GRANTED){
                result = 1;
            }
        }
        else{
            result = 1;
        }

        return result;
    }

    private String USIMInfoCheck() {
        String result_phone_num = null;

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (tm == null) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "다시 시도하세요", 3000).show();
        } else {

            try {
                int usimState = tm.getSimState();
                Log.e("check usimstate", ""+usimState);

                if (usimState == TelephonyManager.SIM_STATE_UNKNOWN) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "USIM이 존재하지 않습니다.", 3000).show();
                    return new String("error");
                } else if (usimState == TelephonyManager.SIM_STATE_ABSENT) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "USIM이 영구 중지 상태입니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                    return new String("error");
                } else if (usimState == TelephonyManager.SIM_STATE_PERM_DISABLED) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "USIM이 존재하지만 오류가 존재합니다.", 3000).show();
                    return new String("error");
                } else if (usimState == TelephonyManager.SIM_STATE_CARD_IO_ERROR) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "통신사 제한이 걸려있습니다. 통신사에 문의하세요", 3000).show();
                    return new String("error");
                } else if (usimState == TelephonyManager.SIM_STATE_CARD_RESTRICTED) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "USIM 이 존재하지 않거나 오류가 존재합니다", 3000).show();
                    return new String("error");
                } else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Snackbar.make(getWindow().getDecorView().getRootView(), "어플을 사용하기 위한 전화권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //권한 설정 메소드 붙이자.
                            }
                        }).show();
//                        findViewById()
                    }
                    result_phone_num = tm.getLine1Number();
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return result_phone_num;
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
