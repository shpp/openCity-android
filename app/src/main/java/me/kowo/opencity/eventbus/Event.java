package me.kowo.opencity.eventbus;

import java.util.ArrayList;
import java.util.List;


public class Event {
    EventMessage eventMessage;
    public String query = "";

    public Object getTransferObject() {
        return transferObject;
    }

    public Event setTransferObject(Object transferObject) {
        this.transferObject = transferObject;
        return this;
    }

    public List<Object> getTransferArray() {
        return transferArray;
    }

    public Event setTransferArray(ArrayList<Object> transferArray) {
        this.transferArray = transferArray;
        return this;
    }

    public Object transferObject;
    public List<Object> transferArray;

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public Event setEventMessage(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
        return this;
    }
}
