package com.example.jobfinder.di

import android.content.Context
import com.example.jobfinder.admin.post.data.DefaultPostJobRepositoryImpl
import com.example.jobfinder.admin.post.domain.PostJobRepository
import com.example.jobfinder.auth.login.data.DefaultLoginRepositoryImpl
import com.example.jobfinder.auth.login.domain.LoginRepository
import com.example.jobfinder.auth.sign_up.data.DefaultRegisterRepositoryImpl
import com.example.jobfinder.auth.sign_up.domain.RegisterRepository
import com.example.jobfinder.common.util.downloader.Downloader
import com.example.jobfinder.common.util.downloader.DownloaderImpl
import com.example.jobfinder.common.util.validator.DefaultFormValidatorRepositoryImpl
import com.example.jobfinder.common.util.validator.FormValidatorRepository
import com.example.jobfinder.user.applications.data.DefaultGetJobApplicationImp
import com.example.jobfinder.user.applications.domain.GetJobApplications
import com.example.jobfinder.user.apply.data.ApplicationRepositoryDefaultImpl
import com.example.jobfinder.user.apply.domain.ApplicationRepository
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
import dagger.hilt.android.qualifiers.ApplicationContext
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
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): PostJobRepository = DefaultPostJobRepositoryImpl(firebaseFirestore, firebaseStorage,firebaseAuth)


    @Provides
    @Singleton
    fun provideHomeRepository(
        firebaseFirestore: FirebaseFirestore,
    ): Repository = RepositoryDefaultImpl(firebaseFirestore)


    @Provides
    @Singleton
    fun provideViewJobRepository(firebaseFirestore: FirebaseFirestore, auth: FirebaseAuth): com.example.jobfinder.user.view_job.domain.Repository =
        com.example.jobfinder.user.view_job.data.ViewJobDefaultRepositoryImpl(firebaseFirestore,auth)


    @Provides
    @Singleton
    fun provideApplicationRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        auth: FirebaseAuth,
    ): ApplicationRepository =
        ApplicationRepositoryDefaultImpl(firebaseFirestore, firebaseStorage, auth)

    @Provides
    @Singleton
    fun provideJobViewingRepository(
        auth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): GetJobApplications =
        DefaultGetJobApplicationImp(auth = auth, dataBase = firebaseFirestore)

    @Provides
    @Singleton
    fun provideApplicantsRepo(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): com.example.jobfinder.admin.applications.domain.ApplicantsRepo =
        com.example.jobfinder.admin.applications.data.DefaultApplicantRepoImpl(database, auth)

    @Provides
    @Singleton
    fun provideCandidateRepo(
        firestore: FirebaseFirestore
    ): com.example.jobfinder.admin.candidate.domain.CandidateRepo =
        com.example.jobfinder.admin.candidate.data.CandidateRepoImpl(firestore)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDownloader(context: Context): Downloader {
        return DownloaderImpl(context)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): com.example.jobfinder.profile.domain.ProfileScreenRepo =
        com.example.jobfinder.profile.data.DefaultProfileRepoImpl(firebaseAuth, firebaseFirestore, firebaseStorage)
}