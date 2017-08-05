package com.example.lenovo.twitterapna2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    final private int tabCount=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager=(ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        final int[] ICONS = new int[]{
                R.mipmap.tweets,
                R.mipmap.search,
                R.mipmap.recent,
                R.mipmap.trents

        };
        for(int i=0;i<tabCount;i++) {
            tabLayout.getTabAt(i).setIcon(ICONS[i]);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:toolbar.setTitle("MyTweets");
                        break;
                    case 1:toolbar.setTitle("SEARCH");
                        break;
                    case 2:toolbar.setTitle("RECENT");
                        break;
                    case 3:toolbar.setTitle("TRENDS");
                        break;

                }
                mSectionsPagerAdapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new ComposerActivity.Builder(MainActivity.this)
                       .session(TwitterUtils.twitterSession)
                      .image(Uri.parse("https://www.google.co.in/search?q=hd+wallpaper&tbm=isch&imgil=mQHsIJxCQG6oDM%253A%253BG8XXjjbUAYL6HM%253Bhttps%25253A%25252F%25252Fwww.planwallpaper.com%25252Fwallpaper-hd&source=iu&pf=m&fir=mQHsIJxCQG6oDM%253A%252CG8XXjjbUAYL6HM%252C_&usg=__SnfWR2Mf8TMgg6VVCQa-MZSb6WE%3D&biw=1366&bih=662&ved=0ahUKEwiOwOjjmarVAhWMmZQKHd-fBPUQyjcIQA&ei=kT96Wc6zH4yz0gTfv5KoDw#imgrc=9CPmb1CtMCBzwM:"))
                      .text("Love where you work")
                      .hashtags("#twitter")
                      .createIntent();
               startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if(id==R.id.login){

        }else if(id==R.id.logout){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
