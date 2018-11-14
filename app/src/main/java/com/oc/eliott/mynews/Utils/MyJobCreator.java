package com.oc.eliott.mynews.Utils;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class MyJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch(tag){
            case DisplayNotificationJob.TAG:
                return new DisplayNotificationJob();
            default:
                return null;

        }
    }
}
