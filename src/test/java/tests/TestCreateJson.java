package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.CallDuration;
import models.UdrModel;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class TestCreateJson {
    @Test
    public void test1() throws JsonProcessingException {
        String expectedJson =
                """
                {
                  "incomingCall" : {
                    "totalTime" : "00:43:34"
                  },
                  "outcomingCall" : {
                    "totalTime" : "00:11:40"
                  },
                  "msisdn" : "22969392259"
                }""";

        UdrModel udrModel = new UdrModel();
        udrModel.setPhoneNumber("22969392259");
        udrModel.setIncomingCall(new CallDuration(
                Duration.ofHours(0).plusMinutes(43).plusSeconds(34)));
        udrModel.setOutcomingCall(new CallDuration(
                Duration.ofHours(0).plusMinutes(11).plusSeconds(40)));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode actualNode = objectMapper.readTree(udrModel.toJson());

        Assert.assertEquals(expectedNode, actualNode);
    }

    @Test
    public void test2() throws JsonProcessingException {
        String expectedJson =
                """
                {
                  "incomingCall" : {
                    "totalTime" : "00:13:28"
                  },
                  "outcomingCall" : {
                    "totalTime" : "00:19:34"
                  },
                  "msisdn" : "23896848133"
                }""";

        UdrModel udrModel = new UdrModel();
        udrModel.setPhoneNumber("23896848133");
        udrModel.setIncomingCall(new CallDuration(
                Duration.ofHours(0).plusMinutes(13).plusSeconds(28)));
        udrModel.setOutcomingCall(new CallDuration(
                Duration.ofHours(0).plusMinutes(19).plusSeconds(34)));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode actualNode = objectMapper.readTree(udrModel.toJson());

        Assert.assertEquals(expectedNode, actualNode);
    }
}
