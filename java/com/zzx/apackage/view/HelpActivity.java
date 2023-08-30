package com.zzx.apackage.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.zzx.apackage.R;

public class HelpActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		Button returnBtn=(Button) findViewById(R.id.help_return);
		returnBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		Button shareBtn=(Button) findViewById(R.id.help_share);
		shareBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				StringBuilder sb=new StringBuilder();
				String shareText = getResources().getString(R.string.share_desc);
				sb.append(shareText);
				String url = getResources().getString(R.string.my_source_url);
				sb.append(url);

				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
				sendIntent.setType("text/main");
				startActivity(sendIntent);
			}
		});
	}

	

}
