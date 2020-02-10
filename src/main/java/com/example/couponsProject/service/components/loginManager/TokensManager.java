package com.example.couponsProject.service.components.loginManager;

import com.example.couponsProject.beans.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Service
public class TokensManager {

    private Collection<AccessToken> accessTokens;

    /**
     * empty c'tor for TokensManager
     */
    public TokensManager() {
        accessTokens = new ArrayList<AccessToken>();
    }

    /**
     * check if the access token is exist in the list, if so, the token validity should be extended by 30 minutes
     * @param accessToken String
     * @param userId int
     * @param clientType ClientType
     * @return boolean
     */
    public boolean isAccessTokenExist(String accessToken, int userId, ClientType clientType)
    {
        if(accessTokens!=null)
        {
            for (AccessToken xst : accessTokens) {
                if(xst!=null) {
                    if ((xst.getUuid().toString().equals(accessToken)) && (xst.getUserId()==userId) && (xst.getClientType().equals(clientType))) {
                        //Each time we come here, the Token validity should be extended by 30 minutes, therefor we call to addNewAccessToken
                        LocalDateTime nowPlus30Minutes =LocalDateTime.now().plusMinutes(30);
                        xst.setExpiredTime(Date.from( nowPlus30Minutes.atZone( ZoneId.systemDefault()).toInstant()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * check if the access token that got from the client side, is exist in the list for authentication
     * @param accessToken String
     * @return boolean
     */
    public boolean isClientRegistered(String accessToken)
    {
        if(accessTokens!=null)
        {
            for (AccessToken xst : accessTokens) {
                if(xst!=null) {
                    if (xst.getUuid().toString().equals(accessToken)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * add new access token to the authorized users list
     * @param accessToken AccessToken
     */
    public void addNewAccessToken(AccessToken accessToken)
    {
        if(!isAccessTokenExist(accessToken.getUuid().toString(),accessToken.getUserId(),accessToken.getClientType()))
        {
            LocalDateTime nowPlus30Minutes =LocalDateTime.now().plusMinutes(30);
            accessToken.setExpiredTime(Date.from( nowPlus30Minutes.atZone( ZoneId.systemDefault()).toInstant()));
            this.accessTokens.add(accessToken);
        }
    }

    /**
     * remove access token from the list. we came here if the user logged out or if the access token time expired.
     * @param accessToken AccessToken
     */
    public void removeAccessToken(AccessToken accessToken)
    {
        this.accessTokens.remove(accessToken);
    }

    /**
     * When the user logged out from the system, then remove his access token
     * @param accessToken String
     * @return boolean
     */
    public boolean logoutAccessToken(String accessToken)
    {
        if(accessTokens!=null)
        {
            for (AccessToken xst : accessTokens) {
                if(xst!=null) {
                    if (xst.getUuid().toString().equals(accessToken)) {
                        removeAccessToken(xst);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * When there was no server-side activity in the last 30 minutes, then remove all the time out access tokens
     */
    public void removeAllExpiredAccessTokens()
    {
        if(accessTokens!=null)
        {
            Date date = new Date();
            for(AccessToken accessToken:accessTokens)
            {
                if(accessToken!=null) {
                    if (date.after(accessToken.getExpiredTime()))
                        removeAccessToken(accessToken);
                }
            }
        }
    }

}


