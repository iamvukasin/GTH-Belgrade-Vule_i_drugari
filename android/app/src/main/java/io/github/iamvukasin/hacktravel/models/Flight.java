package io.github.iamvukasin.hacktravel.models;

import java.io.Serializable;

public class Flight implements Serializable {

    private String iata_to;
    private String iata_from;
    private String flight_date_from;
    private String flight_time_from;
    private String flight_date_to;
    private String flight_time_to;
    private int stops_from;
    private int stops_to;
    private int price;
    private int price_with_pets;
    private int pets_not_flying;
    private String reason;
    private String link;

    public String getIata_to() {
        return iata_to;
    }

    public void setIata_to(String iata_to) {
        this.iata_to = iata_to;
    }

    public String getIata_from() {
        return iata_from;
    }

    public void setIata_from(String iata_from) {
        this.iata_from = iata_from;
    }

    public String getFlight_date_from() {
        return flight_date_from;
    }

    public void setFlight_date_from(String flight_date_from) {
        this.flight_date_from = flight_date_from;
    }

    public String getFlight_time_from() {
        return flight_time_from;
    }

    public void setFlight_time_from(String flight_time_from) {
        this.flight_time_from = flight_time_from;
    }

    public String getFlight_date_to() {
        return flight_date_to;
    }

    public void setFlight_date_to(String flight_date_to) {
        this.flight_date_to = flight_date_to;
    }

    public String getFlight_time_to() {
        return flight_time_to;
    }

    public void setFlight_time_to(String flight_time_to) {
        this.flight_time_to = flight_time_to;
    }

    public int getStops_from() {
        return stops_from;
    }

    public void setStops_from(int stops_from) {
        this.stops_from = stops_from;
    }

    public int getStops_to() {
        return stops_to;
    }

    public void setStops_to(int stops_to) {
        this.stops_to = stops_to;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice_with_pets() {
        return price_with_pets;
    }

    public void setPrice_with_pets(int price_with_pets) {
        this.price_with_pets = price_with_pets;
    }

    public int getPets_not_flying() {
        return pets_not_flying;
    }

    public void setPets_not_flying(int pets_not_flying) {
        this.pets_not_flying = pets_not_flying;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
