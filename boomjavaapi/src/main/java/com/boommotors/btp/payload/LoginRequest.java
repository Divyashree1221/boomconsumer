package com.boommotors.btp.payload;

/**
 *
 * @author divyashree
 */
public class LoginRequest {

    private Integer source;
     
    private String email;
    
    private String firstName;
    
    private String lastName;

    private String mobileNumber;
    
    private String socialId;
    
    private String provider;
    private Integer userRoleID;
    
    private String password;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getSource() {
        return source;
    }
   
    public void setSource(Integer source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
        
    public Integer getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(Integer userRoleID) {
        this.userRoleID = userRoleID;
    }
    
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
