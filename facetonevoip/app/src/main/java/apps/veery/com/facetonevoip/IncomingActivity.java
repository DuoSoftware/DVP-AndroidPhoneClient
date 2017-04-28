package apps.veery.com.facetonevoip;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipSdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import apps.veery.com.util.Line;
import apps.veery.com.util.Session;
public class IncomingActivity extends AppCompatActivity {
    LinearLayout lay_call, lay_speak, lay_dialpad;
    ImageButton ib_delete, btn_answer, btn_reject, btn_answer_reject, btn_speaker, btn_mute, btn_dialpad;
    ImageView btn_call_forward, btn_call_attended_forward, btn_conference;
    TextView tv_timer, tv_caller;
    PortSipSdk mPortSipSdk;
    MyApplication myApp;
    int _CurrentlyLine = 0;
    Line[] lines = null;
    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    Session currentLineGlobal;
    EditText et_no;
    Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine, btn_star, btn_zero, btn_hash;

    private void createComponents() {
        lay_call = (LinearLayout) findViewById(R.id.outgoing_income_layout);
        lay_speak = (LinearLayout) findViewById(R.id.outgoing_speaker_layout);
        lay_dialpad = (LinearLayout) findViewById(R.id.incoming_lay_dialpad);
        btn_answer = (ImageButton) findViewById(R.id.incoming_btn_answer);
        btn_reject = (ImageButton) findViewById(R.id.incoming_btn_reject);
        btn_answer_reject = (ImageButton) findViewById(R.id.incoming_btn_answer_reject);
        btn_mute = (ImageButton) findViewById(R.id.incoming_btn_mute);
        btn_speaker = (ImageButton) findViewById(R.id.incoming_btn_speaker);
        btn_dialpad = (ImageButton) findViewById(R.id.incoming_btn_dial);
        btn_call_forward = (ImageView) findViewById(R.id.income_btn_call_forward);
        btn_call_attended_forward = (ImageView) findViewById(R.id.income_btn_call_attended_forward);
        btn_conference = (ImageView) findViewById(R.id.income_btn_call_conference);
        tv_timer = (TextView) findViewById(R.id.incoming_timer);
        tv_timer.setTextSize(20);
        tv_caller = (TextView) findViewById(R.id.incoming_caller);
        myApp = (MyApplication) getApplicationContext();
        mPortSipSdk = myApp.getPortSIPSDK();
        lines = myApp.getLines();
        ib_delete = (ImageButton) findViewById(R.id.incoming_ib_delete);
        ib_delete.setImageResource(R.drawable.ic_contacts_white_24dp);
        ib_delete.setTag(R.drawable.ic_contacts_white_24dp);
        if (null != MyApplication.getCaller()) {
            Toast.makeText(this, "Intent " + MyApplication.getCaller(), Toast.LENGTH_SHORT).show();
            tv_caller.setText(MyApplication.getCaller());
        }

        et_no = (EditText) findViewById(R.id.incoming_et_no);
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

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int buttonWidth = width / 3;

        btn_speaker.setMaxWidth(buttonWidth);
        btn_dialpad.setMaxWidth(buttonWidth);
        btn_mute.setMaxWidth(buttonWidth);
        btn_conference.setMaxHeight(buttonWidth);

        btn_speaker.setImageResource(R.drawable.ic_speaker_phone_white_24dp);
        btn_speaker.setTag(R.drawable.ic_speaker_phone_white_24dp);
        btn_mute.setImageResource(R.drawable.ic_mic_white_24dp);
        btn_mute.setTag(R.drawable.ic_mic_white_24dp);
    }

    private void senddtmfMethod(int i) {
        final Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        int dtmfResponse = mPortSipSdk.sendDtmf(currentLine.getSessionId(), PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, i, 160, true);
    }

    private void noClick() {

        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("1");
                senddtmfMethod(1);
            }
        });
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("2");
                senddtmfMethod(2);
            }
        });
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("3");
                senddtmfMethod(3);
            }
        });
        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("4");
                senddtmfMethod(4);
            }
        });
        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("5");
                senddtmfMethod(5);
            }
        });
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("6");
                senddtmfMethod(6);
            }
        });
        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("7");
                senddtmfMethod(7);
            }
        });
        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("8");
                senddtmfMethod(8);
            }
        });
        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("9");
                senddtmfMethod(9);
            }
        });
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("*");
                senddtmfMethod(10);
            }
        });
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("0");
                senddtmfMethod(0);
            }
        });
        btn_hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppendTetx("#");
                senddtmfMethod(11);
            }
        });
    }

    private void getAppendTetx(String s) {
        String first_string = et_no.getText().toString();
        first_string += s;
        et_no.setText(first_string);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.setIntent(intent);
        Toast.makeText(this, "Intent " + getIntent().getExtras().getString("caller2"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);
        createComponents();
        btnclicks();
        noClick();
    }


    private void btnclicks() {
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerCall();
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectCall();
            }
        });
        btn_answer_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Reject Call");
                rejectedAnswerCall();
