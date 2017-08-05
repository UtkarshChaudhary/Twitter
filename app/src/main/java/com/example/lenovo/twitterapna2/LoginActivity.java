package com.example.lenovo.twitterapna2;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {
TwitterLoginButton mLoginButton;
String token,tokenSecret,userName;
 long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Twitter.initialize(this);
        TwitterUtils.refresh();
        if(TwitterUtils.twitterSession!=null){
            //log statement
          //  Log.i("LoginActivity","twitterSession!=null");
         startNewActivity();
        }

        SharedPreferences sp=getSharedPreferences(SharedPreferenceConstant.SP_NAME,MODE_PRIVATE);
        token=sp.getString(SharedPreferenceConstant.TOKEN,null);
        if(token!=null){
            //log statement
          //  Log.i("LoginActivity","token!=null");
         tokenSecret=sp.getString(SharedPreferenceConstant.TOKEN_SECRET,null);
          userName=sp.getString(SharedPreferenceConstant.USER_NAME,null);
          userId=sp.getLong(SharedPreferenceConstant.USER_ID,-1);
            setActiveSession();
            startNewActivity();
        }
        //log statement
        //Log.i("LoginActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton=(TwitterLoginButton) findViewById(R.id.login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterUtils.twitterSession=result.data;
                TwitterUtils.twitterAuthToken=TwitterUtils.twitterSession.getAuthToken();
                TwitterUtils.refreshTwitterAuthConfig();

                SharedPreferences sp=getSharedPreferences(SharedPreferenceConstant.SP_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putLong(SharedPreferenceConstant.USER_ID,TwitterUtils.twitterSession.getUserId());
                editor.putString(SharedPreferenceConstant.USER_NAME,TwitterUtils.twitterSession.getUserName());
                editor.putString(SharedPreferenceConstant.TOKEN,TwitterUtils.twitterAuthToken.token);
                editor.putString(SharedPreferenceConstant.TOKEN_SECRET,TwitterUtils.twitterAuthToken.secret);
                editor.commit();

                startNewActivity();
                finish();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
      }

      private void setActiveSession(){
          TwitterUtils.twitterAuthToken= new TwitterAuthToken(token,tokenSecret);
          TwitterUtils.twitterSession=new TwitterSession(TwitterUtils.twitterAuthToken,userId,userName);
          TwitterUtils.refreshTwitterAuthConfig();
      }

    private void startNewActivity(){
        Intent i=new Intent(LoginActivity.this,MainActivity.class);
        // i.putExtra("ID",userId)
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginButton.onActivityResult(requestCode,resultCode,data);
    }
}
