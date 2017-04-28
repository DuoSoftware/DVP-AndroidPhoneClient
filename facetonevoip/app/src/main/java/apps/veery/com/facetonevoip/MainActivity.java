package apps.veery.com.facetonevoip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipErrorcode;
import com.portsip.PortSipSdk;

import java.io.IOException;
import java.util.Random;

import apps.veery.com.model.Auth;
import apps.veery.com.model.ErrorInfo;
import apps.veery.com.model.jwtres.Decode;
import apps.veery.com.reqmodel.ReqUser;
import apps.veery.com.services.AppControler;
import apps.veery.com.services.AuthServiceGenarator;
import apps.veery.com.util.Line;
import apps.veery.com.util.SettingConfig;
import apps.veery.com.util.UserInfo;
import apps.veery.com.utilities.Components;
import apps.veery.com.utilities.JWTUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ReqUser user;
    Button btn_log;
    EditText tv_un, tv_pw,tv_sip,tv_port;
    TextView tv_powered, tv_www, tv_la_un, tv_la_pw;
    Typeface type_regular, type_bold;
    public static final String MyPREFERENCES = "FACETONEVOIPDATA";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    SweetAlertDialog dd;
    PortSipSdk mSipSdk;
    MyApplication myApplication;
    Context context = null;
    String LogPath = null;
    String licenseKey ="PORTSIP_TEST_LICENSE";

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
//        if (null != sharedpreferences.getString("User", null)) {
//            String shared_user = sharedpreferences.getString("User", null);
//            String shared_jwt = sharedpreferences.getString("UserJWT", null);
//            Gson gson = new Gson();
//            Auth obj_user = gson.fromJson(shared_user, Auth.class);
//
//            AppControler.setUser(obj_user);
//            Log.d("LOGKEY", obj_user.getAccessToken());
//            String user_JWT = null;
//            try {
//                user_JWT = JWTUtils.decoded(AppControler.getUser().getAccessToken());
//                Decode obj_jwt = gson.fromJson(user_JWT, Decode.class);
//                AppControler.setJwtres(obj_jwt);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Log.d("LOGJTI", AppControler.getJwtres().getJti());
//
//            Intent i = new Intent(this, DashActivity.class);
//            startActivity(i);
//            this.finish();
//
//        }
        type_regular = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Regular.otf");
        type_bold = Typeface.createFromAsset(getResources().getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        tv_un = (EditText) findViewById(R.id.main_et_un);
        tv_pw = (EditText) findViewById(R.id.main_et_pw);
        tv_sip = (EditText)findViewById(R.id.main_et_sip);
        tv_port = (EditText)findViewById(R.id.main_et_port);

        user = new ReqUser();
        user.setGrantType("password");
//        user.setScope("resourceid write_ardsresource write_notification read_myUserProfile profile_veeryaccount profile_avatar");
        user.setScope("write_ardsresource write_notification read_myUserProfile profile_veeryaccount resourceid");
        btn_log = (Button) findViewById(R.id.main_btn_log);
        dd = new Components().showLoadingSuccsessMassage(this);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dd.show();
                if (!isInternetOn(getApplicationContext())) {

                    dd.setTitleText("opps!").setContentText("Please check you Internet Connection")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    dd.dismiss();
                                }
                            })
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE)
                            ;
                    dd.show();
                } else {
                    validation();
                }
            }
        });

        btn_log.setTypeface(type_regular);
        tv_un.setTypeface(type_regular);
        tv_pw.setTypeface(type_regular);
        context = getApplicationContext();
        myApplication = ((MyApplication) getApplicationContext());
        mSipSdk = myApplication.getPortSIPSDK();
        myApplication.setCurrentActivity(this);

    }

    private void validation() {
        if (TextUtils.isEmpty(tv_un.getText())) {
            dd.setTitleText("opps!").setContentText("Username can't empty")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        } else if (TextUtils.isEmpty(tv_pw.getText())) {
            dd.setTitleText("opps!").setContentText("Password cant empty")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }else if(TextUtils.isEmpty(tv_sip.getText())){
            dd.setTitleText("opps!").setContentText("Sip cant empty")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }else if(TextUtils.isEmpty(tv_port.getText())){
            dd.setTitleText("opps!").setContentText("Port cant empty")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        } else {
            user.setUsername(tv_un.getText().toString());
            user.setPassword(tv_pw.getText().toString());
            networkCall(user);
        }
    }

    private void networkCall(ReqUser user) {
        online();
//        goTohome();
    }
    private int online() {
        dd.show();
        myApplication.setpDialog(dd);
        int result = setUserInfo();

        if (result == PortSipErrorcode.ECoreErrorNone) {
            result = mSipSdk.registerServer(90, 3);
            if(result!=PortSipErrorcode.ECoreErrorNone ){
//                statusString = "register server failed";
//                undateStatus();
                Toast.makeText(MainActivity.this, "register server failed", Toast.LENGTH_SHORT).show();
            }
        }else {
//            undateStatus();
//            goTohome();
        }
        myApplication.setLoginState(true);
        return result;
    }
    int setUserInfo() {
        Environment.getExternalStorageDirectory();
        LogPath = Environment.getExternalStorageDirectory().getAbsolutePath() + '/';

//		String localIP = myApplication.getLocalIP(false);// ipv4
        String localIP = "0.0.0.0";
        int localPort = new Random().nextInt(4940) + 5060;
        UserInfo info = saveUserInfo();

        if(info.isAvailable())
        {
            mSipSdk.CreateCallManager(context.getApplicationContext());// step 1

            int result = mSipSdk.initialize(info.getTransType(),
                    PortSipEnumDefine.ENUM_LOG_LEVEL_NONE, LogPath,
                    Line.MAX_LINES, "PortSIP VoIP SDK for Android",
                    0,0);// step 2
            if (result != PortSipErrorcode.ECoreErrorNone) {
//                statusString = "init Sdk Failed";
                Toast.makeText(MainActivity.this, "init Sdk Failed", Toast.LENGTH_SHORT).show();
                return result;
            }

            int nSetKeyRet = mSipSdk.setLicenseKey(licenseKey);// step 3
            if (nSetKeyRet == PortSipErrorcode.ECoreTrialVersionLicenseKey) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("Prompt").setMessage("Short Coversation");
//                builder.setPositiveButton(android.R.string.ok,
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                builder.create().show();
                Toast.makeText(MainActivity.this, "Short Coversation", Toast.LENGTH_SHORT).show();
            } else if (nSetKeyRet == PortSipErrorcode.ECoreWrongLicenseKey) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Prompt").setMessage("This sample was built base on Release PortSIP VoIP SDK");
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                return -1;
            }

            result = mSipSdk.setUser(info.getUserName(),info.getUserDisplayName(),info.getAuthName(),info.getUserPassword(),
                    localIP, localPort, info.getUserdomain(),info.getSipServer(),info.getSipPort(),
                    info.getStunServer(), info.getStunPort(), null, 5060);// step 4

            if (result != PortSipErrorcode.ECoreErrorNone) {
//                statusString = "setUser resource failed";
                Toast.makeText(MainActivity.this, "setUser resource failed", Toast.LENGTH_SHORT).show();
                return result;
            }
        } else {
            return -1;
        }

        SettingConfig.setAVArguments(context,mSipSdk);
        return PortSipErrorcode.ECoreErrorNone;
    }

    private UserInfo saveUserInfo(){
        int port;
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(tv_un.getText().toString());
        userInfo.setUserPwd(tv_pw.getText().toString());
        userInfo.setSipServer(tv_sip.getText().toString());
        userInfo.setSipPort(Integer.valueOf(tv_port.getText().toString()));
        userInfo.setStunPort(Integer.valueOf(tv_port.getText().toString()));
        SettingConfig.setUserInfo(context, userInfo);
        return userInfo;
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void goTohome() {
        Intent i = new Intent(this, DashActivity.class);
        startActivity(i);
        this.finish();
    }
}
