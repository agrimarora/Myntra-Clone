package com.example.myntra.data.di

import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
  @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
return FirebaseAuth.getInstance()
    }
    @Singleton
    @Provides
    fun providefirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()

    }

}