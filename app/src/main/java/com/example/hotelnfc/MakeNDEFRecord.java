package com.example.hotelnfc;

import android.nfc.NdefRecord;

import java.nio.charset.Charset;
import java.util.Locale;

public class MakeNDEFRecord {

    public NdefRecord changeNDEFType(String payload, Locale locale, boolean encode){

        byte[] lanByte = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncode = encode ? Charset.forName("UTF-8") : Charset.forName("UTF-16");

        byte[] textToBype = payload.getBytes(utfEncode);

        int utfBit = encode ? 0: (1 << 7);
        char status = (char) (utfBit + lanByte.length + textToBype.length);
        byte[] data = new byte[1+ lanByte.length + textToBype.length];
        data[0] = (byte) status;
        System.arraycopy(lanByte, 0, data, 1, lanByte.length);
        System.arraycopy(textToBype, 0, data, 1+lanByte.length, lanByte.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }
}
