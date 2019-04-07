//
// Name                 Christopher Lynch
// Student ID           S1511825
// Programme of Study   Computing
//

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
import org.clynch203.gcu.coursework.models.Item;
import org.clynch203.gcu.coursework.util.XMLParser;

import static org.clynch203.gcu.coursework.util.Constants.DATE_RANGE_FRAGMENT_REQUEST_CODE;

/**
 * Main Activity for the application.
 * Loaded after the LaunchActivity has loaded required data.
 * Contains a DrawerLayout to display different Fragments.
 */
public class MainActivity extends AppCompatActivity implements DateRangeFragment.InterfaceCommunicator, NavigationView.OnNavigationItemSelectedListener {

    private ChannelController channelController;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    private Toolbar toolbar;
    private Menu menu;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gets the XML data loaded during the LaunchActivity
        // uses the XMLParser to create a Channel object and
        // initialise a ChannelController using the Channel
        channelController = new ChannelController(
                XMLParser.parseData(getIntent().getStringExtra("data")));

        // load the Toolbar and set it as the ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialise the FragmentManager
        fragmentManager = getSupportFragmentManager();

        // initialise the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // initialise the NavigationView and set an ItemSelected listener
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setCurrentFragment("Home");
    }

    /**
     * Sets the current displayed Fragment.
     *
     * @param title Name of the Fragment to display.
     */
    private void setCurrentFragment(String title) {
        drawerLayout.closeDrawers();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (title) {
            case "Home":
                currentFragment = new HomeFragment();
                ((HomeFragment) currentFragment).setChannelController(channelController);
                updateToolbar("Home");
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "Map":
                currentFragment = new MapFragment();
                ((MapFragment) currentFragment).setChannelController(channelController);
                updateToolbar("Map");
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "About":
                currentFragment = new AboutFragment();
                updateToolbar("About");
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
        }

        fragmentTransaction.replace(R.id.content_frame, currentFragment);
        fragmentTransaction.commit();
    }

    /**
     * Updates the toolbar when the currentFragment is changed.
     *
     * @param fragmentName Name of the new Fragment.
     */
    private void updateToolbar(String fragmentName) {
        toolbar.setTitle(fragmentName);

        if (menu == null) return;

        // hides the searchItem and dateItem from the
        // toolbar if the currentFragment is not 'Home'

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

    /**
     * Initialises the Toolbar menu.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();

        // makes the search view auto focus when the search item is selected
        // so the user can begin typing right away
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Called when user presses enter on search view.
             * As results are updated on text change this is not used.
             *
             * @param query Text in search view
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /**
             * Called when text changes in search view.
             *
             * @param query Text in search view
             */
            @Override
            public boolean onQueryTextChange(String query) {
                ((HomeFragment) currentFragment).displayItems(channelController.searchItemsByLocation(query));
                return false;
            }
        });
        return true;
    }

    /**
     * Called when Toolbar option is selected.
     *
     * @param item The item selected on the Toolbar.
     */
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

    /**
     * Called when DatePicker is submitted on the HomeFragment.
     *
     * @param code          Request code
     * @param requestIntent Data submitted from Fragment
     */
    @Override
    public void sendRequest(int code, Intent requestIntent) {
        if (code == DATE_RANGE_FRAGMENT_REQUEST_CODE) {
            ((HomeFragment) currentFragment).sendRequest(requestIntent);
        }
    }

    /**
     * Displays MapFragment and opens InfoWindow for selected Item.
     *
     * @param item Item to display InfoWindow for on Map.
     */
    public void displayItemOnMap(Item item) {
        setCurrentFragment("Map");
        ((MapFragment) currentFragment).setTargetItem(item);
    }

    /**
     * Called when NavigationDrawer option is selected.
     *
     * @param menuItem The menu item that was selected.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        setCurrentFragment(String.valueOf(menuItem.getTitle()));
        return true;
    }
}
