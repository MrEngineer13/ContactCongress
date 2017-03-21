package com.mrengineer13.contact_congress;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.mrengineer13.contact_congress.modules.ApplicationModule;
import com.mrengineer13.contact_congress.modules.DataModule;
import com.mrengineer13.contact_congress.modules.NetworkModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by Jon on 6/23/16.
 */

public class ContactCongress extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .dataModule(new DataModule())
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

//            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
