package apps.veery.com.utilities;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Lakshan on 9/12/2016.
 */
public class Components {

    private static  ProgressDialog pDialog;

    public Components() {



    }

    public static void dissmissMassage(final SweetAlertDialog Dialog){
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                Dialog.dismiss();
            }
        }.start();

    }

    public SweetAlertDialog showLoadingSuccsessMassage(Context context){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.setCancelable(false);

        return pDialog;

    }

    public static void showErrorMassage(Context context,String title,String msg){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }

    public static  void showSucsessMassage(Context context,String title,String msg){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }


    public void openAleart(Context context,String title,String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static ProgressDialog getpDialog() {
        return pDialog;
    }

    public static void setpDialog(Context context) {
        Components.pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        showpDialog();
    }

    public static void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
//        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
//        int start = Math.min(initialValue, finalValue);
//        int end = Math.max(initialValue, finalValue);
//        int difference = Math.abs(finalValue - initialValue);
//        Handler handler = new Handler();
//        for (int count = start; count <= end; count++) {
//            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 100) * count;
//            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    textview.setText(finalCount + "");
//                }
//            }, time);
//        }
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, finalValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textview.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

}
