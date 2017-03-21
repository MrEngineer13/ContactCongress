package com.mrengineer13.contact_congress.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.mrengineer13.contact_congress.R;
import com.mrengineer13.contact_congress.congress_list.CongressListFragment;
import com.mrengineer13.contact_congress.congress_search.CongressSearchFragment;
import com.mrengineer13.contact_congress.settings.SettingsFragment;

/**
 * Created by Jon on 6/26/16.
 */
public class HomeNavAdapter implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;

    public HomeNavAdapter(FragmentManager manager) {
        this.manager = manager;
    }

    public void selectNavItem(@NonNull MenuItem item) {
        Fragment fragment = new SettingsFragment();
        switch (item.getItemId()) {
            case R.id.action_list:
                fragment = new CongressListFragment();
                break;
            case R.id.action_search:
                fragment = new CongressSearchFragment();
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                break;
        }
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectNavItem(item);
        return true;
    }
}
