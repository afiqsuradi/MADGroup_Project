package com.example.madgroup_project.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.madgroup_project.R;
import com.example.madgroup_project.data.ItemConditions;
import com.example.madgroup_project.data.models.Item;
import com.example.madgroup_project.data.viewmodel.ItemViewModel;
import java.util.List;

public class ItemReminderWorker extends Worker {

    private static final String CHANNEL_ID = "item_reminder_channel";
    private ItemViewModel itemViewModel;

    public ItemReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        itemViewModel = new ViewModelProvider.AndroidViewModelFactory(
                (getApplicationContext() instanceof android.app.Application ? (android.app.Application) getApplicationContext() : null )
        ).create(ItemViewModel.class);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        List<Item> items = queryMaintenanceAndBrokenItems();

        if(items.isEmpty()){
            return Result.success();
        }

        String summary = createNotificationSummary(items);
        sendNotification(context, summary);
        return Result.success();
    }

    private List<Item> queryMaintenanceAndBrokenItems() {
        return  itemViewModel.getAllItemsByConditions(ItemConditions.MAINTENANCE, ItemConditions.BROKEN);
    }

    private String createNotificationSummary(List<Item> items) {
        StringBuilder builder = new StringBuilder();
        int brokenCount = 0;
        int maintenanceCount = 0;

        for (Item item : items) {
            if(item.getCondition() == ItemConditions.BROKEN){
                brokenCount++;
            } else if(item.getCondition() == ItemConditions.MAINTENANCE){
                maintenanceCount++;
            }
        }

        if(brokenCount > 0){
            builder.append(brokenCount).append(" items are broken. ");
        }
        if(maintenanceCount > 0){
            builder.append(maintenanceCount).append(" items need maintenance.");
        }
        return builder.toString();
    }


    private void sendNotification(Context context, String message) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Item Status Reminder")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }

    private void createNotificationChannel(Context context) {
        CharSequence name = "Item Reminder";
        String description = "Notification to remind user of broken and maintenance items.";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}