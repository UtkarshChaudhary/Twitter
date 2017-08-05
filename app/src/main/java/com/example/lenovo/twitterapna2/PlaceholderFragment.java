package com.example.lenovo.twitterapna2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BasicTimelineFilter;
import com.twitter.sdk.android.tweetui.FilterValues;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineFilter;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 02-08-2017.
 */

public class PlaceholderFragment extends Fragment{

private static final String ARG_SECTION_NUMBER="section_number";
  ListView listView;

    public PlaceholderFragment(){

    }

    public static PlaceholderFragment newInstance(int sectionNumber){
        PlaceholderFragment fragment=new PlaceholderFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

     Bundle b=getArguments();
     int selectionNumber=b.getInt(ARG_SECTION_NUMBER);
      if(selectionNumber==1){
         View rootView=inflater.inflate(R.layout.fragment_tabbed,container,false);
         listView=(ListView)rootView.findViewById(R.id.list);
          if(TwitterUtils.twitterSession!=null) {
              UserTimeline userTimeline = new UserTimeline.Builder()
                      .screenName(TwitterUtils.twitterSession.getUserName())
                      .build();
              CustomAdapter.OnTweetClickListener listener = new CustomAdapter.OnTweetClickListener() {
                  @Override
                  public void onTweetClicked(int position, Tweet tweet) {
                      Intent i = new Intent(getContext(), TweetView.class);
                      i.putExtra("id", tweet.id);
                      startActivity(i);
                  }
              };
              CustomAdapter adapter = new CustomAdapter(getContext(), userTimeline, listener);
              listView.setAdapter(adapter);
              adapter.notifyDataSetChanged();}
              return rootView;

      }else if(selectionNumber ==2){
          EditText editText=new EditText(getContext());
          editText.setClickable(true);
          Button button=new Button(getContext());
          button.setClickable(true);

          View rootView=inflater.inflate(R.layout.fragment_tabbed,container,false);
          listView =(ListView)rootView.findViewById(R.id.list);
          if(TwitterUtils.twitterSession!=null) {
              List<String> handles = Arrays.asList("ericfrohnhoefer", "benward", "vam_si");
              final FilterValues filterValues = new FilterValues(null, null, handles, null); // or load from JSON, XML, etc
              final TimelineFilter timelineFilter = new BasicTimelineFilter(filterValues, Locale.ENGLISH);
              SearchTimeline searchTimeline = new SearchTimeline.Builder()
                      .query("twitterdev")
                      .build();
              final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                      .setTimeline(searchTimeline)
                      .setTimelineFilter(timelineFilter)
                      .build();
              listView.setAdapter(adapter);
              adapter.notifyDataSetChanged();
          }
          return rootView;
      } else if(selectionNumber ==3){

          View rootView=inflater.inflate(R.layout.fragment_tabbed,container,false);
          listView=(ListView)rootView.findViewById(R.id.list);
          if(TwitterUtils.twitterSession!=null) {
              ApiInterface apiInterface = ApiClient.getInstance();
              Call<ArrayList<Tweet>> call = apiInterface.getUserTimline();
              final ArrayList<Tweet> use = new ArrayList<>();
              call.enqueue(new Callback<ArrayList<Tweet>>() {
                  @Override
                  public void onResponse(Call<ArrayList<Tweet>> call, Response<ArrayList<Tweet>> response) {
                      FixedTweetTimeline homeTimeline = new FixedTweetTimeline.Builder()
                              .setTweets(response.body())
                              .build();

                      use.addAll(response.body());

                      CustomAdapter.OnTweetClickListener listener = new CustomAdapter.OnTweetClickListener() {
                          @Override
                          public void onTweetClicked(int position, Tweet tweet) {
                              Intent i = new Intent(getContext(), TweetView.class);
                              i.putExtra("id", tweet.id);
                              startActivity(i);
                          }
                      };
                      CustomAdapter adapter = new CustomAdapter(getContext(), homeTimeline, listener);
                      listView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                  }

                  @Override
                  public void onFailure(Call<ArrayList<Tweet>> call, Throwable t) {

                  }
              });
          }
          return rootView;
      }else if(selectionNumber==4){
          View rootView=inflater.inflate(R.layout.fragment_tabbed,container,false);
         listView=(ListView)rootView.findViewById(R.id.list);
          if(TwitterUtils.twitterSession!=null) {
              ApiInterface apiInterface = ApiClient.getInstance();
              Call<ArrayList<Home>> call = apiInterface.getUserTrends("1");
              call.enqueue(new Callback<ArrayList<Home>>() {

                  @Override
                  public void onResponse(Call<ArrayList<Home>> call, Response<ArrayList<Home>> response) {
                      Log.i("PlaceholderFragment",response.body().get(0)+" ");
                    Home home = response.body().get(0);
                      ArrayList<Trends> trendList = home.trends;
                      ArrayList<String> list = new ArrayList<>();
                      for (int i = 0; i < trendList.size(); i++) {
                          list.add(trendList.get(i).name);
                      }
                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.usedfortrends, list);
                      listView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                  }

                  @Override
                  public void onFailure(Call<ArrayList<Home>> call, Throwable t) {

                  }
              });
              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      Intent i = new Intent(getContext(), TrendsOpenActivity.class);
                      TextView textView = (TextView) view.findViewById(R.id.textView2);
                      i.putExtra("TrendsClicked", textView.getText().toString());
                      startActivity(i);
                  }
              });
          }
          return rootView;
      }
      return null;
     }
}
