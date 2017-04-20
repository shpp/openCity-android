package me.kowo.opencity.models;

public class Place {
    /**
     * id : 43
     * name : Комунальний заклад „Навчально-виховне об’єднання - Спеціальна загальноосвітня школа І-ІІ ступенів № 1 – дошкільний навчальний заклад Кіровоградської міської ради Кіровоградської області
     * address_id : 42
     * category_id : 2
     * comment : null
     * city : Кропивницький
     * street : Волкова
     * number : 26а
     * geo_place_id : 5492715c6f426c941b5760bb57f3e8099124bb59
     * map_lat : 48.49807060000000
     * map_lng : 32.22245210000000
     * comment_adr : вулиця Волкова, 26, Кропивницький, Кіровоградська область, Україна
     * acc_cnt : 0
     */

    private int id;
    private String name;
    private int address_id;
    private int category_id;
    private Object comment;
    private String city;
    private String street;
    private String number;
    private String geo_place_id;
    private String map_lat;
    private String map_lng;
    private String comment_adr;
    private String short_name;

    public String getShortName() {
        return short_name;
    }

    public void setShortName(String short_name) {
        this.short_name = short_name;
    }

    private int acc_cnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGeo_place_id() {
        return geo_place_id;
    }

    public void setGeo_place_id(String geo_place_id) {
        this.geo_place_id = geo_place_id;
    }

    public String getMap_lat() {
        return map_lat;
    }

    public void setMap_lat(String map_lat) {
        this.map_lat = map_lat;
    }

    public String getMap_lng() {
        return map_lng;
    }

    public void setMap_lng(String map_lng) {
        this.map_lng = map_lng;
    }

    public String getComment_adr() {
        return comment_adr;
    }

    public void setComment_adr(String comment_adr) {
        this.comment_adr = comment_adr;
    }

    public int getAcc_cnt() {
        return acc_cnt;
    }

    public void setAcc_cnt(int acc_cnt) {
        this.acc_cnt = acc_cnt;
    }
}
