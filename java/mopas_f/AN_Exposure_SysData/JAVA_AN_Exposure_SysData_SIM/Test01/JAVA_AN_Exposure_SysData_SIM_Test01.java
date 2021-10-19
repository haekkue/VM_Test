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
            ���Ⱦ��� : ���ø����̼ǿ��� ����̽��� SIMī�� Serial Number ���� ���� ȹ���ϱ� ���� ���˴ϴ�.
                       �Ǽ��ڵ忡�� ���� �߰ߵǴ� �ڵ��̹Ƿ� �ݵ�� Ȯ���ؾ� �մϴ�.
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
            ���� : �������� �۹̼������� Ȯ���ϰ�, �Ǽ��ڵ��� ��� ��� ������ġ�Ͻñ� �ٶ��ϴ�.
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