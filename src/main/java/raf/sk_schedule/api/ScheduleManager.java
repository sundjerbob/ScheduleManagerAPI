package raf.sk_schedule.api;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_mapper.RepetitiveScheduleMapper;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;
import raf.sk_schedule.api.Constants.WeekDay;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * This interface represents the scheduling component API.
 * The universal date format of this time scheduling api is yyyy-mm-dd for example: "2023-10-14"
 * The universal time format of this time scheduling api is HH:MM for example: "04:20"
 * This is defined in Constants.java class
 * Universal time related data formats are required and used by ScheduleSlot class.
 */
public interface ScheduleManager {


    /**
     * Initialize the schedule with specific starting and ending dates.
     *
     * @param startDate The date when the schedule's accounting period starts.
     * @param endDate   The date when the schedule's accounting period ends.
     */
    void initialize(String startDate, String endDate);

    /**
     * Initialize the schedule with specific starting and ending dates.
     *
     * @param startDate The date when the schedule's accounting period start.
     * @param endDate   The date when the schedule's accounting period ends.
     */
    void initialize(Date startDate, Date endDate);

    void setExcludedWeekDays(WeekDay... days);

    void setExcludedWeekDays(String... days);

    /**
     * Load room properties from a CSV file specified by csvPath and add them to the schedule.
     * Returns the number of rows successfully imported from the CSV file.
     *
     * @param csvPath The path to the CSV file containing room properties.
     * @return The number of rows successfully imported.
     * @throws ScheduleIOException If there is an issue reading the CSV file.
     */
    public int loadRoomsSCV(String csvPath) throws ScheduleIOException;


    /**
     * Load schedule slots from a CSV file specified by csvPath and add them to the schedule.
     * Returns the number of rows successfully imported from the CSV file.
     *
     * @param csvPath The path to the CSV file containing schedule slots.
     * @return The number of rows successfully imported.
     * @throws ScheduleIOException If there is an issue reading the CSV file.
     */
    public int loadScheduleSCV(String csvPath) throws ScheduleIOException;

    /**
     * Checks whether the schedule contains a room with the specified name (roomName).
     *
     * @param roomName The name of the room you are looking for.
     * @return True if a room with the specified name exists in the schedule, false otherwise.
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
     * Get the room properties with the specified name.
     *
     * @param name The name of the room to retrieve.
     * @return The room properties with the specified name, or null if not found.
     */
    public RoomProperties getRoom(String name);

    /**
     * Delete a room from the schedule.
     *
     * @param name The name of the room to be deleted.
     * @throws ScheduleException is thrown if there was no room with name equal to name param value.
     */
    void deleteRoom(String name) throws ScheduleException;


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
     * Schedules a repetitive time slot based on the provided parameters. The slot can be set by
     * passing a duration, an endTime, or both. If one of them is null, the other will be used to
     * schedule the slot. If both are null, the slot cannot be scheduled.
     *
     * @param startTime               The start time for the slot in "HH:mm" format.
     * @param duration                The duration of the slot in minutes. It is not necessary if the endTime is not null.
     * @param endTime                 The end time for the slot in "HH:mm" format. It is not necessary if the duration is defined positive number.
     * @param weekDay                 The day of the week on which the occurrence of the first ScheduleSlot will be mapped. If weekDay is null, the first slot will be mapped at the schedulingIntervalStart date.
     * @param recurrencePeriod        The number of days defining the interval between repetitions. After the first slot is mapped, every subsequent slot is scheduled at the end of passing recurrencePeriod number of days.
     * @param schedulingIntervalStart The start date for scheduling the slot (in "yyyy-MM-dd" format).
     * @param schedulingIntervalEnd   The end date for scheduling the slot (in "yyyy-MM-dd" format).
     * @return True if the slot was successfully scheduled, false otherwise.
     */
    boolean scheduleRepetitiveTimeSlot(String startTime,
                                       long duration,
                                       String endTime,
                                       WeekDay weekDay,
                                       int recurrencePeriod,
                                       String schedulingIntervalStart,
                                       String schedulingIntervalEnd);


    boolean scheduleRepetitiveTimeSlot(RepetitiveScheduleMapper recurrenceInterval);


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
     * Export the schedule data to a CSV file within specified date bounds.
     *
     * @param filePath   The path to an existing file or path on witch a new file will be created in case it doesn't exist already.
     * @param lowerBound The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    int exportScheduleCSV(String filePath, String lowerBound, String upperBound);

    /**
     * Export the filtered schedule data to a CSV file within specified date bounds.
     *
     * @param filePath       The path to an existing file or path on witch a new file will be created in case it doesn't exist already.
     * @param searchCriteria The search criteria for filtering.
     */
    int exportFilteredScheduleCSV(String filePath, SearchCriteria searchCriteria);

    /**
     * Export the schedule data to a JSON file within specified date bounds.
     *
     * @param filePath   The path to an existing file or path on witch a new file will be created in case it doesn't exist already.
     * @param lowerBound The lower date bound for export. If null, it will be the earliest date in the schedule.
     * @param upperBound The upper date bound for export. If null, it will be the latest date in the schedule.
     */
    int exportScheduleJSON(String filePath, String lowerBound, String upperBound);

    /**
     * Export the filtered schedule data to a JSON file within specified date bounds.
     *
     * @param filePath       The path to an existing file or path on witch a new file will be created in case it doesn't exist already.
     * @param searchCriteria The search criteria for filtering.
     */
    int exportFilteredScheduleJSON(String filePath, SearchCriteria searchCriteria);

    /**
     * Returns a list of all room properties available in the schedule.
     *
     * @return A list of all room properties.
     */
    public List<RoomProperties> getAllRooms();

    /**
     * Gets the schedule slots within the specified date bounds (lowerBound and upperBound).
     * If lowerBound is null, it uses the earliest date in the schedule.
     * If upperBound is null, it uses the latest date in the schedule.
     *
     * @param lowerBoundDate The lower date bound for the schedule.
     * @param upperBoundDate The upper date bound for the schedule.
     * @return A list of schedule slots within the specified date bounds.
     */
    List<ScheduleSlot> getSchedule(String lowerBoundDate, String upperBoundDate);

    /**
     * Gets the schedule slots within the specified date bounds (lowerBound and upperBound).
     * If lowerBound is null, it uses the earliest date in the schedule.
     * If upperBound is null, it uses the latest date in the schedule.
     *
     * @param lowerBoundDate The lower date bound for the schedule.
     * @param upperBoundDate The upper date bound for the schedule.
     * @return A list of schedule slots within the specified date bounds.
     */
    public List<ScheduleSlot> getSchedule(Date lowerBoundDate, Date upperBoundDate);

    /**
     * Returns a list of all schedule slots in the schedule.
     *
     * @return A list of all schedule slots.
     */
    List<ScheduleSlot> getWholeSchedule();


}
