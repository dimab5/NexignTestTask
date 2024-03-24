package services;

import models.Abonent;
import storage.repositories.AbonentRepository;
import storage.repositories.IAbonentRepository;
import storage.repositories.ITransactionRepository;
import storage.repositories.TransactionRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CdrService implements ICdrService {
    private static final int CDR_COUNT = 12;
    private static final int MIN_NUM_CALLS_PER_MONTH = 10;
    private static final int MAX_NUM_CALLS_PER_MONTH = 100;
    private static final long MIN_PHONE_NUMBER = 10000000000L;
    private static final long MAX_PHONE_NUMBER = 99999999999L;
    private static final int YEAR_SECONDS = 365 * 24 * 60 * 60;
    private static final int MIN_CALL_DURATION = 60;
    private static final int MAX_CALL_DURATION = 1800;
    private Random random = new Random();
    private IAbonentRepository abonentRepository = new AbonentRepository();
    private ITransactionRepository transactionRepository = new TransactionRepository();
    private List<Abonent> abonents = generateAbonents(15);
    private String filePath = "generatedCDR/";

    public void generateCdr() {
        for (int i = 0; i < CDR_COUNT; i++) {
            String fileName = filePath + (i + 1) + "_month_cdr.txt";

            try (FileWriter writer = new FileWriter(fileName)) {
                int NUM_CALLS_PER_MONTH = random.nextInt(
                        MAX_NUM_CALLS_PER_MONTH - MIN_NUM_CALLS_PER_MONTH + 1) +
                        MIN_NUM_CALLS_PER_MONTH;

                for (int j = 0; j < NUM_CALLS_PER_MONTH; j++) {
                    Abonent abonent = abonents.get(random.nextInt(abonents.size()));
                    Long startTime = Instant.now().getEpochSecond() - random.nextInt(YEAR_SECONDS);
                    long callDuration = random.nextInt(MAX_CALL_DURATION - MIN_CALL_DURATION) + MIN_CALL_DURATION;
                    long endTime = startTime + callDuration;

                    int typeCall = generateTypeCall();
                    String cdrRecord = String.format(
                            "%02d,%s,%d,%d\n",
                            typeCall,
                            abonent.getPhoneNumber(),
                            startTime,
                            endTime);

                    writer.write(cdrRecord);

                    transactionRepository.addTransaction(
                            String.valueOf(typeCall),
                            abonent,
                            Instant.ofEpochSecond(startTime),
                            Instant.ofEpochSecond(endTime));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Abonent> generateAbonents(int count) {
        List<Abonent> abonents = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String phoneNumber = generatePhoneNumber();
            Abonent abonent = new Abonent();
            abonent.setPhoneNumber(phoneNumber);
            abonents.add(abonentRepository.addAbonent(phoneNumber));
        }

        return abonents;
    }

    private int generateTypeCall() {
        return random.nextInt(2) + 1;
    }

    private String generatePhoneNumber() {
        return String.valueOf(MIN_PHONE_NUMBER + random.nextLong
                (MAX_PHONE_NUMBER - MIN_PHONE_NUMBER + 1));
    }
}