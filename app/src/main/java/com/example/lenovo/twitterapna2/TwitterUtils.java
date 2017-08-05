package com.example.lenovo.twitterapna2;

import android.util.Log;
import android.util.Pair;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.network.OAuth1aInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 02-08-2017.
 */

public class TwitterUtils {

    public static TwitterAuthConfig twitterAuthConfig;
    public static TwitterSession twitterSession;
    public static TwitterAuthToken twitterAuthToken;

//    static {
//        twitterAuthConfig = TwitterCore.getInstance().getAuthConfig();
//        twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
//        if (twitterSession != null) {
//            twitterAuthToken = twitterSession.getAuthToken();
//        }
//    }
    public static void refresh() {
        twitterAuthConfig = TwitterCore.getInstance().getAuthConfig();
        twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(twitterSession!=null) {
            twitterAuthToken = twitterSession.getAuthToken();
            Log.i("TwitterUtils",twitterAuthToken.toString()+"");
            Log.i("TwitterUtils",twitterSession.getAuthToken().secret.toString()+"");
        }

    }
    public static void refreshTwitterAuthConfig(){
        twitterAuthConfig=TwitterCore.getInstance().getAuthConfig();
    }
    // header using oAuthSigning
    public static String getHeader(String method, String url, Pair... pairs){

        Map<String , String > params = new HashMap<>();
        for (Pair pair : pairs) {
            params.put(pair.first.toString(), pair.second.toString());
        }

        OAuthSigning oAuthSigning = new OAuthSigning(twitterAuthConfig, twitterAuthToken);
        return oAuthSigning.getAuthorizationHeader(method, url, params);
    }

    public static String getUserScreenName(){
        if(twitterSession!=null){
            return twitterSession.getUserName();
        }
        return null;
    }
    // Twitter interceptor (OAuth1aInterceptor Interceptor class made by Twitter)

    public static OAuth1aInterceptor getInterceptor(){
        if(twitterSession!=null){
            return new OAuth1aInterceptor(twitterSession ,twitterAuthConfig);
        }
        return null;
    }

}
