package raf.sk_schedule.api;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.RoomProperties;
import raf.sk_schedule.model.ScheduleSlot;

import java.io.IOException;
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
    void initialize(String startDate, String endDate);

    public int loadRoomsSCV(String csvPath) throws IOException;

    public int loadScheduleSCV(String csvPath) throws IOException;

    /**
     * Checks weather the schedule contains the room with specified name.
     *
     * @param roomName name of the room you are looking for.
     */


    public boolean hasRoom(String roomName);

    /**
     * Add a room to the schedule.
     *
     * @param properties The properties of the room (e.g., name, capacity, equipment).
     */
    void addRoom(RoomProperties properties);

    /**
     * Update the properties of an existing room in the schedule.
     *
     * @param name    The name of the room to be updated.
     * @param newProp The updated properties of the room.
     * @throws ScheduleException is thrown if there was no room with name equal to name param value.
     */
    void updateRoom(String name, RoomProperties newProp);

    /**
     * Delete a room from the schedule.
     *
     * @param name The name of the room to be deleted.
     * @throws ScheduleException is thrown if there was no room with name equal to name param value.
     */
    void deleteRoom(String name) throws ScheduleException;

    public RoomProperties getRoom(String name);

    /**
     * Add a time slot to the schedule.
     *
     * @param timeSlot The time slot to be added.
     * @return True if the time slot was successfully added; false otherwise.
     * @throws ParseException    If there is an issue parsing the time slot.
     * @throws ScheduleException if a slot is already occupied
     */
    boolean scheduleTimeSlot(ScheduleSlot timeSlot) throws ParseException, ScheduleException;


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
    List<ScheduleSlot> getFreeTimeSlots(String startDate, String endDate);

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
    void exportScheduleCSV(String lowerBound, String upperBound);

    /**
     * Export the schedule data to a JSON file within specified date bounds.
     *
     * @param lowerBound The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    void exportScheduleJSON(String lowerBound, String upperBound);

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


    public List<RoomProperties> getAllRooms();

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
