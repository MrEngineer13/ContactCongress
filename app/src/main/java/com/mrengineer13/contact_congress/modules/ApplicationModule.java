package com.mrengineer13.contact_congress.modules;

import android.app.Application;

import com.mrengineer13.contact_congress.ContactCongress;
import com.mrengineer13.contact_congress.utils.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jon on 6/26/16.
 */

@Module
public class ApplicationModule {
    private final ContactCongress app;

    public ApplicationModule(ContactCongress app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    RxBus provideBus() {
        return new RxBus();
    }
}
