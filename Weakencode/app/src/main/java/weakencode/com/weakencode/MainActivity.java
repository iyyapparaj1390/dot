package weakencode.com.weakencode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weakencode.com.weakencode.Api.CreateDotPreference;
import weakencode.com.weakencode.Api.MyModulePreference;
import weakencode.com.weakencode.Api.Networkcall;
import weakencode.com.weakencode.helper.Apicalls;
import weakencode.com.weakencode.helper.LoginDetails;
import weakencode.com.weakencode.helper.Permission;
import weakencode.com.weakencode.helper.Utills;

public class MainActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_pass)
    EditText inputPass;
    @BindView(R.id.input_layout_pass)
    TextInputLayout inputLayoutPass;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.rel_reg)
    RelativeLayout relReg;
    @BindView(R.id.login)
    LinearLayout login;
    LoginDetails details = new LoginDetails();
    ProgressDialog dia;
    //CreateDotPreference pref;
    MyModulePreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnSignup.setOnClickListener(this);
        pref=new MyModulePreference(MainActivity.this);
        //pref=CreateDotPreference.getInstance(MainActivity.this);
        details.setEmail("fragrantorc@mailinator.com");
        details.setDevice_id("b4191ec717ee49f6");
        details.setDevice_type("1");
        details.setPassword(Permission.sha256("Test1234"));
        inputEmail.setText(details.getEmail());
        inputPass.setText("Test1234");


    }

    void init() {
         if (Permission.checknetwork(MainActivity.this)) {
            dia=new ProgressDialog(this,R.style.MyAlertDialogStyle);
            dia.setMessage("Login..");
            dia.setCancelable(false);
            // dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
            dia.show();

            Apicalls networkcall = Networkcall.getClient().create(Apicalls.class);
            Call<String> getDetails = networkcall.getLoginDetails(details.getEmail(), details.getPassword(), details.getDevice_id(), details.getDevice_type());
            getDetails.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        if(dia.isShowing())
                            dia.dismiss();
                        if (response != null) {
                            JSONObject object = new JSONObject(response.body().toString());
                            JSONObject getdata = getObject("data", object);
                            Log.e("accesstoke", getString("access_token", getdata));
                            String access_token = getString("access_token", getdata);
                            Log.e("acc", "tok" + access_token);

                            /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("access_token", access_token);
                            editor.apply();
*/
                            pref.put(CreateDotPreference.token,access_token);
                            pref.put(CreateDotPreference.login,true);
                            startActivity(new Intent(MainActivity.this, DotcreateActivity.class));
                            finish();

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
            Permission.showmessage(Utills.networkCheck, MainActivity.this);
        }
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

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.btn_signup:
                init();
                break;
               // startActivity(new Intent(MainActivity.this, DotcreateActivity.class));
        }
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
