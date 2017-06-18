package com.programs.lala.myschool;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.programs.lala.myschool.Adapter.MessageAdapter;
import com.programs.lala.myschool.data.MessageContract;
import com.programs.lala.myschool.fragments.Accounts;
import com.programs.lala.myschool.fragments.BussFragment;
import com.programs.lala.myschool.fragments.GetBusLocation;
import com.programs.lala.myschool.fragments.Home;
import com.programs.lala.myschool.interfaces.Communication;
import com.programs.lala.myschool.modeld.AccountModel;
import com.programs.lala.myschool.modeld.MessageModel;
import com.programs.lala.myschool.modeld.PostModel;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.LoaderManager.LoaderCallbacks;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Communication, LoaderCallbacks<Cursor> {
    MessageModel model = new MessageModel();
    List<MessageModel> messageList = new ArrayList<>();

    MessageAdapter adapter;

    @InjectView(R.id.home)
    ImageButton home;
    @InjectView(R.id.bus)
    ImageButton bus;
    @InjectView(R.id.accounts)
    ImageButton accounts;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectView(R.id.content)
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////
        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_message_white_24dp, this.getTheme());

        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        ////////////////////////


        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                     @Override
                                     public void onDrawerSlide(View drawer, float slideOffset) {


                                         contentView.setX(navigationView.getWidth() * slideOffset);
                                         RelativeLayout.LayoutParams lp =
                                                 (RelativeLayout.LayoutParams) contentView.getLayoutParams();
                                         lp.height = drawer.getHeight() -
                                                 (int) (drawer.getHeight() * slideOffset * 0.3f);
                                         contentView.setLayoutParams(lp);
                                     }

                                     @Override
                                     public void onDrawerClosed(View drawerView) {
                                     }
                                 }
        );
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Home fragment = new Home();
        ft.replace(R.id.fragment, fragment);

        ft.commit();

        accounts.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        bus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        home.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));


        LoaderCallbacks<Cursor> callback = MainActivity.this;

        Bundle bundleForLoader = null;


        getSupportLoaderManager().initLoader(0, bundleForLoader, callback);

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                String type = getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .getString("type", "parent");
                if (type.equals("parent")) {

                    GetBusLocation fragment = new GetBusLocation();
                    ft.replace(R.id.fragment, fragment);
                } else {

                    BussFragment fragment = new BussFragment();
                    ft.replace(R.id.fragment, fragment);

                }
                ft.commit();
                accounts.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                bus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
                home.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                Home fragment = new Home();
                ft.replace(R.id.fragment, fragment);

                ft.commit();

                accounts.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                bus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                home.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
            }
        });
        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                Accounts fragment = new Accounts();
                ft.replace(R.id.fragment, fragment);

                ft.commit();
                accounts.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
                bus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                home.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));


            }
        });
        /////////////////////////////
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
        getMenuInflater().inflate(R.menu.main3, menu);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClickPost(PostModel model) {
        Intent intent = new Intent(MainActivity.this, PostDetails.class);
        intent.putExtra("postModel", model);

        startActivity(intent);
    }

    @Override
    public void onClickAccount(AccountModel model) {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        intent.putExtra("accountModel", model);
        startActivity(intent);
    }

    @Override
    public void onClickMessage(MessageModel model) {
        Intent intent = new Intent(MainActivity.this, ChatRoom.class);
        intent.putExtra("messageModel", model);
        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MessageContract.MessageEntry.CONTENT_URI
                , new String[]{MessageContract.MessageEntry.COLUMN_name, MessageContract.MessageEntry.COLUMN_message,
                MessageContract.MessageEntry.COLUMN_sender_image,
                MessageContract.MessageEntry.COLUMN_sender_id, MessageContract.MessageEntry.COLUMN_receive_id
        }
                , null, null, MessageContract.MessageEntry.COLUMN_id + " DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        messageList.clear();
        while (data.moveToNext()) {
            model = new MessageModel();
            model.setName(data.getString(0));
            model.setMessge(data.getString(1));
            model.setImage(data.getString(2));
            model.setSender_id(data.getString(3));
            model.setReceiver_id(data.getString(4));

            boolean exist = false;
            for (int i = 0; i < messageList.size(); i++) {
                if (model.getName().equals(messageList.get(i).getName())) {
                    exist = true;
                    break;
                }

            }
            if (!exist) {
                messageList.add(model);
            }
        }
        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
