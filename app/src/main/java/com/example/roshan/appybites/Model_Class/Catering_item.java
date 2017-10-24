package com.example.roshan.appybites.Model_Class;

/**
 * Created by roshan on 3/18/17.
 */

public class Catering_item {

    private String product_id;
    private String p_name;
    private String p_desc;
    private String p_price;
    private String chef_image;
    private String p_image;
    private String p_special;
    private String p_rating;

    public String getMenu_cate() {
        return menu_cate;
    }

    public String setMenu_cate(String menu_cate) {
        this.menu_cate = menu_cate;
        return menu_cate;
    }

    private String menu_cate;

    public String getP_ingredients() {
        return p_ingredients;
    }

    public void setP_ingredients(String p_ingredients) {
        this.p_ingredients = p_ingredients;
    }

    public String getP_day() {
        return p_day;
    }

    public String setP_day(String p_day) {
        this.p_day = p_day;
        return p_day;
    }

    private String p_ingredients;
    private String p_day;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getP_name() {
        return p_name;
    }

    public String setP_name(String p_name) {
        this.p_name = p_name;
        return p_name;
    }

    public String getP_desc() {
        return p_desc;
    }

    public void setP_desc(String p_desc) {
        this.p_desc = p_desc;
    }

    public String getP_price() {
        return p_price;
    }

    public String setP_price(String p_price) {
        this.p_price = p_price;
        return p_price;
    }

    public String getChef_image() {
        return chef_image;
    }

    public String setChef_image(String chef_image) {
        this.chef_image = chef_image;
        return chef_image;
    }

    public String getP_image() {
        return p_image;
    }

    public String setP_image(String p_image) {
        this.p_image = p_image;
        return p_image;
    }

    public String getP_special() {
        return p_special;
    }

    public void setP_special(String p_special) {
        this.p_special = p_special;
    }

    public String getP_rating() {
        return p_rating;
    }

    public void setP_rating(String p_rating) {
        this.p_rating = p_rating;
    }
}

