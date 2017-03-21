package com.mrengineer13.contact_congress;

import android.app.Activity;

import com.mrengineer13.contact_congress.base.BaseActivity;
import com.mrengineer13.contact_congress.base.BaseFragment;
import com.mrengineer13.contact_congress.modules.ApplicationModule;
import com.mrengineer13.contact_congress.modules.DataModule;
import com.mrengineer13.contact_congress.modules.NetworkModule;
import com.mrengineer13.contact_congress.settings.SettingsFragment;
import com.mrengineer13.contact_congress.utils.Prefs;
import com.mrengineer13.contact_congress.utils.RxBus;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by Jon on 6/26/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class})
public interface AppComponent {

    RxBus bus();

    Realm realm();

    Prefs prefs();

    void inject(ContactCongress app);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(Activity activity);
}
