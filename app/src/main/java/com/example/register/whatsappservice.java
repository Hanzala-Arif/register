package com.example.register;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class whatsappservice extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(getRootInActiveWindow()==null){
            return;
        }
        AccessibilityNodeInfoCompat nodeInfoCompat=AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());
        List<AccessibilityNodeInfoCompat> messagenodeinfo= nodeInfoCompat.findAccessibilityNodeInfosByViewId("com.whatspp:id/entry");
        if(messagenodeinfo==null || messagenodeinfo.isEmpty()){
            return;
        }
        AccessibilityNodeInfoCompat messagefeild=messagenodeinfo.get(0);
//        if(messagefeild ==null || messagefeild.getText().length()==0 || messagefeild.getText().toString().endsWith("")
//        {
//            return;
//        }
        List<AccessibilityNodeInfoCompat> sendmessage= nodeInfoCompat.findAccessibilityNodeInfosByViewId("com.whatspp:id/send");
        if(sendmessage==null || sendmessage.isEmpty()){
            return;
        }
        AccessibilityNodeInfoCompat sendmsg=sendmessage.get(0);
        if(!sendmsg.isVisibleToUser()){
            return;
        }
        sendmsg.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        try{
            Thread.sleep(2000);
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(2000);


        }catch(Exception e){}
    }

    @Override
    public void onInterrupt() {

    }
}
