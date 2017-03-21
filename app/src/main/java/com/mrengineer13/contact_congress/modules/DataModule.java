package com.mrengineer13.contact_congress.modules;

import android.app.Application;

import com.mrengineer13.contact_congress.utils.Prefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Jon on 6/28/16.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    Prefs provideApplication(Application app) {
        return new Prefs(app);
    }

    @Provides
    @Singleton
    Realm provideRealm(Application app) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(app).build();
        Realm.setDefaultConfiguration(realmConfig);
        return Realm.getDefaultInstance();
    }
}
