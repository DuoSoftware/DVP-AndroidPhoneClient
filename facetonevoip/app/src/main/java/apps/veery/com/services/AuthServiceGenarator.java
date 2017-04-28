package apps.veery.com.services;

import apps.veery.com.model.Auth;
import apps.veery.com.model.Revoke;
import apps.veery.com.reqmodel.ReqUser;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Lakshan on 11/11/2016.
 */
public interface AuthServiceGenarator {

    @Headers({
            "Content-Type:application/json",
            "Authorization:Basic YWU4NDkyNDAtMmM2ZC0xMWU2LWIyNzQtYTllZWM3ZGFiMjZiOjYxNDU4MTMxMDIxNDQyNTgwNDg="
    })
    @POST("token")
    Call<Auth> getAuth(@Body ReqUser user);

    @DELETE("token/revoke/{jti}")
    Call<Revoke> getRevoke(@Header("Authorization") String authorization, @Path("jti") String jti );

    class AuthServiceFactory{

        private static AuthServiceGenarator service;
        public static AuthServiceGenarator getInstance(){
//            HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
            String BASE_URL="http://userservice.app.veery.cloud/oauth/";
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(logLevel);
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service=retrofit.create(AuthServiceGenarator.class);
                return service;
            }else {
                return service;
            }
        }
    }
}
