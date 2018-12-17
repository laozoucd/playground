package com.stingerzou.mygithub.di;

import com.stingerzou.mygithub.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
