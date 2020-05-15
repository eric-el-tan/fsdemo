package com.foodstuff.demo.dto.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Timeslot {
    LocalDate day;
    Integer hour;
    List<User> shopper;

    public Timeslot(LocalDate day, Integer hour) {
        this.day = day;
        this.hour = hour;
        this.shopper = new ArrayList<>();
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public List<User> getShopper() {
        return shopper;
    }

    public void setShopper(List<User> shopper) {
        this.shopper = shopper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(day, timeslot.day) &&
                Objects.equals(hour, timeslot.hour) &&
                Objects.equals(shopper, timeslot.shopper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour, shopper);
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "day=" + day +
                ", hour=" + hour +
                ", shopper=" + shopper +
                '}';
    }
}
