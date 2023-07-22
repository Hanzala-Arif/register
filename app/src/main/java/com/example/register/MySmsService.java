package com.example.register;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MySmsService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.example.register.action.FOO";
    private static final String ACTION_WHATSAPP = "com.example.register.action.BAZ";

    // TODO: Rename parameters
    private static final String MESSAGE= "com.example.register.extra.PARAM1";
    private static final String Number = "com.example.register.extra.PARAM2";

    public MySmsService() {
        super("MySmsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, String Message, String NumbeR) {
        Intent intent = new Intent(context, MySmsService.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(MESSAGE,Message);
        intent.putExtra(Number,NumbeR);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionWHATSAPP(Context context, String Message, String NumbeR) {
        Intent intent = new Intent(context, MySmsService.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE,Message);
        intent.putExtra(Number,NumbeR);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String NumbeR = intent.getStringExtra(Number);
                handleActionSMS(message, NumbeR);
            } else if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String NumbeR = intent.getStringExtra(Number);
                handleActionWHATSAPP(message,NumbeR);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(String MESSAGE, String Number) {

        for(int i=0;i<=1;i++) {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(Number, null, MESSAGE, null, null);
                sendBroadCastMessage("msg:"+i+""+Number);
                // Toast.makeText(MainActivity.this, "Send", Toast.LENGTH_SHORT).show();
//                        sendEmail(Order, content, Email);


            } catch (Exception e) {
                //Toast.makeText(MainActivity.this, "Not Send", Toast.LENGTH_SHORT).show();
            }
        }
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String param1, String Number) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sendBroadCastMessage(String MESSAGE){
        Intent intent=new Intent("my.own.broadcast");
        intent.putExtra("msg",MESSAGE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}