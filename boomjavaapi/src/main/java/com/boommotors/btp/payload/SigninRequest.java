package com.boommotors.btp.payload;

/**
 *
 * @author Ramya
 */
public class SigninRequest {

    private String mobileoremail;
    private String password;
    private Integer source;
    private Integer twoFactorAuth = 0;
    private Integer otp = 0;
    private Integer page=1;
    private Integer count=0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileoremail() {
        return mobileoremail;
    }

    public void setMobileoremail(String mobileoremail) {
        this.mobileoremail = mobileoremail;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTwoFactorAuth() {
        return twoFactorAuth;
    }

    public void setTwoFactorAuth(Integer twoFactorAuth) {
        this.twoFactorAuth = twoFactorAuth;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Integer getOtp() {
        return otp;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer source) {
        this.page = page;
    }
}
