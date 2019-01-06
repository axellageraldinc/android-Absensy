package ppl.com.absensy.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ppl.com.absensy.model.Subject;

public class ClassReminderImpl implements ClassReminder {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat CLASS_DAY_FORMAT = new SimpleDateFormat("u"); // Day number of week (1 = Monday, ..., 7 = Sunday)
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat("mm");

    private AlarmManager alarmManager;
    private Context context;

    public ClassReminderImpl(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void cancelExistingReminder(Subject uneditedSubject) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.SUBJECT_ID_KEY, uneditedSubject.getId());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(alarmIntent);
    }

    @Override
    public void setReminder(Subject subject) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(CLASS_DAY_FORMAT.format(subject.getClassSchedule())) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(HOUR_FORMAT.format(subject.getClassSchedule())));
        calendar.set(Calendar.MINUTE, Integer.parseInt(MINUTE_FORMAT.format(subject.getClassSchedule())) - 30); // set the alarm 30 minutes advance

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.SUBJECT_ID_KEY, subject.getId());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 7, alarmIntent); // set alarm weekly at specified day and time
    }
}
