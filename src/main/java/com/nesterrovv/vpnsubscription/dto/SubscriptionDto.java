package com.nesterrovv.vpnsubscription.dto;

import com.nesterrovv.vpnlibrary.Token;
import java.util.Date;
import java.util.Objects;

public class SubscriptionDto {

    private Date expirationDate;
    private boolean isActive;
    private Token token;

    public SubscriptionDto() {}

    public SubscriptionDto(Date expirationDate, boolean isActive, Token token) {
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscriptionDto that = (SubscriptionDto) o;
        return isActive == that.isActive
            && Objects.equals(expirationDate, that.expirationDate)
            && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expirationDate, isActive, token);
    }

}
