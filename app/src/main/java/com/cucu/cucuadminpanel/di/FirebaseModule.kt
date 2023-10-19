package com.cucu.cucuadminpanel.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /*@Provides
    @Singleton
    fun providesFirebaseAuth():FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseUser() : FirebaseUser? = FirebaseAuth.getInstance().currentUser*/

    @Provides
    @Singleton
    fun provideFirebaseStorage() : FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()
}