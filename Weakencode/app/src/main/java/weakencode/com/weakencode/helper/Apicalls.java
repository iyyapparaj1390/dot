package weakencode.com.weakencode.helper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static weakencode.com.weakencode.helper.Apicalls.login;

/**
 * Created by samsung on 6/5/2018.
 */

public interface Apicalls {
    public final static String login = "login";
    public final static String createdot = "dots";

    @POST(login)
    public Call<String> getLoginDetails(@Body LoginDetails lgoin);

    @FormUrlEncoded
    @POST(login)
    public Call<String> getLoginDetails(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String deviceid,
            @Field("device_type") String devicetype
    );

    @FormUrlEncoded
    @POST(createdot)
    public Call<String> createDot(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("ph_country_code") String phcountry,
            @Field("phone_number") String phonenumber,
            @Field("category_id") String catid,
            @Field("address") String address,
            @Field("description") String description,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("post_code") String postcode,
            @Field("street") String street,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country,
            @Field("folder_id") String folderid
    );


}
