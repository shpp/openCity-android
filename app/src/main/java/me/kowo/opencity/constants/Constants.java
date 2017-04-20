package me.kowo.opencity.constants;


public class Constants {
    public static final String BASE_URL = "http://opencity.shpp.me";
    public static final String GET_PLACES_URL = BASE_URL + "/api/v1/getplaces?";
    public static final String STREET_PREFIX = "вул.";
    public static final int MIN_LEN_TO_QUERY = 2;
    public static final String PREF_TAG = "pref_tag";
    public static final String LINK_PHONE = "phone";
    public static final String LINK_EMAIL = "email";
    public static final String LINK_WEB = "www";
    public static final String PARAM = "parameters";
    public static final String ACCESS = "accessibilities";

    public static final String FAKE_PARAMETERS = "{\n" +
            "1: {\n" +
            "\t\"name\": \"Керівник\",\n" +
            "\t\"comment\": \"Керівник огрганізації\",\n" +
            "\t\"type\": \"person\",\n" +
            "\t\"icon\": \"/img/icons/head.png\"\n" +
            "\t},\n" +
            "2: {\n" +
            "\t\"name\": \"Телефон\",\n" +
            "\t\"comment\": \"Номер телефна організації\",\n" +
            "\t\"type\": \"phone\",\n" +
            "\t\"icon\": \"/img/icons/phone.png\"\n" +
            "\t},\n" +
            "3: {\n" +
            "\t\"name\": \"E-mail\",\n" +
            "\t\"comment\": \"Адреса електронної пошти організації\",\n" +
            "\t\"type\": \"email\",\n" +
            "\t\"icon\": \"/img/icons/email.png\"\n" +
            "\t},\n" +
            "4: {\n" +
            "\t\"name\": \"Сайт\",\n" +
            "\t\"comment\": \"Адреса сайту організації\",\n" +
            "\t\"type\": \"www\",\n" +
            "\t\"icon\": \"/img/icons/website.png\"\n" +
            "\t}\n" +
            "}";

}
