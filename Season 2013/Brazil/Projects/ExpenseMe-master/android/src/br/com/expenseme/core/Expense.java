package br.com.expenseme.core;

import java.io.Serializable;

public class Expense implements Serializable {

    private static final long serialVersionUID = -6641292855569752036L;

    private String description;
    private String venue;
    private float price;
    private String objectId;
    private String dateTimeParam;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDateTimeParams() {
        return dateTimeParam;
    }

    public void setDateTimeParam(String dateTimeParam) {
        this.dateTimeParam = dateTimeParam;
    }
}
