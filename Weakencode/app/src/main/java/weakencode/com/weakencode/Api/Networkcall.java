package weakencode.com.weakencode.Api;

/**
 * Created by samsung on 3/30/2018.
 */

import butterknife.internal.Utils;

import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import weakencode.com.weakencode.helper.Utills;


public class Networkcall {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utills.url)
                    .addConverterFactory(ScalarsConverterFactory.create())

                    .build();
        }
        return retrofit;
    }
}