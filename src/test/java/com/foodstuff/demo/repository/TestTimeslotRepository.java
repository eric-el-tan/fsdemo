package com.foodstuff.demo.repository;

import com.foodstuff.demo.dto.pojo.Timeslot;
import com.foodstuff.demo.dto.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTimeslotRepository {

    final static int NO_OF_TIMESLOT_PER_DAY = 24;
    final static int DAY_PER_WEEK = 7;

    private TimeslotRepository timeslotRepository;
    private List<LocalDate> dayOfNextWeek;

    @BeforeEach
    public void setup(){
        timeslotRepository = new TimeslotRepository();
        dayOfNextWeek = new ArrayList<>();
        for (int dayOfWeek = 1; dayOfWeek <= DAY_PER_WEEK; dayOfWeek++) {
            dayOfNextWeek.add(LocalDate.now().with(DayOfWeek.of(dayOfWeek)).plusWeeks(1));
        }
    }

    @AfterEach
    public void tearDown(){
        timeslotRepository.clear();
        timeslotRepository = null;
        dayOfNextWeek.clear();
        dayOfNextWeek = null;
    }

    @Test
    public void TestCreateTimeslot_Normal(){
        for (int i = 0; i < DAY_PER_WEEK; i++) {
            assertEquals(NO_OF_TIMESLOT_PER_DAY, timeslotRepository.getTimeslot(dayOfNextWeek.get(0)).size());
        }
    }

    @Test
    public void TestCreateTimeslot_Abnormal(){
        assertEquals(0, timeslotRepository.getTimeslot(LocalDate.now()).size());

    }

    @Test
    public void TestBookTimeslot_Abnormal_MoreThanEightShopper(){
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(2), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(3), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(4), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(5), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(6), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(7), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(8), dayOfNextWeek.get(0), 1));
        assertFalse(timeslotRepository.bookTimeslot(new User(9), dayOfNextWeek.get(0), 1));
    }

    @Test
    public void TestBookTimeslot_Abnormal_TimeslotNotExist() {
        assertFalse(timeslotRepository.bookTimeslot(new User(1), LocalDate.now(), 1));
    }

    @Test
    public void TestBookTimeslot_Abnormal_SameUserBookedTimeslotOfTheSameDay() {
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(0), 1));
        assertFalse(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(0), 2));
        assertTrue(timeslotRepository.bookTimeslot(new User(2), dayOfNextWeek.get(0), 2));
    }

    @Test
    public void TestReportAllBookedTimeslot_Normal(){
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(2), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(3), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(4), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(5), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(6), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(7), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(8), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(1), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(2), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(3), 1));
        printAllTimeslot(timeslotRepository.getBookedTimeslot(new User(1)));
    }

    @Test
    public void TestUtilizationReport_Normal(){
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(0), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(2), dayOfNextWeek.get(0), 2));
        assertTrue(timeslotRepository.bookTimeslot(new User(3), dayOfNextWeek.get(0), 2));
        assertTrue(timeslotRepository.bookTimeslot(new User(4), dayOfNextWeek.get(0), 3));
        assertTrue(timeslotRepository.bookTimeslot(new User(5), dayOfNextWeek.get(0), 3));
        assertTrue(timeslotRepository.bookTimeslot(new User(6), dayOfNextWeek.get(0), 3));
        assertTrue(timeslotRepository.bookTimeslot(new User(7), dayOfNextWeek.get(0), 4));
        assertTrue(timeslotRepository.bookTimeslot(new User(8), dayOfNextWeek.get(0), 4));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(1), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(2), 1));
        assertTrue(timeslotRepository.bookTimeslot(new User(1), dayOfNextWeek.get(3), 1));
        System.out.println(timeslotRepository.getUtlizationReport(dayOfNextWeek.get(0)));
    }

    private void printAllTimeslot(List<Timeslot> timeslots) {
        timeslots.stream().forEach(ts -> System.out.println(ts));
    }
}
