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
import android.widget.SearchView;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.controllers.ChannelController;
import org.clynch203.gcu.coursework.fragments.AboutFragment;
import org.clynch203.gcu.coursework.fragments.DateRangeFragment;
import org.clynch203.gcu.coursework.fragments.HomeFragment;
import org.clynch203.gcu.coursework.fragments.MapFragment;
import org.clynch203.gcu.coursework.util.XMLParser;

public class MainActivity extends AppCompatActivity implements DateRangeFragment.InterfaceCommunicator {

    private ChannelController channelController;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    private Toolbar toolbar;
    private Menu menu;

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
        toolbar = findViewById(R.id.toolbar);
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
                        updateToolbar("Home");
                        break;
                    case "Map":
                        currentFragment = new MapFragment();
                        ((MapFragment) currentFragment).setChannelController(channelController);
                        updateToolbar("Map");
                        break;
                    case "About":
                        currentFragment = new AboutFragment();
                        updateToolbar("About");
                        break;
                }

                fragmentTransaction.replace(R.id.content_frame, currentFragment);
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    private void updateToolbar(String fragmentName) {
        toolbar.setTitle(fragmentName);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem dateItem = menu.findItem(R.id.action_date);

        if (fragmentName.equals("Home")) {
            searchItem.setVisible(true);
            dateItem.setVisible(true);
        } else {
            searchItem.setVisible(false);
            dateItem.setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String query) {
                ((HomeFragment)currentFragment).displayItems(channelController.searchItemsByLocation(query));
                return false;
            }
        });
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
