package org.clynch203.gcu.coursework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.controllers.ChannelController;
import org.clynch203.gcu.coursework.fragments.AboutFragment;
import org.clynch203.gcu.coursework.fragments.DateRangeFragment;
import org.clynch203.gcu.coursework.fragments.HomeFragment;
import org.clynch203.gcu.coursework.fragments.MapFragment;
import org.clynch203.gcu.coursework.fragments.SettingsFragment;
import org.clynch203.gcu.coursework.util.XMLParser;

public class MainActivity extends AppCompatActivity implements DateRangeFragment.InterfaceCommunicator {

    private ChannelController channelController;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelController = new ChannelController(
                XMLParser.parseData(getIntent().getStringExtra("data")));

        drawerLayout = findViewById(R.id.drawer_layout);

        initialiseFragment();
        initialiseToolbar();
        initialiseNavigationDrawer();
    }

    private void initialiseFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = new HomeFragment();
        ((HomeFragment) currentFragment).setChannelController(channelController);
        fragmentTransaction.add(R.id.content_frame, currentFragment);
        fragmentTransaction.commit();
    }

    private void initialiseToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_toolbar_menu);
        }
    }

    private void initialiseNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (String.valueOf(menuItem.getTitle())) {

                    case "Home":
                        currentFragment = new HomeFragment();
                        ((HomeFragment) currentFragment).setChannelController(channelController);
                        break;
                    case "Map":
                        currentFragment = new MapFragment();
                        break;
                    case "About":
                        currentFragment = new AboutFragment();
                        break;
                    case "Settings":
                        currentFragment = new SettingsFragment();
                        break;
                }

                fragmentTransaction.replace(R.id.content_frame, currentFragment);
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).initialiseToolbar(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_date:
                DateRangeFragment fragment = new DateRangeFragment();
                fragment.show(getSupportFragmentManager(), "dateRangeFragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendRequest(int code, Intent requestIntent) {
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment)currentFragment).sendRequest(requestIntent);
        }
    }
}
