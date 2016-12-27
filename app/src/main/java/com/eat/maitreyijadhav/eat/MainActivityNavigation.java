package com.eat.maitreyijadhav.eat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;

import android.content.res.Configuration;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//this is the main activity that holds the framework that handles different fragments
public class MainActivityNavigation extends AppCompatActivity {
    //Declare variables
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private int navigationForLoggedInUser = R.array.navigation_drawer_items_array;
    private int navigationForNormalUser = R.array.navigation_drawer_items_array_normal;
    private int menuItems = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        //get the firebase authentication instance and get the current logged in user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //if user is logged in then provide log out button and vice versa
        if (firebaseAuth.getCurrentUser() == null) {
            menuItems = navigationForNormalUser;
        } else if (firebaseUser.getEmail().toString().equals("adminfoodies@gmail.com")) {
            menuItems = R.array.navigation_drawer_items_array_admin;
            //Toast.makeText(this, "Welcome " + firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Welcome " + firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
            menuItems = navigationForLoggedInUser;
        }
        //check if intent is start fr
        if (getIntent().getIntExtra("StartFragmentNUmber", 0) == 1) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new StartersPage());
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            this.getSupportActionBar().setTitle(("Starter"));
        }
        else if (getIntent().getIntExtra("StartFragmentNUmber", 0) == 3) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new MainCoursePage());
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            this.getSupportActionBar().setTitle(("Main Course"));
        } else if (getIntent().getIntExtra("StartFragmentNUmber", 0) == 2) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DessertPage());
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            this.getSupportActionBar().setTitle(("Dessert"));
        }else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomePage());
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            this.getSupportActionBar().setTitle(("Home"));
        }
        titles = getResources().getStringArray(menuItems);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_navigation);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            //Called when a drawer has settled in a completely closed state
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        //toggles the navigation icon
        drawerLayout.addDrawerListener(drawerToggle);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

    }
    //checks which element is clicked from navigation menu
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Code to run when the item gets clicked
            selectItem(position);

        }
    }

    //on pressing back button check if there is any stack element
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();

        if (backCount > 0) {
            finish();
        }
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        //menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    //checks condition of toggele- opened or closed
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //checks if device screen orientation changed
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new HomePage();
                break;
            case 1:
                fragment = new StartersPage();
                break;
            case 2:
                fragment = new MainCoursePage();
                break;
            case 3:
                fragment = new DessertPage();
                break;
            case 4:
                fragment = new ShoppingListPage();
                break;
            case 5:
                fragment = new SettingsPage();
                break;
            case 6:
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginActivityPage.class));
                finish();
                break;
            default:
                fragment = new HomePage();

        }

        //launch fragment accordingly
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        this.getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
