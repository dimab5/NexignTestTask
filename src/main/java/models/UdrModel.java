package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Usage Data Record (UDR) model.
 */
@Getter
@Setter
public class UdrModel {
    /**
     * The phone number associated with the UDR.
     */
    @JsonProperty("msisdn")
    private String phoneNumber;

    /**
     * The duration of incoming calls for the UDR.
     */
    private CallDuration incomingCall;

    /**
     * The duration of outgoing calls for the UDR.
     */
    private CallDuration outcomingCall;

    /**
     * Converts the UdrModel object to JSON string.
     *
     * @return JSON representation of the UdrModel object.
     */
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
