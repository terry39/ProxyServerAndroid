package com.android.sms.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.proxy.client.GlobalProxyUtil;
import com.android.proxy.client.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	private TextView mTvShow;
	private StringBuilder oldMsg;
	private EditText mEdtPort;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.connect).setOnClickListener(this);
		findViewById(R.id.disconnect).setOnClickListener(this);
		findViewById(R.id.appmanager).setOnClickListener(this);
		mTvShow = (TextView) findViewById(R.id.tv_socket_message);
		mEdtPort = (EditText) findViewById(R.id.port);
		oldMsg = new StringBuilder();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		EventBus.getDefault().register(this);
		mTvShow.setMovementMethod(ScrollingMovementMethod.getInstance());
		GlobalProxyUtil.getInstance(this).init();
		GlobalProxyUtil.getInstance(this).addProxyPackage(this, "com.android.vending");
		GlobalProxyUtil.getInstance(this).addProxyPackage(this,"com.google.android.gm");
	}


	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()){
				case R.id.connect:
					Intent it  = new Intent(this,GetRemotePortService.class);
					startService(it);
					break;
				case R.id.disconnect:
					GlobalProxyUtil.getInstance(this).serviceStop(this);
					break;
				case R.id.appmanager:
					Intent intent = new Intent(this,AppManager.class);
					startActivity(intent);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Subscribe
	public void onEvent(final MessageEvent event) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (event != null) {

					if (oldMsg.length() == 0) {
						oldMsg.append(event.getSocketMessage());
					} else {
						oldMsg.append("\r\n");
						oldMsg.append(event.getSocketMessage());
					}

					mTvShow.setText(oldMsg.toString());
				}
			}
		});

	}


}
