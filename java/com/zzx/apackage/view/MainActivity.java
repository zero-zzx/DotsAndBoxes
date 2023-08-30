package com.zzx.apackage.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zzx.apackage.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Intent doubleee=new Intent(this,DoubleActivity.class);
		final Intent helpp=new Intent(this,HelpActivity.class);
		final Intent singlee=new Intent(this,SingleActivity.class);
		final Intent sound=new Intent(this,SoundSettingActivity.class);
		Button single= (Button) findViewById(R.id.imageButton2);
		Button doublee=(Button)findViewById(R.id.imageButton3);
		Button help=(Button)findViewById(R.id.imageButton6);
		Button soundd=(Button)findViewById(R.id.imageButton1);
		
		soundd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(sound);
			}
		});
		doublee.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(doubleee);
			}
		});
		help.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(helpp);
			}
		});
		single.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(singlee);
			}
		});
	}

	static long firstTime=0;
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			long secondTime=System.currentTimeMillis();
			if(secondTime-firstTime>2000){
				Toast.makeText(MainActivity.this,"zzx提醒您，再按一次退出程序",Toast.LENGTH_SHORT).show();
				firstTime=secondTime;
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

}
