package com.boommotors.btp.payload;

/**
 *
 * @author rjanumpally
 */
public class JwtAuthenticationConsumerResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private Boolean isFirstTimeUser;
    private String userId;
    private String imei;
    private String mac;
    private String clusterID;

    public JwtAuthenticationConsumerResponse(String accessToken, Boolean isFirstTimeUser, String userId, String imei,
            String mac, String clusterID) {
        this.accessToken = accessToken;
        this.isFirstTimeUser = isFirstTimeUser;
        this.userId = userId;
        this.imei = imei;
        this.mac = mac;
        this.clusterID = clusterID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getIsFirstTimeUser() {
        return isFirstTimeUser;
    }

    public void setIsFirstTimeUser(Boolean isFirstTimeUser) {
        this.isFirstTimeUser = isFirstTimeUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }
    
    
}
