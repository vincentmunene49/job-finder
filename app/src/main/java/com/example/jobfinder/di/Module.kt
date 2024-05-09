package com.example.jobfinder.di

import com.example.jobfinder.admin.post.data.DefaultPostJobRepositoryImpl
import com.example.jobfinder.admin.post.domain.PostJobRepository
import com.example.jobfinder.auth.login.data.DefaultLoginRepositoryImpl
import com.example.jobfinder.auth.login.domain.LoginRepository
import com.example.jobfinder.auth.sign_up.data.DefaultRegisterRepositoryImpl
import com.example.jobfinder.auth.sign_up.domain.RegisterRepository
import com.example.jobfinder.common.util.validator.DefaultFormValidatorRepositoryImpl
import com.example.jobfinder.common.util.validator.FormValidatorRepository
import com.example.jobfinder.user.home.data.RepositoryDefaultImpl
import com.example.jobfinder.user.home.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): RegisterRepository = DefaultRegisterRepositoryImpl(firebaseAuth, firebaseFirestore)


    @Provides
    @Singleton
    fun provideFormValidatorRepository(): FormValidatorRepository =
        DefaultFormValidatorRepositoryImpl()

    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): LoginRepository = DefaultLoginRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Provides
    @Singleton
    fun provideJobRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): PostJobRepository = DefaultPostJobRepositoryImpl(firebaseFirestore, firebaseStorage)


    @Provides
    @Singleton
    fun provideHomeRepository(
        firebaseFirestore: FirebaseFirestore,
    ): Repository = RepositoryDefaultImpl(firebaseFirestore)


    @Provides
    @Singleton
    fun provideViewJobRepository(firebaseFirestore: FirebaseFirestore): com.example.jobfinder.user.view_job.domain.Repository =
        com.example.jobfinder.user.view_job.data.ViewJobDefaultRepositoryImpl(firebaseFirestore)


}