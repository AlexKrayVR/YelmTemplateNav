package yelm.io.template.notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import yelm.io.template.stuff.Logging;

public class FcmMessageService extends FirebaseMessagingService {

    private static final int NOTIFY_ID = 101;

    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        NotificationChannelCreator.createNotificationChannel(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Logging.logDebug("remoteMessage.getData(): " + remoteMessage.getData().toString());
        if (remoteMessage.getNotification() != null) {
            Logging.logDebug("Message Notification Body: " + remoteMessage.getNotification().getBody());
            Logging.logDebug("Message Notification Title: " + remoteMessage.getNotification().getTitle());
        }
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage remoteMessage) {
//        Intent i = new Intent(this, LoaderActivity.class);
//        String data = remoteMessage.getData().toString();
//        try {
//            JSONObject jsonObj = new JSONObject(data);
//            String chat = jsonObj.getString("name");
//            Logging.logDebug("name: " + jsonObj.getString("name"));
//            if (chat.equals("chat")) {
//                if (Constants.customerInChat) {
//                    return;
//                }else {
//                    i = new Intent(this, ChatActivity.class);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        i.putExtra("data", data);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 333, i, PendingIntent.FLAG_UPDATE_CURRENT);
////FLAG_ONE_SHOT       FLAG_UPDATE_CURRENT
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.raccoon_icon);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
//                .setAutoCancel(true)
//                .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setLargeIcon(bitmap)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setSmallIcon(R.drawable.ic_notify)
//                .setColor(getResources().getColor(R.color.mainThemeColor))
//                .setContentIntent(pendingIntent)
//                .setSound(defaultSoundUri);
//
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        manager.notify(NOTIFY_ID, builder.build());
    }
}