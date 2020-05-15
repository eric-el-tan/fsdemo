package com.foodstuff.demo.repository;

import com.foodstuff.demo.dto.pojo.Timeslot;
import com.foodstuff.demo.dto.pojo.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TimeslotRepository {
    private static final int MAX_THRESHOLD_SHOPPER = 8;

    List<Timeslot> timeslots = new ArrayList<>();

    public TimeslotRepository() {
        for (int dayOfWeek = 1; dayOfWeek < 8; dayOfWeek++) {
            for (int hour = 0; hour < 24; hour++) {
                timeslots.add(new Timeslot(LocalDate.now().with(DayOfWeek.of(dayOfWeek)).plusWeeks(1), hour));
            }
        }
    }

    public List<Timeslot> getTimeslot(LocalDate day) {
        return timeslots.stream().filter(ts -> ts.getDay().equals(day)).collect(Collectors.toList());
    }

    public Optional<Timeslot> getTimeslot(LocalDate day, Integer hour) {
        return timeslots.stream().filter(ts -> ts.getDay().equals(day) && ts.getHour().equals(hour)).findAny();
    }

    public boolean bookTimeslot(User user, LocalDate day, int hour){
        Optional<Timeslot> timeslotOptional = getTimeslot(day, hour);
        if (!timeslotOptional.isPresent())
            return false;
        Optional<Timeslot> bookedAlreadyOptional = getTimeslot(day).stream().filter(ts -> ts.getShopper().contains(user)).findAny();
        if (bookedAlreadyOptional.isPresent())
            return false;
        Timeslot timeslot = timeslotOptional.get();
        if (timeslot.getShopper().size() >= MAX_THRESHOLD_SHOPPER){
            return false;
        }
        return timeslot.getShopper().add(user);
    }

    public List<Timeslot> getBookedTimeslot(User user){
        return timeslots.stream().filter(ts -> ts.getShopper().contains(user)).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getUtlizationReport(LocalDate day){
        return timeslots.stream().filter(ts -> ts.getDay().equals(day)).collect(Collectors.toMap(Timeslot::getHour, ts->ts.getShopper().size()));
    }

    public void clear() {
        timeslots = null;
    }
}
