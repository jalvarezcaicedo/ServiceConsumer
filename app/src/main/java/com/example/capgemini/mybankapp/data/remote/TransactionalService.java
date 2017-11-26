package com.example.capgemini.mybankapp.data.remote;

import android.content.Context;

import com.example.capgemini.mybankapp.R;
import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerInformationResponse;
import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerProductResponse;
import com.example.capgemini.mybankapp.data.model.login.Login;
import com.example.capgemini.mybankapp.data.model.signup.SignUpRequest;
import com.example.capgemini.mybankapp.data.model.signup.SignUpResponse;
import com.example.capgemini.mybankapp.data.model.transaction.TransactionRequest;
import com.example.capgemini.mybankapp.data.model.transaction.TransactionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class TransactionalService {

    public static RetrofitInterface newTransactionalService(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();


        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("localhost", "sha256/URRP0/Bw8F3eMJ5Sn6m/C+aYOEK97e82x+l0cjXVRzM=")
                .build();


        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(R.raw.mybank_client))) {
            ca = cf.generateCertificate(caInput);
        }
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .certificatePinner(certificatePinner)
                .hostnameVerifier((s, sslSession) -> {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("localhost", sslSession);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlConnection.TRANSACTIONAL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(RetrofitInterface.class);

    }

    public interface RetrofitInterface {
        @POST("/login")
        Observable<Response<Login>> loginCall(@Body Login login);

        @POST("/api/1.0/customer/sign-up")
        Observable<Response<SignUpResponse>> signUpCall(@Body SignUpRequest signUpRequest);

        @GET
        Observable<Response<CustomerInformationResponse>> customerByIdCall(@Url String url, @Header("Authorization") String authorization);

        @GET
        Observable<Response<CustomerProductResponse>> customerProductCall(@Url String url, @Header("Authorization") String authorization);

        @GET
        Observable<Response<List<TransactionResponse>>> lastTransactionsCall(@Url String url, @Header("Authorization") String authorization);

        @POST("/api/1.0/transaction/save")
        Observable<Response<TransactionResponse>> saveTransactionCall(@Header("Authorization") String authorization, @Body TransactionRequest transactionRequest);

    }
}
