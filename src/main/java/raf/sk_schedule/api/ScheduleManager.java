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

    public static String dateFormat = "yyyy-MM-dd";

    public static String dateTimeFormat = "yyyy-MM-dd HH:mm";


    /**
     * Initialize the schedule.+
     */
    void initialize();

    /**
     * Initialize the schedule with specific starting and ending date.
     *@param startDate when schedule account starts
     *@param endDate when schedule account ends
     */
    void initialize(Date startDate, Date endDate);

    /**
     * Add a room to the schedule.
     *
     * @param properties The properties of the room (e.g.,name , capacity, equipment...).
     */
    boolean addRoom( RoomProperties properties);
    public boolean updateRoom(String name, RoomProperties newProp);

    public boolean deleteRoom(String name);

        /**
         * Add a time slot to the schedule.
         *
         * @param timeSlot The time slot to add.
         */
    boolean addTimeSlot(ScheduleSlot timeSlot) throws ParseException;

    /**
     * Delete a time slot from the schedule.
     *
     * @param timeSlot The time slot to delete.
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
     * @param startDate The start date for the range.
     * @param endDate   The end date for the range.
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

    List<ScheduleSlot> getWholeSchedule();
}
