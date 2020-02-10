package com.example.couponsProject.service.components.jobs;

import com.example.couponsProject.service.components.loginManager.TokensManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeoutAccessTokensCleanerTask {
    @Autowired
    private TokensManager tokensManager;

    private boolean jobEnabled = false;

    /**
     * start the cleanTimeoutAccessTokensTask job
     */
    public void startJob()
    {
        this.jobEnabled = true;
    }

    /**
     * stop the cleanTimeoutAccessTokensTask job
     */
    public void stopJob()
    {
        this.jobEnabled = false;
    }

    /**
     * delete all the timeout access tokens from the TokensManager list every minute
     * job is invoked every minute
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void cleanTimeoutAccessTokensTask(){
        if(this.jobEnabled)
        {
            // CLEAN TIME OUT ACCESS TOKENS:
            tokensManager.removeAllExpiredAccessTokens();
        }
    }
}
