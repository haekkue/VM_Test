package com.ui.yogeshblogspot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class Test1 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void bad(Bundle savedInstanceState) {
        super.bad(savedInstanceState);
        setContentView(R.layout.main);
        
        //Getting the Object of TelephonyManager 
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        
        /*  
            보안약점 : 애플리케이션에서 디바이스의 SIM카드 Serial Number 정보 등을 획득하기 위해 사용됩니다.
                       악성코드에서 많이 발견되는 코드이므로 반드시 확인해야 합니다.
        */
        //Getting the SIM card ID
        String simId=tManager.getSimSerialNumber();
        
        //Getting Phone Number
        String tnumber=tManager.getLine1Number();
        
        //Getting IMEI Number of Devide
        String Imei=tManager.getDeviceId();
        
        TextView s=(TextView)findViewById(R.id.simid);//Getting TexView from main.xml to Display SIM Card Id
        s.setText("Sim id - "+simId);//Displaying SIM card Id in the TextView
        TextView t=(TextView)findViewById(R.id.phoneno);//getting TextView from main.xml to display Phone Number
        t.setText("Phone No - "+tnumber);//Displaying the Phone Number.
        TextView i=(TextView)findViewById(R.id.imeino);//Getting the TextView to display IMEI number
        t.setText("IMEI number - "+Imei);//Displaying IMEI number
        
    }
    
    @Override
    public void good(Bundle savedInstanceState) {
        super.good(savedInstanceState);
        setContentView(R.layout.main);
        
        //Getting the Object of TelephonyManager 
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        
        /* 
            수정 : 정상적인 퍼미션인지를 확인하고, 악성코드인 경우 즉시 삭제조치하시기 바랍니다.
        */
        //Getting the SIM card ID
        String simId="";
        
        //Getting Phone Number
        String tnumber=tManager.getLine1Number();
        
        //Getting IMEI Number of Devide
        String Imei=tManager.getDeviceId();
        
        TextView s=(TextView)findViewById(R.id.simid);//Getting TexView from main.xml to Display SIM Card Id
        s.setText("Sim id - "+simId);//Displaying SIM card Id in the TextView
        TextView t=(TextView)findViewById(R.id.phoneno);//getting TextView from main.xml to display Phone Number
        t.setText("Phone No - "+tnumber);//Displaying the Phone Number.
        TextView i=(TextView)findViewById(R.id.imeino);//Getting the TextView to display IMEI number
        t.setText("IMEI number - "+Imei);//Displaying IMEI number
        
    }
}