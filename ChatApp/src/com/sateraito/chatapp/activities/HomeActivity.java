package com.sateraito.chatapp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sateraito.chatapp.R;

public class HomeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_sign_out:
	            signOut();
	            return true;
	        case R.id.action_chat_room:
	            openChatRoom();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void signOut() {
		
	}
	
	private void openChatRoom() {
		
	}

	
}
