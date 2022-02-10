package com.example.github.di

import com.example.github.BuildConfig
import com.example.github.remote.service.GitHubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideGithubApiService(
        moshi: Moshi,
        client: Lazy<OkHttpClient>
    ): GitHubService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client.get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GitHubService::class.java)
    }


    @Provides
    @Singleton
    fun provideGenericOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        trustManager: Array<TrustManager>,
        sslContext: SSLContext?
    ): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .sslSocketFactory(sslContext?.socketFactory!!, trustManager[0] as X509TrustManager).build()


    @Provides
    @Singleton
    fun provideTrustCertificate(): Array<TrustManager> {
        return arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })
    }

    @Provides
    @Singleton
    fun provideSSlContext(
        trustManager: Array<TrustManager>
    ): SSLContext? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustManager, SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sslContext
    }
}
