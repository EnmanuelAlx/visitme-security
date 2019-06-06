package gil.mota.visitme.visitmesecurity.services;

/**
 * Created by mota on 18/4/2018.
 */

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONException;

import gil.mota.visitme.visitmesecurity.managers.NotificationManager;

public class NotificationExtender extends NotificationExtenderService {

    public NotificationExtender() {
        super();
        NotificationManager.getInstance().addServiceInstance(this);
    }

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

        try {
            System.out.println("ON NEW NOTIFICATION:"+receivedResult.payload.additionalData.toString());
            return NotificationManager.getInstance().onNewNotification(receivedResult.payload.additionalData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void showNotification(OverrideSettings settings) {
        displayNotification(settings);
    }
}