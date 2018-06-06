package weakencode.com.weakencode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weakencode.com.weakencode.Api.CreateDotPreference;
import weakencode.com.weakencode.Api.MyModulePreference;
import weakencode.com.weakencode.Api.Networkcall;
import weakencode.com.weakencode.helper.Apicalls;
import weakencode.com.weakencode.helper.Createdot;
import weakencode.com.weakencode.helper.Permission;
import weakencode.com.weakencode.helper.Utills;

/**
 * Created by samsung on 6/5/2018.
 */

public class DotcreateActivity extends Activity {
    Button logout, create_dot;
    TextInputLayout inputLayout;
    EditText dot;
    Createdot createdot = new Createdot();
    String token;
    ProgressDialog dia;
   // CreateDotPreference pref;
    MyModulePreference pref;
    ArrayList<Createdot>dotlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    private void init() {
        //pref=CreateDotPreference.getInstance(DotcreateActivity.this);
        pref=new MyModulePreference(DotcreateActivity.this);
        logout = (Button) findViewById(R.id.logout);
        create_dot = (Button) findViewById(R.id.create_dot);
        inputLayout = (TextInputLayout) findViewById(R.id.textinput);
        dot = (EditText) findViewById(R.id.dot);
       // SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        token = pref.getString(CreateDotPreference.token,"");
        Log.e("data", "access" + token);

        createdot.setPh_country_code("+91");
        createdot.setPhone_number("7305916574");
        createdot.setCategory_id(1);
        createdot.setAddress("No 7 shanmugam salai, Vadapalani");
        createdot.setDescription("Dot Full Description");
        createdot.setLat("13.054938");
        createdot.setLng("80.208097");
        createdot.setPost_code("600078");
        createdot.setStreet("600078");
        createdot.setCity("Chennai");
        createdot.setState("Tamin Nadu");
        createdot.setCountry("India");
        createdot.setFolder_id("0");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.clear();
                finish();
            }
        });

        create_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = dot.getText().toString();
                if (!name.isEmpty()) {
                    createdot.setName(dot.getText().toString());

                    if (Permission.checknetwork(DotcreateActivity.this)) {
                        dia=new ProgressDialog(DotcreateActivity.this,R.style.MyAlertDialogStyle);
                        dia.setMessage("Inserting..");
                        dia.setCancelable(false);
                        // dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                        dia.show();

                        Apicalls networkcall = Networkcall.getClient().create(Apicalls.class);
                        Call<String> getDetails = networkcall.createDot("Bearer " + token, dot.getText().toString(), createdot.getPh_country_code(), createdot.getPhone_number(),
                                String.valueOf(createdot.getCategory_id()), createdot.getAddress(), createdot.getDescription(), createdot.getLat(), createdot.getLng(),
                                createdot.getPost_code(), createdot.getStreet(), createdot.getCity(), createdot.getState(), createdot.getCountry(), createdot.getFolder_id());

                        getDetails.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.e("response", "" + response.body().toString());
                                if(dia.isShowing())
                                    dia.dismiss();
                                try {
                                    if (response != null) {

                                        JSONObject object = new JSONObject(response.body().toString());
                                        JSONObject getdata = getObject("data", object);
                                        Log.e("response", "" + getdata.toString());
                                        dot.getText().clear();
                                        Permission.showmessage("Successfully Created",DotcreateActivity.this);
                                    }
                                } catch (Exception e) {
                                    if(dia.isShowing())
                                        dia.dismiss();


                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                if(dia.isShowing())
                                    dia.dismiss();

                            }
                        });

                    } else {

                        if(pref.getString(CreateDotPreference.list,"").equalsIgnoreCase(""))
                        {
                            dotlist.clear();
                            dotlist.add(createdot);
                            Gson gson = new Gson();
                            String json = gson.toJson(dotlist);
                            pref.put(CreateDotPreference.list,json);
                           Log.e("1","1");


                        }
                        else{
                            Gson gson = new Gson();
                            String json = pref.getString(CreateDotPreference.list,"");

                            Type type = new TypeToken<ArrayList<Createdot>>(){}.getType();
                            ArrayList<Createdot> dotscreate= gson.fromJson(json, type);
                            dotscreate.add(createdot);
                            String jsonsave = gson.toJson(dotscreate);
                            pref.put(CreateDotPreference.list,jsonsave);
                            Log.e("2","2");


                        }
                        dot.getText().clear();
                        Permission.showmessage("Please check your internet. Meantime dot stored in local storage.Once internet is available dot will be created automatically.", DotcreateActivity.this);
                    }
                }
                else{
                    Permission.showmessage("Please give valid name", DotcreateActivity.this);

                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onstart", "onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onresume", "onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onpause", "onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ontstop", "onstop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onrestart", "onrestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ondestroy", "ondestroy");
    }

    JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        if (jObj.has(tagName)) {
            return jObj.getJSONObject(tagName);
        } else {
            return new JSONObject();
        }
    }

    JSONArray getJsonarray(String tagName, JSONObject jObj) throws JSONException {
        if (jObj.has(tagName)) {
            return jObj.getJSONArray(tagName);
        } else {
            return new JSONArray();
        }
    }

    String getString(String tagName, JSONObject jObj) throws JSONException {
        if (jObj.has(tagName)) {

            return jObj.getString(tagName);
        } else {
            return "";
        }
    }
}
