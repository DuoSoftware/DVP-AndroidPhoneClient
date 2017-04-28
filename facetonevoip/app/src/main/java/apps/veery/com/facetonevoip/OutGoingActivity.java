package apps.veery.com.facetonevoip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipSdk;

import apps.veery.com.util.Line;
import apps.veery.com.util.Session;

public class OutGoingActivity extends AppCompatActivity {
    ImageView btn_reject;
    PortSipSdk mPortSipSdk;
    MyApplication myApp;
    int _CurrentlyLine = 0;
    Line[] lines = null;
    ImageButton btn_mute,btn_speaker,btn_dialpad;
    LinearLayout lay_dialpad;

    Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine, btn_star, btn_zero, btn_hash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going);
        myApp = (MyApplication)getApplicationContext();
        myApp.setCurrentActivity(this);
        mPortSipSdk = myApp.getPortSIPSDK();
        lines = myApp.getLines();


        createcomponets();
        createClicks();
        noClick();

    }

    private void createClicks() {
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectCall();
            }
        });
        btn_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mute();
            }
        });
        btn_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerOn();
            }
        });

        btn_dialpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lay_dialpad.getVisibility() == View.VISIBLE) {
                    lay_dialpad.setVisibility(View.GONE);
                } else {
                    lay_dialpad.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void speakerOn() {
        if (btn_speaker.getTag().equals(R.drawable.mute)) {
            Log.d("TAG","Inside speaker on");
            btn_speaker.setImageResource(R.drawable.speaker);
            btn_speaker.setTag(R.drawable.speaker);
            mPortSipSdk.setLoudspeakerStatus(true);
        } else {
            Log.d("TAG","Inside speaker off");
            btn_speaker.setImageResource(R.drawable.mute);
            btn_speaker.setTag(R.drawable.mute);
            mPortSipSdk.setLoudspeakerStatus(false);
        }
    }

    private void mute() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (btn_mute.getTag().equals(R.drawable.ic_mic_white_24dp)) {
            Log.d("TAG","Inside speaker on");
            btn_mute.setImageResource(R.drawable.ic_mic_off_white_24dp);
            btn_mute.setTag(R.drawable.ic_mic_off_white_24dp);
            mPortSipSdk.muteSession(currentLine.getSessionId(), true, true, true, true);
        } else {
            Log.d("TAG","Inside speaker off");
            btn_mute.setImageResource(R.drawable.ic_mic_white_24dp);
            btn_mute.setTag(R.drawable.ic_mic_white_24dp);
            mPortSipSdk.muteSession(currentLine.getSessionId(), false, false, false, false);
        }
    }

    private void createcomponets() {
        btn_reject=(ImageView) findViewById(R.id.outgoing_btn_reject);
        btn_mute=(ImageButton) findViewById(R.id.outgoing_btn_mute);
        btn_mute.setTag(R.drawable.ic_mic_white_24dp);
        btn_speaker=(ImageButton) findViewById(R.id.outgoing_btn_speaker);
        btn_speaker.setTag(R.drawable.mute);
        btn_dialpad=(ImageButton) findViewById(R.id.outgoing_btn_dial);
        lay_dialpad=(LinearLayout)findViewById(R.id.lay_outgoing_dialpad);
        lay_dialpad.setVisibility(View.GONE);

        btn_one = (Button) findViewById(R.id.grid_one);
        btn_two = (Button) findViewById(R.id.grid_two);
        btn_three = (Button) findViewById(R.id.grid_three);
        btn_four = (Button) findViewById(R.id.grid_four);
        btn_five = (Button) findViewById(R.id.grid_five);
        btn_six = (Button) findViewById(R.id.grid_six);
        btn_seven = (Button) findViewById(R.id.grid_seven);
        btn_eight = (Button) findViewById(R.id.grid_eight);
        btn_nine = (Button) findViewById(R.id.grid_nine);
        btn_star = (Button) findViewById(R.id.grid_star);
        btn_zero = (Button) findViewById(R.id.grid_zero);
        btn_hash = (Button) findViewById(R.id.grid_hash);
    }

    private void senddtmfMethod(int i){
        final Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        int dtmfResponse = mPortSipSdk.sendDtmf(currentLine.getSessionId(), PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, i, 160, true);
    }
    private void noClick() {

        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(1);


            }
        });
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(2);
            }
        });
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(3);
            }
        });
        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(4);
            }
        });
        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(5);
            }
        });
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(6);
            }
        });
        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(7);
            }
        });
        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(8);
            }
        });
        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(9);
            }
        });
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(10);
            }
        });
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(0);
            }
        });
        btn_hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senddtmfMethod(11);
            }
        });
    }

    private void rejectCall() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        Toast.makeText(this, "Rejected call", Toast.LENGTH_SHORT).show();
        if (currentLine.getRecvCallState()) {
            mPortSipSdk.rejectCall(currentLine.getSessionId(), 486);
            currentLine.reset();
//            showTips(lines[_CurrentlyLine].getLineName() + ": Rejected call");
            this.finish();
        }else{
            Toast.makeText(this, "Rejected ELSE call", Toast.LENGTH_SHORT).show();
            mPortSipSdk.rejectCall(currentLine.getSessionId(), 486);
            currentLine.reset();
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        mPortSipSdk.rejectCall(currentLine.getSessionId(), 486);
        currentLine.reset();
        this.finish();
    }
}
