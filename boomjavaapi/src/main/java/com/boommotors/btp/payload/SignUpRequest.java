package com.boommotors.btp.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author divyashree
 */
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;  
    private String mobileNumber;
    private String provider;
    private Integer userRoleId;
    private Integer source;
     private Integer page;
   
    

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    
     public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getSource() {
        return source;
    }
    
    public void setSource(Integer source){
         this.source = source;
    }
    
     public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer source){
         this.page = page;
    }
     
}
