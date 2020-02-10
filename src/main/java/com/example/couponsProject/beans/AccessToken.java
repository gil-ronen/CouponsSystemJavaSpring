package com.example.couponsProject.beans;

import com.example.couponsProject.service.components.loginManager.ClientType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Scope("prototype")
public class AccessToken {

    private Date expiredTime;
    private UUID uuid;
    private int userId;
    private ClientType clientType;


    /**
     * AccessToken full c'tor
     * @param userId int
     * @param clientType ClientType
     */
    public AccessToken(int userId, ClientType clientType)
    {
        expiredTime = new Date();
        uuid = UUID.randomUUID();
        this.userId = userId;
        this.clientType = clientType;
    }

    /**
     * get the access token expired time
     * @return Date
     */
    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
     * set the access token expired time
     * @param expiredTime Date
     */
    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * get the generated access token (uuid)
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }


    /**
     * get the user id
     * @return int
     */
    public int getUserId() {
        return userId;
    }


    /**
     * get the client type
     * @return ClientType
     */
    public ClientType getClientType() {
        return clientType;
    }


    /**
     * convert access token (uuid) to json for the client side
     * @return String
     */
    public String toJason() {
        return "{\"AccessToken\": \"" + uuid + "\"}";
    }

}
