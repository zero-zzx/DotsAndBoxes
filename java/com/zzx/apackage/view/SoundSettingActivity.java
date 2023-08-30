package com.zzx.apackage.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.zzx.apackage.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SoundSettingActivity extends Activity {
	static boolean music;
	static boolean touch;
	static ToggleButton musicSwitch;
	static ToggleButton touchSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		Button returnBtn = (Button)findViewById(R.id.sound_return);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		musicSwitch = (ToggleButton) findViewById(R.id.toggleButton1);
		touchSwitch = (ToggleButton) findViewById(R.id.toggleButton2);
		load();
		musicSwitch.setChecked(music);
		touchSwitch.setChecked(touch);
		musicSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
  
            @Override  
            public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {
                if (isChecked) {  
                    music=true; 
                    save();
                } else {  
                    music=false; 
                    save();
                }  
            }  
        });  
		touchSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
            @Override  
            public void onCheckedChanged(CompoundButton buttonView,  
                    boolean isChecked) {
                if (isChecked) {  
                    touch=true; 
                    save();
                } else {  
                    touch=false; 
                    save();
                }  
            }  
        });  
	}
	
		

	private void save() {
		try {
			FileOutputStream fos = openFileOutput("sound",0);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeBoolean(music);
			dos.writeBoolean(touch);
			dos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void load() {
		try {
			FileInputStream fis =openFileInput("sound");
			DataInputStream dis=new DataInputStream(fis);
			music=dis.readBoolean();
			touch=dis.readBoolean();
			dis.close();
		} catch (Exception e) {
			music=true;
			touch=true;
		}
	}

}
