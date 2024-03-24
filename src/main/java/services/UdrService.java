package services;

import models.CallDuration;
import models.UdrModel;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UdrService implements IUdrService {
    private static final int CDR_COUNT = 12;
    private static String CDR_FILES_PARH = "generatedCDR/";
    private static String UDR_FILES_PATH = "reports/";

    @Override
    public void generateReport() {
        File directory = new File(CDR_FILES_PARH);
        File[] files = directory.listFiles();

        for (File file : files) {
            String filePath = CDR_FILES_PARH + file.getName();
            Integer monthNumber = extractMonthFromFileName(file.getName());

            Map<String, UdrModel> udrModels = createReport(filePath);
            writeJsonToFile(monthNumber, udrModels);

            System.out.println(monthNumber + " month");
            printReportTable(udrModels.entrySet().stream());
        }
    }

    @Override
    public void generateReport(String phoneNumber) {
        File directory = new File(CDR_FILES_PARH);
        File[] files = directory.listFiles();

        for (File file : files) {
            String filePath = CDR_FILES_PARH + file.getName();
            Integer monthNumber = extractMonthFromFileName(file.getName());

            Map<String, UdrModel> udrModels = createReport(filePath);
            writeJsonToFile(monthNumber, udrModels);

            System.out.println(monthNumber + " month");
            printReportTable(udrModels.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(phoneNumber)));
        }
    }

    @Override
    public void generateReport(String phoneNumber, Integer month) {
        File directory = new File(CDR_FILES_PARH);
        File[] files = directory.listFiles();

        for (File file : files) {
            String filePath = CDR_FILES_PARH + file.getName();
            Integer monthNumber = extractMonthFromFileName(file.getName());

            Map<String, UdrModel> udrModels = createReport(filePath);
            writeJsonToFile(monthNumber, udrModels);

            if (monthNumber == month) {
                System.out.println(monthNumber + " month");
                printReportTable(udrModels.entrySet().stream()
                        .filter(entry -> entry.getKey().equals(phoneNumber)));
            }
        }
    }

    private void writeJsonToFile(Integer monthNumber, Map<String, UdrModel> udrModels) {
        for (String phone : udrModels.keySet()) {
            String reportFileName = UDR_FILES_PATH + phone + "_" + monthNumber + ".json";
            String json = udrModels.get(phone).toJson();

            try (FileWriter writer = new FileWriter(reportFileName)) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, UdrModel> createReport(String filePath) {
        Set<String> abonentPhoneNumbers = addAbonents(filePath);

        Map<String, UdrModel> udrModels = new HashMap<>();
        for (String phone : abonentPhoneNumbers) {
            UdrModel udr = new UdrModel();
            udr.setPhoneNumber(phone);

            udrModels.put(phone, udr);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String phoneNumber = parts[1].trim();
                String callType = parts[0].trim();
                Duration callDuration = Duration.ofSeconds(Long.parseLong(parts[3]) - Long.parseLong(parts[2]));

                UdrModel udr = udrModels.get(phoneNumber);
                if (callType.equals("01")) {
                    CallDuration outgoingCall = udr.getOutcomingCall();
                    if (outgoingCall == null) {
                        udr.setOutcomingCall(new CallDuration(callDuration));
                    } else {
                        Duration totalDuration = parseDuration(outgoingCall.getTotalTime()).plus(callDuration);
                        udr.setOutcomingCall(new CallDuration(totalDuration));
                    }
                } else if (callType.equals("02")) {
                    CallDuration incomingCall = udr.getIncomingCall();
                    if (incomingCall == null) {
                        udr.setIncomingCall(new CallDuration(callDuration));
                    } else {
                        Duration totalDuration = parseDuration(incomingCall.getTotalTime()).plus(callDuration);
                        udr.setIncomingCall(new CallDuration(totalDuration));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return udrModels;
    }

    private Set<String> addAbonents(String filePath) {
        Set<String> uniqueAbonents = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String phoneNumber = parts[1].trim();
                uniqueAbonents.add(phoneNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uniqueAbonents;
    }

    private Integer extractMonthFromFileName(String fileName) {
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return -1;
        }
    }

    private Duration parseDuration(String durationString) {
        String[] parts = durationString.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    private void printReportTable(Stream<Map.Entry<String, UdrModel>> udrModels) {
        System.out.println("+------------------+------------------+------------------+------------------+");
        System.out.println("|    MSISDN        | Incoming Call    | Outgoing Call    | Total Call       |");
        System.out.println("+------------------+------------------+------------------+------------------+");

        udrModels.forEach(entry -> {
            String msisdn = entry.getKey();
            UdrModel udr = entry.getValue();
            CallDuration incomingCall = udr.getIncomingCall();
            CallDuration outgoingCall = udr.getOutcomingCall();

            System.out.printf("| %-16s | %-16s | %-16s | %-16s |%n",
                    msisdn,
                    incomingCall != null ? incomingCall.getTotalTime() : "-",
                    outgoingCall != null ? outgoingCall.getTotalTime() : "-",
                    getTotalDuration(udr));
        });

        System.out.println("+------------------+------------------+------------------+------------------+");
    }

    private String getTotalDuration(UdrModel udr) {
        Duration incomingDuration = udr.getIncomingCall() != null ? parseDuration(udr.getIncomingCall().getTotalTime()) : Duration.ZERO;
        Duration outgoingDuration = udr.getOutcomingCall() != null ? parseDuration(udr.getOutcomingCall().getTotalTime()) : Duration.ZERO;
        Duration totalDuration = incomingDuration.plus(outgoingDuration);

        long hours = totalDuration.toHours();
        long minutes = totalDuration.minusHours(hours).toMinutes();
        long seconds = totalDuration.minusHours(hours).minusMinutes(minutes).getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}