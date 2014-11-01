package com.sateraito.chatapp.activities;

import com.sateraito.chatapp.R;
import com.sateraito.chatapp.ultilities.Constant;
import com.sateraito.chatapp.ultilities.Preference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements ConnectionCallbacks,
		OnConnectionFailedListener {
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/*
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	/*
	 * Track whether the sign-in button has been clicked so that we know to
	 * resolve all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;

	/*
	 * Store the connection result from onConnectionFailed callbacks so that we
	 * can resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_login);
		// Preference.savePreferences(getApplicationContext(),
		// Constant.IS_DISPLAY_LOGIN, false);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View view) {
						if (view.getId() == R.id.sign_in_button
								&& !mGoogleApiClient.isConnecting()) {
							mSignInClicked = true;
							resolveSignInError();
						}
					}
				});

		findViewById(R.id.sign_out_button).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View view) {
						if (view.getId() == R.id.sign_out_button) {
							if (mGoogleApiClient.isConnected()) {
								// Prior to disconnecting, run
								// clearDefaultAccount().
								Plus.AccountApi
										.clearDefaultAccount(mGoogleApiClient);
								Plus.AccountApi.revokeAccessAndDisconnect(
										mGoogleApiClient).setResultCallback(
										new ResultCallback<Status>() {
											@Override
											public void onResult(Status result) {
												// mGoogleApiClient is now
												// disconnected and access has
												// been revoked.
												// Trigger app logic to comply
												// with the developer policies
												Toast.makeText(getApplicationContext(), "user disconnected "+result.getStatusMessage(), Toast.LENGTH_SHORT).show();
											}
										});
								mGoogleApiClient.disconnect();
								Toast.makeText(getApplicationContext(), "User is disconnected!", Toast.LENGTH_LONG).show();
								mGoogleApiClient.connect();

							}
						}
					}
				});
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		// if (!mIntentInProgress && result.hasResolution()) {
		// try {
		// mIntentInProgress = true;
		// startIntentSenderForResult(result.getResolution()
		// .getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
		// } catch (SendIntentException e) {
		// // The intent was canceled before it was sent. Return to the
		// // default
		// // state and attempt to connect to get an updated
		// // ConnectionResult.
		// mIntentInProgress = false;
		// mGoogleApiClient.connect();
		// }
		// }
		if (!mIntentInProgress) {
			// Store the ConnectionResult so that we can use it later when the
			// user clicks
			// 'sign-in'.
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	// public void onConnected(Bundle connectionHint) {
	// // We've resolved any connection errors. mGoogleApiClient can be used to
	// // access Google APIs on behalf of the user.
	// }

	@Override
	public void onConnected(Bundle connectionHint) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	}
	
	public void onDisconnected() {
		Toast.makeText(this, "User is disconnected!", Toast.LENGTH_LONG).show();
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		// if (requestCode == RC_SIGN_IN) {
		// mIntentInProgress = false;
		//
		// if (!mGoogleApiClient.isConnecting()) {
		// mGoogleApiClient.connect();
		// }
		// }
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(mConnectionResult.getResolution()
						.getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

}
