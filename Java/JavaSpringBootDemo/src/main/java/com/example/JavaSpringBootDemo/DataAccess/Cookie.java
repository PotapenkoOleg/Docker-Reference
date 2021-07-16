package com.example.JavaSpringBootDemo.DataAccess;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema = "public", name = "cookie")
public class Cookie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookie_id")
    private Long cookieID;

    @Column(name = "cookie_name")
    private String cookieName;

    @Column(name = "cookie_recipe_url")
    private String cookieRecipeUrl;

    @Column(name = "cookie_sku")
    private String cookieSku;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_cost", columnDefinition = "NUMERIC")
    private BigDecimal unitCost;

    // Constructors

    public Cookie() {
    }

    public Cookie(String cookieName, String cookieRecipeUrl, String cookieSku, int quantity, BigDecimal unitCost) {
        this.cookieName = cookieName;
        this.cookieRecipeUrl = cookieRecipeUrl;
        this.cookieSku = cookieSku;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    // endregion

    // region Getters and Setters

    public Long getCookieID() {
        return cookieID;
    }

    public void setCookieID(Long cookieID) {
        this.cookieID = cookieID;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieRecipeUrl() {
        return cookieRecipeUrl;
    }

    public void setCookieRecipeUrl(String cookieRecipeUrl) {
        this.cookieRecipeUrl = cookieRecipeUrl;
    }

    public String getCookieSku() {
        return cookieSku;
    }

    public void setCookieSku(String cookieSku) {
        this.cookieSku = cookieSku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    // endregion

    // region toString

    @Override
    public String toString() {
        return "Cookie{" +
                "cookieID=" + cookieID +
                ", cookieName='" + cookieName + '\'' +
                ", cookieRecipeUrl='" + cookieRecipeUrl + '\'' +
                ", cookieSku='" + cookieSku + '\'' +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                '}';
    }

    // endregion
}