//                rejectCall();
            }
        });
        btn_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerOn();
            }
        });

        btn_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mute();
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
//                lay_dialpad.setVisibility(View.VISIBLE);
            }
        });

        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer) ib_delete.getTag() == R.drawable.ic_contacts_white_24dp) {
                    goToContacts();
                } else if ((Integer) ib_delete.getTag() == R.drawable.ic_backspace_white_24dp) {
                    et_no.setText(removeLastCharacter(et_no.getText().toString()));
                }
            }
        });

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

        btn_call_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_no.getText())) {
                    Toast.makeText(IncomingActivity.this, "The transfer number is empty", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(et_no.getText())) {
                    transfer(et_no.getText().toString());
                }
            }
        });
        btn_call_attended_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_no.getText())) {
                    Toast.makeText(IncomingActivity.this, "The transfer number is empty", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(et_no.getText())) {
                    attendTransfer(et_no.getText().toString());
                }
            }
        });

        btn_conference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_no.getText())) {
                    Toast.makeText(IncomingActivity.this, "The transfer number is empty", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(et_no.getText())) {
                    conference(et_no.getText().toString());
                }
            }
        });

    }

    private void transfer(String referTo) {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        int rt = mPortSipSdk.refer(currentLine.getSessionId(), referTo);
//        mPortSipSdk.sendDtmf(currentLine.getSessionId(),)

//        int sum = Integer.valueOf(referTo+"#");// 0~9
//        mPortSipSdk.sendDtmf(currentLine.getSessionId(),
//                PortSipEnumDefine.ENUM_DTMF_MOTHOD_RFC2833, 10,
//                160, true);
//        mPortSipSdk.sendDtmf(currentLine.getSessionId(),
//                PortSipEnumDefine.ENUM_DTMF_MOTHOD_RFC2833, sum,
//                160, true);
        if (rt != 0) {
            Toast.makeText(IncomingActivity.this, "failed to Transfer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(IncomingActivity.this, "failed to Transfer", Toast.LENGTH_SHORT).show();
        }
    }

    private void conference(String referTo) {
        final Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        final String sum = referTo;
        Log.d("SUM", String.valueOf(sum));
        int threedtmf = mPortSipSdk.sendDtmf(currentLine.getSessionId(),
                PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, 0,
                160, true);

        final int[] newGuess = new int[sum.length()];
        for (int i = 0; i < sum.length(); i++) {
            newGuess[i] = sum.charAt(i) - '0';
        }
        Handler handler = new Handler();
        for (final int d : newGuess) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("EXTENSION", String.valueOf(d));
                    int dtmfResponse = mPortSipSdk.sendDtmf(currentLine.getSessionId(), PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, d, 160, true);
                    Log.d("RESPONSE", String.valueOf(dtmfResponse));
                }
            }, 1);
        }
    }

    private void attendTransfer(String referTo) {
        final Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        final String sum = referTo + "11";
        Log.d("SUM", String.valueOf(sum));
        int hashdtmf = mPortSipSdk.sendDtmf(currentLine.getSessionId(),
                PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, 10,
                160, true);
        int threedtmf = mPortSipSdk.sendDtmf(currentLine.getSessionId(),
                PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, 3,
                160, true);

        final int[] newGuess = new int[sum.length()];
        for (int i = 0; i < sum.length(); i++) {
            newGuess[i] = sum.charAt(i) - '0';
        }
        Handler handler = new Handler();
        for (final int d : newGuess) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("EXTENSION", String.valueOf(d));
                    int dtmfResponse = mPortSipSdk.sendDtmf(currentLine.getSessionId(), PortSipEnumDefine.ENUM_DTMF_MOTHOD_INFO, d, 160, true);
                    Log.d("RESPONSE", String.valueOf(dtmfResponse));
                }
            }, 1);
        }
    }

    private void hold() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (!currentLine.getSessionState()
                || currentLine.getHoldState()) {
            return;
        }
        int rt = mPortSipSdk.hold(currentLine.getSessionId());
        if (rt != 0) {
            Toast.makeText(this, "hold operation failed.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentLine.setHoldState(true);
    }

    private void unhold() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (!currentLine.getSessionState()
                || !currentLine.getHoldState()) {
            return;
        }
        int rt = mPortSipSdk.unHold(currentLine.getSessionId());
        if (rt != 0) {
            currentLine.setHoldState(false);
            Toast.makeText(this, lines[_CurrentlyLine].getLineName() + ": Un-Hold Failure.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentLine.setHoldState(false);
        Toast.makeText(this, lines[_CurrentlyLine].getLineName() + ": Un-Hold", Toast.LENGTH_SHORT).show();
    }

    private void checkedittext() {
        if (TextUtils.isEmpty(et_no.getText())) {
            ib_delete.setImageResource(R.drawable.ic_contacts_white_24dp);
            ib_delete.setTag(R.drawable.ic_contacts_white_24dp);
        } else if (null != et_no.getText() || !et_no.getText().toString().equals("")) {
            Log.d("TAG", "Not Empty");
            ib_delete.setImageResource(R.drawable.ic_backspace_white_24dp);
            ib_delete.setTag(R.drawable.ic_backspace_white_24dp);
        }
    }

    public String removeLastCharacter(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private void goToContacts() {
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
    }

    private void mute() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (btn_mute.getTag().equals(R.drawable.ic_mic_white_24dp)) {
            Log.d("TAG", "Inside speaker on");
            btn_mute.setImageResource(R.drawable.ic_mic_off_white_24dp);
            btn_mute.setTag(R.drawable.ic_mic_off_white_24dp);
            mPortSipSdk.muteSession(currentLine.getSessionId(), true, true, true, true);
        } else {
            Log.d("TAG", "Inside speaker off");
            btn_mute.setImageResource(R.drawable.ic_mic_white_24dp);
            btn_mute.setTag(R.drawable.ic_mic_white_24dp);
            mPortSipSdk.muteSession(currentLine.getSessionId(), false, false, false, false);
        }
    }

    private void speakerOn() {
        if (btn_speaker.getTag().equals(R.drawable.ic_speaker_phone_white_24dp)) {
            Log.d("TAG", "Inside speaker on");
            btn_speaker.setImageResource(R.drawable.ic_smartphone_white_24dp);
            btn_speaker.setTag(R.drawable.ic_smartphone_white_24dp);
            mPortSipSdk.setLoudspeakerStatus(true);
        } else {
            Log.d("TAG", "Inside speaker off");
            btn_speaker.setImageResource(R.drawable.ic_speaker_phone_white_24dp);
            btn_speaker.setTag(R.drawable.ic_speaker_phone_white_24dp);
            mPortSipSdk.setLoudspeakerStatus(false);
        }
    }

    private void rejectedAnswerCall() {
//        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
//        if (currentLine.getRecvCallState()) {
//            mPortSipSdk.rejectCall(currentLine.getSessionId(), 486);
//            currentLine.reset();
//
//            Toast.makeText(this, "Call rejected", Toast.LENGTH_SHORT).show();
//            return;
//        }


        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        int reject = mPortSipSdk.rejectCall(currentLineGlobal.getSessionId(), 480);
        currentLine.reset();
        if (reject == 0) {
            Toast.makeText(this, "Call rejected", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Toast.makeText(this, "Error Code" + reject, Toast.LENGTH_SHORT).show();
        }
//            showTips(lines[_CurrentlyLine].getLineName() + ": Rejected call");

    }

    private void rejectCall() {
        Session currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        if (currentLine.getRecvCallState()) {
            int reject = mPortSipSdk.rejectCall(currentLine.getSessionId(), 486);
            currentLine.reset();
            if (reject == 0) {
                Toast.makeText(this, "Call rejected", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                Toast.makeText(this, "Error Code" + reject, Toast.LENGTH_SHORT).show();
            }
//            showTips(lines[_CurrentlyLine].getLineName() + ": Rejected call");
        }
    }

    private void answerCall() {
        Line currentLine = myApp.findSessionByIndex(_CurrentlyLine);
        currentLineGlobal = myApp.findSessionByIndex(_CurrentlyLine);
        if (!currentLine.getRecvCallState()) {
//            showTips("No incoming call on current line, please switch a line.");
            Toast.makeText(IncomingActivity.this, "No incoming call on current line, please switch a line.", Toast.LENGTH_SHORT).show();

        }
        int answered = myApp.answerSessionCall(currentLine, true);
        if (answered == 0) {
            currentLine.setRecvCallState(true);
        } else {
            Toast.makeText(this, "Call answer Fail", Toast.LENGTH_SHORT).show();
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        lay_call.setVisibility(View.GONE);
        lay_speak.setVisibility(View.VISIBLE);
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            tv_timer.setText("" + mins + ":" + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };
}
