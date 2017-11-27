package me.kowo.cityguide.providers;

import java.util.ArrayList;

import me.kowo.cityguide.models.Establishment;


public class DataProvider {
    private ArrayList<Establishment> establishmentsList;

    public ArrayList<Establishment> getEstablishmentsList() {
        if (establishmentsList == null) {
            fillEstablishmentsListWithTestData();
        }
        return establishmentsList;
    }

    private void fillEstablishmentsListWithTestData() {
        establishmentsList = new ArrayList<>();
        establishmentsList.add(createEstablishment("#3", "Aviaciina 64"));
        establishmentsList.add(createEstablishment("#19", "Volkova 24"));
        establishmentsList.add(createEstablishment("#25", "Levanevskogo 2B"));
    }

    private Establishment createEstablishment(String name, String address) {
        Establishment establishment = new Establishment();
        establishment.name = name;
        establishment.address = address;
        return establishment;
    }
}
