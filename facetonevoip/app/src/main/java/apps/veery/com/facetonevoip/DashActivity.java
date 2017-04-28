package apps.veery.com.facetonevoip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.portsip.PortSipSdk;

import apps.veery.com.util.Line;
import apps.veery.com.util.Session;

public class DashActivity extends AppCompatActivity {
    ImageView btn_call;
    Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine, btn_star, btn_zero, btn_hash;
    EditText et_no;
    ImageButton ib_delete;
    PortSipSdk mPortSipSdk;
    MyApplication myApp;
    int _CurrentlyLine = 0;
    Line[] lines = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        btn_call=(ImageView) findViewById(R.id.dash_btn_call);

        et_no=(EditText) findViewById(R.id.dash_et_no);
        ib_delete=(ImageButton)findViewById(R.id.dash_ib_delete);
        ib_delete.setImageResource(R.drawable.ic_contacts_white_24dp);
        ib_delete.setTag(R.drawable.ic_contacts_white_24dp);

        btn_one=(Button)findViewById(R.id.grid_one);
        btn_two=(Button)findViewById(R.id.grid_two);
        btn_three=(Button)findViewById(R.id.grid_three);
        btn_four=(Button)findViewById(R.id.grid_four);
        btn_five=(Button)findViewById(R.id.grid_five);
        btn_six=(Button)findViewById(R.id.grid_six);
        btn_seven=(Button)findViewById(R.id.grid_seven);
        btn_eight=(Button)findViewById(R.id.grid_eight);
        btn_nine=(Button)findViewById(R.id.grid_nine);
        btn_star=(Button)findViewById(R.id.grid_star);
        btn_zero=(Button)findViewById(R.id.grid_zero);
        btn_hash=(Button)findViewById(R.id.grid_hash);
        myApp = (MyApplication)getApplicationContext();
        mPortSipSdk = myApp.getPortSIPSDK();
        lines = myApp.getLines();
        Log.d("TAG FIRST",et_no.getText().toString());
        et_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedittext();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        deleteClick();
        noClick();
        callClick();
    }

    private void callClick() {
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumberOutGoing();
            }
        });
    }

    private void checkNumberOutGoing(){
        String callTo = et_no.getText().toString();
        if (callTo == null || callTo.length() <= 0) {
//            showTips("The phone number is empty.");
            Toast.makeText(DashActivity.this, "The phone number is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (currentLine.getSessionState()
                || currentLine.getRecvCallState()) {
//            showTips("Current line is busy now, please switch a line.");
            Toast.makeText(DashActivity.this, "Current line is busy now, please switch a line.", Toast.LENGTH_SHORT).show();
        }
        // Ensure that we have been added one audio codec at least
        if (mPortSipSdk.isAudioCodecEmpty()) {
//            showTips("Audio Codec Empty,add audio codec at first");
            Toast.makeText(DashActivity.this, "Audio Codec Empty,add audio codec at first", Toast.LENGTH_SHORT).show();
        }
        // Usually for 3PCC need to make call without SDP
        long sessionId = mPortSipSdk.call(callTo, true, true);
        if (sessionId <= 0) {
//            showTips("Call failure");
            Toast.makeText(DashActivity.this, "Call failure", Toast.LENGTH_SHORT).show();
            return;
        }
        currentLine.setSessionId(sessionId);
        currentLine.setSessionState(true);
        currentLine.setVideoState(true);
        myApp.setCurrentLine(lines[_CurrentlyLine]);
//        showTips(lines[_CurrentlyLine].getLineName() + ": Calling...");
        Toast.makeText(DashActivity.this, "Calling", Toast.LENGTH_SHORT).show();
        goToOutGoing();
        myApp.updateSessionVideo();
    }

    private void goToOutGoing() {
        Intent i=new Intent(this,OutGoingActivity.class);
        startActivity(i);
    }

    private void deleteClick(){

        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer)ib_delete.getTag()==R.drawable.ic_contacts_white_24dp){
                    goToContacts();
                }else if((Integer)ib_delete.getTag()==R.drawable.ic_backspace_white_24dp){
                    et_no.setText(removeLastCharacter(et_no.getText().toString()));
                }
            }
        });
    }
    public String removeLastCharacter(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
    private void goToContacts(){
        Intent i=new Intent(this,ContactsActivity.class);
        startActivity(i);
    }

    private void noClick(){
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("1");
            }
        });
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("2");
            }
        });
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("3");
            }
        });
        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("4");
            }
        });
        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("5");
            }
        });
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("6");
            }
        });
        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("7");
            }
        });
        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("8");
            }
        });
        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("9");
            }
        });
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("*");
            }
        });
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("0");
            }
        });
        btn_hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("#");
            }
        });
    }

    private void getAppendTetx(String s){
        String first_string=et_no.getText().toString();
        first_string+=s;
        et_no.setText(first_string);
    }

    private void checkedittext(){
        if(TextUtils.isEmpty(et_no.getText())){
            Log.d("TAG","Not Empty");
            ib_delete.setImageResource(R.drawable.ic_contacts_white_24dp);
            ib_delete.setTag(R.drawable.ic_contacts_white_24dp);
        }
        else if(null!= et_no.getText()||!et_no.getText().toString().equals("")){
            Log.d("TAG","Not Empty");
            ib_delete.setImageResource(R.drawable.ic_backspace_white_24dp);
            ib_delete.setTag(R.drawable.ic_backspace_white_24dp);
        }
    }
}
