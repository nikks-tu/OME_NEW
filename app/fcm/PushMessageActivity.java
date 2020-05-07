package com.example.ordermadeeasy.fcm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ordermadeeasy.R;

/**
 * Created by kishore.i on 02/23/2019.
 */
public class PushMessageActivity extends AppCompatActivity {

	String subject, message;
	Button button,CLOSE;
	static String packageName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.push_alert);
		if (getIntent().getExtras() != null) {
//			subject = getIntent().getExtras().getString("subject");
			message = getIntent().getExtras().getString("message");
			message.trim();
		}
		

//		if (subject != null) {
//			setTitle("");
//		}

		try {
			packageName = getPackageManager().getPackageInfo(getPackageName(),
					0).packageName;
			Log.d("PackageName", packageName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getAllIds();

	}

	public void getAllIds() {

		TextView mssg = (TextView) findViewById(R.id.tv);

		if (message != null) {
			mssg.setText(message);
		}

		button = (Button) findViewById(R.id.btn_ok);
		CLOSE  = (Button) findViewById(R.id.CLOSE);
		button.setOnClickListener(v -> {


		});
		
		CLOSE.setOnClickListener(v -> finish());
	}
}
