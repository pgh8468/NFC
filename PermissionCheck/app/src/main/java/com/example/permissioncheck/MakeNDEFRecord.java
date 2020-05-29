package com.example.permissioncheck;

import android.nfc.FormatException;
import android.nfc.NdefRecord;

import java.nio.charset.Charset;
import java.util.Locale;

public class MakeNDEFRecord {

    public NdefRecord changeNDEFType(String payload, Locale locale, boolean encode){

        byte[] lanByte = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncode = encode ? Charset.forName("UTF-8") : Charset.forName("UTF-16");

        byte[] textToByte = payload.getBytes(utfEncode);

        int utfBit = encode ? 0 : (1 <<7 );
        char status = (char) (utfBit + lanByte.length + textToByte.length);
        byte[] data = new byte[1 + lanByte.length + textToByte.length];
        data[0] = (byte) status;
        System.arraycopy(lanByte, 0, data, 1, lanByte.length);
        System.arraycopy(textToByte, 0, data, 1 + lanByte.length, lanByte.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }
}
