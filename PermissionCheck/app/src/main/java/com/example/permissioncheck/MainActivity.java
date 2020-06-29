package com.example.permissioncheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private NfcAdapter nfcAdapter;
    private NdefMessage ndefMessage;
    IntentFilter[] writeTagFilter;
    private PendingIntent pendingIntent; // NFC로 전송받은 데이터를 Intent 를 이용하여 다른 액티비티로 넘겨주는 역할
    private final int MY_PERMISSION_NFC_CODE = 1000;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter =NfcAdapter.getDefaultAdapter(this);

        tv1 = (TextView) findViewById(R.id.showNFC);

        if(nfcAdapter == null){
            Toast.makeText(this, "nfc를 사용할 수 없는 기기입니다.", Toast.LENGTH_LONG).show();

        }

        else{

            //SINGLE_TOP FLAG 는 현재 Activity에서 Intent를 받게 할 수 있음
//            Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this,0,new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);

            IntentFilter tagDect = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            writeTagFilter = new IntentFilter[] {tagDect};


//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED){
//
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.NFC}, MY_PERMISSION_NFC_CODE);
//
//                Log.e("코드야 잘 되고 있니?", new String("hello"));
//
//                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.NFC)){
//
//                    Snackbar.make(getWindow().getDecorView().getRootView(), "현재 기능을 사용하기 위해서는 NFC의 권한 설정이 필요합니다.", Snackbar.LENGTH_LONG)
//                            .setAction("확인", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.NFC} ,MY_PERMISSION_NFC_CODE);
//                                }
//                            }).show();
//                }
//
//            }

        }

    }

    //Activity 가 화면에 보이고 있을 때에만 NFC 태그 인식
    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null){
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilter, null);

            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){


            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag nfctag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        ndefMessage = new NdefMessage(new NdefRecord[]{ new MakeNDEFRecord().changeNDEFType("test", Locale.ENGLISH, true)});
        if ( write_tag(ndefMessage, nfctag)){
            Log.e("success", "congratulations");
        }
        else{
            Log.e("fail", "OMG");
        }
        if(nfctag != null){
            byte[] tagInfo = nfctag.getId();
            Log.e("tagInfo : ",tagInfo.toString());
            tv1.setText("tagID:" + change_to_string(tagInfo));
        }

    }

    public final String decode_datas = "0123456789ABCDEF";

    public String change_to_string(byte[] data){
        StringBuilder sb = new StringBuilder();
        for(int i =0 ; i < data.length; ++i){
            sb.append(decode_datas.charAt( (data[i] >> 4) & 0x0F))
                    .append(decode_datas.charAt(data[i] & 0x0F));
        }
        return sb.toString();
    }

    public boolean write_tag(NdefMessage ndefMessage, Tag tag){

        try {
            Ndef ndef = Ndef.get(tag);
            if(ndef != null){
                ndef.connect();
                ndef.writeNdefMessage(ndefMessage);
                return true;
            }
            else{
                NdefFormatable ndefFormat = NdefFormatable.get(tag);
                if(ndefFormat != null){
                    ndefFormat.connect();
                    ndefFormat.format(ndefMessage);
                    ndefFormat.close();
                    return true;
                }
                else{
                    Log.e("여","기까지");
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

        return false;
    }


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(MY_PERMISSION_NFC_CODE == requestCode && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//            return;
//        }
//
//        else{
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.NFC}, MY_PERMISSION_NFC_CODE);
//        }
//    }
}
