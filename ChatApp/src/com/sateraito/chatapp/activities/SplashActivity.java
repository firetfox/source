package com.sateraito.chatapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sateraito.chatapp.R;
import com.sateraito.chatapp.ultilities.Preference;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_splash);

		new LoadingBuzzNews().execute();
	}

	public class LoadingBuzzNews extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (Preference.isDisplayLogin(SplashActivity.this))
				startActivity(new Intent(getApplicationContext(),
						LoginActivity.class));
			else
				startActivity(new Intent(getApplicationContext(),
						HomeActivity.class));
			finish();
		}

	}
}
