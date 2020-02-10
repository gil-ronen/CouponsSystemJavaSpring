package com.example.couponsProject.service.components.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CouponCleanerDailyTask {

    @Autowired
    private ExpiredCouponsCleanerService cleanerService;

    private boolean jobEnabled = false;

    /**
     * start the cleanExpiredCouponsDailyTask job
     */
    public void startJob()
    {
        this.jobEnabled = true;
    }

    /**
     * stop the cleanExpiredCouponsDailyTask job
     */
    public void stopJob()
    {
        this.jobEnabled = false;
    }

    /**
     * daily task that delete all the expired coupons from the repository
     * job is invoked every day at 24:00:01 (cron pattern: second, minute, hour, day, month, weekday)
     */
    @Scheduled(cron = "1 0 0 * * *")
    public void cleanExpiredCouponsDailyTask(){
        if(this.jobEnabled)
        {
            // CLEAN EXPIRED COUPONS:
            cleanerService.clean();
        }
    }
}
