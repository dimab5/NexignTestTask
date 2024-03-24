package services;

/**
 * Interface for the UDR (Usage Data Report) service.
 */
public interface IUdrService {

    /**
     * Generates a report for all data and outputs it to the console.
     */
    void generateReport();

    /**
     * Generates a report for all data and outputs it to the console for the specified phone number.
     *
     * @param phoneNumber The phone number for which to output the information.
     */
    void generateReport(String phoneNumber);

    /**
     * Generates a report for all data and outputs it to the console for the specified phone number for the specified month.
     *
     * @param phoneNumber The phone number for which to output the report to the console.
     * @param month       The month for which to output the report to the console (1 - January, 2 - February, etc.).
     */
    void generateReport(String phoneNumber, Integer month);
}
