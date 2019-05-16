package com.example.app.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.app.R;

public class TcpClient extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_socket);
        runTcpClient();
//        finish();
    }
    
    private static final int TCP_SERVER_PORT = 9999;//should be same to the server port
	private void runTcpClient() {
    	try {
			Socket s = new Socket("http://192.168.1.108:8000", TCP_SERVER_PORT);//注意host改成你服务器的hostname或IP地址
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			//send output msg
			String outMsg = "TCP connecting to " + TCP_SERVER_PORT + System.getProperty("line.separator"); 
			out.write(outMsg);//发送数据
			out.flush();
			Log.i("TcpClient", "sent: " + outMsg);
			//accept server response
			String inMsg = in.readLine() + System.getProperty("line.separator");//得到服务器返回的数据
			Log.i("TcpClient", "received: " + inMsg);
			//close connection
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
	//replace runTcpClient() at onCreate with this method if you want to run tcp client as a service
	private void runTcpClientAsService() {
//		Intent lIntent = new Intent(this.getApplicationContext(), TcpClientService.class);
//        this.startService(lIntent);
	}
}