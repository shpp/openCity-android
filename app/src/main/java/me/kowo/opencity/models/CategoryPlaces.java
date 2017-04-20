package me.kowo.opencity.models;

import java.util.ArrayList;


public class CategoryPlaces {

    /**
     * success : true
     * places : [{"id":43,"name":"Комунальний заклад \u201eНавчально-виховне об\u2019єднання - Спеціальна загальноосвітня школа І-ІІ ступенів № 1 \u2013 дошкільний навчальний заклад Кіровоградської міської ради Кіровоградської області","address_id":42,"category_id":2,"comment":null,"city":"Кропивницький","street":"Волкова","number":"26а","geo_place_id":"5492715c6f426c941b5760bb57f3e8099124bb59","map_lat":"48.49807060000000","map_lng":"32.22245210000000","comment_adr":"вулиця Волкова, 26, Кропивницький, Кіровоградська область, Україна","acc_cnt":0},{"id":44,"name":"Комунальний заклад «Навчально-виховне об\u2019єднання № 3 \u201eСпеціальна загальноосвітня школа І-ІІІ ступенів \u2013 дитячий садок» Кіровоградської міської ради Кіровоградської області»","address_id":43,"category_id":2,"comment":null,"city":"Кропивницький","street":"Куропятникова","number":"19","geo_place_id":"b2cbfa6444919db00500e44499f55613c0f231ed","map_lat":"48.49730800000000","map_lng":"32.26092310000000","comment_adr":"вулиця Куроп'ятникова, 19, Кропивницький, Кіровоградська область, Україна","acc_cnt":0}]
     * access_cnt_all : 2
     */

    private boolean success;
    private int access_cnt_all;
    private ArrayList<Place> places;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getAccess_cnt_all() {
        return access_cnt_all;
    }

    public void setAccess_cnt_all(int access_cnt_all) {
        this.access_cnt_all = access_cnt_all;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

}
