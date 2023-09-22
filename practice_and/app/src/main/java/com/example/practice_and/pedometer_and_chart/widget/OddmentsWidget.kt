package com.example.practice_and.pedometer_and_chart.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.pedometer_and_chart.StepActivity
import com.example.practice_and.pedometer_and_chart.RealmClass
import com.example.practice_and.toString
import java.time.LocalDateTime

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [OddmentsWidgetConfigureActivity]
 */
class OddmentsWidget : AppWidgetProvider() {

    // 현재시간으로부터 30초간격으로 알람 발생 => 시간 지정해서 15분 단위로 업데이트 시킬 수 있음. 어느정도가 적당할까?
    companion object{
        const val WIDGET_ALARM_INTERVAL = 30000
        var pendingIntent: PendingIntent? = null
        var alarmManager: AlarmManager? = null
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {

        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        if(pendingIntent != null){
            pendingIntent!!.cancel()
            pendingIntent = null
        }
        if(alarmManager != null){
            alarmManager!!.cancel(pendingIntent)
            alarmManager = null
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val action = intent!!.action
        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE == action){
            val nextTime = System.currentTimeMillis() + WIDGET_ALARM_INTERVAL

            if(pendingIntent != null){
                alarmManager!!.cancel(pendingIntent)
                alarmManager = null
            }
            pendingIntent = PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_IMMUTABLE)

            alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager!!.set(AlarmManager.RTC, nextTime, pendingIntent)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.oddments_widget)
    val step = RealmClass.getDateDidStep(LocalDateTime.now().toString(App.DATE_FORMAT))
    views.setTextViewText(R.id.tv_widget, "걸음수 : $step")

    // Click Widget, Open App
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    intent.component = ComponentName(context, StepActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.rl_widget, pendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}