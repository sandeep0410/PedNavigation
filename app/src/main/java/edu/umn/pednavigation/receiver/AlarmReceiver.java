package edu.umn.pednavigation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.umn.pednavigation.LogUtils;
import edu.umn.pednavigation.Util;
import edu.umn.pednavigation.db.DBUtils;
import edu.umn.pednavigation.db.DbDownloader;


/**
 * Created by Sandeep on 10/9/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null && intent.getAction().equals("edu.umn.pednavigation.DOWNLOAD.START"))
            LogUtils.log("Received Broadcast");
        if (Util.isOnline(context)) {
            DbDownloader downloadPeds = new DbDownloader(context, DBUtils.BLEPN_TABLE);
            DbDownloader downloadFlags = new DbDownloader(context, DBUtils.BLEFLAG_TABLE);
            DbDownloader downloadTags = new DbDownloader(context, DBUtils.BLETAG_TABLE);
            DbDownloader downloadPhases = new DbDownloader(context, DBUtils.BLEPHASE_TABLE);
            //***** Download all the tables and update local database****//
            downloadFlags.start();
            downloadPeds.start();
            downloadTags.start();
            downloadPhases.start();
        }

    }


}