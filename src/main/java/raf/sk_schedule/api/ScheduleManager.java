package raf.sk_schedule.api;

import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.RoomProperties;
import raf.sk_schedule.model.ScheduleSlot;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * This interface represents the scheduling component API.
 */
public interface ScheduleManager {


    /**
     * Initialize the schedule with specific starting and ending dates.
     *
     * @param startDate The date when the schedule's accounting period starts.
     * @param endDate   The date when the schedule's accounting period ends.
     */
    void initialize(Date startDate, Date endDate);

    /**
     * Add a room to the schedule.
     *
     * @param properties The properties of the room (e.g., name, capacity, equipment).
     * @return True if the room was successfully added; false otherwise.
     */
    boolean addRoom(RoomProperties properties);

    /**
     * Update the properties of an existing room in the schedule.
     *
     * @param name    The name of the room to be updated.
     * @param newProp The updated properties of the room.
     * @return True if the room was successfully updated; false otherwise.
     */
    boolean updateRoom(String name, RoomProperties newProp);

    /**
     * Delete a room from the schedule.
     *
     * @param name The name of the room to be deleted.
     * @return True if the room was successfully deleted; false otherwise.
     */
    boolean deleteRoom(String name);

    /**
     * Add a time slot to the schedule.
     *
     * @param timeSlot The time slot to be added.
     * @return True if the time slot was successfully added; false otherwise.
     * @throws ParseException If there is an issue parsing the time slot.
     */
    boolean scheduleTimeSlot(ScheduleSlot timeSlot) throws ParseException;

    /**
     * Books a repetitive time slot that recurs every week on the same weekday and at the same time, within a specified time interval.
     *
     * @param dayOfTheWeek The day of the week for which the time slot should recur (e.g., "Monday", "Tuesday").
     * @param startTime    The start time of the repetitive time slot in the format "HH:mm" (e.g., "08:30").
     * @param endTime      The end time of the repetitive time slot in the format "HH:mm" (e.g., "10:00").
     * @param duration     The duration of each individual time slot in minutes.
     * @param startingFrom The date from which the repetitive time slots should start recurring.
     * @param until        The date until which the repetitive time slots should continue recurring.
     *
     * @return True if the repetitive time slots were successfully added; false otherwise.
     */
    boolean scheduleRepetitiveSlot(String dayOfTheWeek, String startTime, String endTime, long duration, Date startingFrom, Date until);

    /**
     * Delete a time slot from the schedule.
     *
     * @param timeSlot The time slot to be deleted.
     * @return True if the time slot was successfully deleted; false otherwise.
     */
    boolean deleteTimeSlot(ScheduleSlot timeSlot);

    /**
     * Move a time slot to a new time and location.
     *
     * @param oldTimeSlot The original time slot.
     * @param newTimeSlot The new time slot.
     */
    void moveTimeSlot(ScheduleSlot oldTimeSlot, ScheduleSlot newTimeSlot);

    /**
     * Check if a specific time slot is available.
     *
     * @param timeSlot The time slot to check for availability.
     * @return True if the time slot is available; false otherwise.
     */
    boolean isTimeSlotAvailable(ScheduleSlot timeSlot);

    /**
     * Get a list of free time slots within a specified date range.
     *
     * @param startDate The start date for the range. If null, it will be the earliest date in the schedule.
     * @param endDate   The end date for the range. If null, it will be the latest date in the schedule.
     * @return A list of free time slots.
     */
    List<ScheduleSlot> getFreeTimeSlots(Date startDate, Date endDate);

    /**
     * Search for time slots based on specific criteria.
     *
     * @param criteria The search criteria (e.g., room, day, equipment).
     * @return A list of matching time slots.
     */
    List<ScheduleSlot> searchTimeSlots(SearchCriteria criteria);

    /**
     * Load schedule data from a file.
     *
     * @param filePath The path to the file to load.
     */
    void loadScheduleFromFile(String filePath);

    /**
     * Save the schedule data to a file.
     *
     * @param filePath The path to the file to save.
     */
    void saveScheduleToFile(String filePath);

    /**
     * Export the schedule data to a CSV file within specified date bounds.
     *
     * @param lowerBound The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    void exportScheduleCSV(Date lowerBound, Date upperBound);

    /**
     * Export the schedule data to a JSON file within specified date bounds.
     *
     * @param lowerBound The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    void exportScheduleJSON(Date lowerBound, Date upperBound);

    /**
     * Export the filtered schedule data to a CSV file within specified date bounds.
     *
     * @param searchCriteria The search criteria for filtering.
     * @param lowerBound     The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound     The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    void exportFilteredScheduleCSV(SearchCriteria searchCriteria, Date lowerBound, Date upperBound);

    /**
     * Export the filtered schedule data to a JSON file within specified date bounds.
     *
     * @param searchCriteria The search criteria for filtering.
     * @param lowerBound     The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound     The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    void exportFilteredScheduleJSON(SearchCriteria searchCriteria, Date lowerBound, Date upperBound);

    /**
     * Get the schedule within specified date bounds.
     *
     * @param lowerBound The lower date bound. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound. If null, it will be the latest date in the schedule.
     * @return A list of schedule slots within the specified date bounds.
     */
    List<ScheduleSlot> getSchedule(Date lowerBound, Date upperBound);

    /**
     * Get the entire schedule.
     *
     * @return A list of all schedule slots.
     */
    List<ScheduleSlot> getWholeSchedule();


}
