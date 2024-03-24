package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;


@Getter
@Setter
public class CallDuration {
    @JsonProperty("totalTime")
    private String totalTime;

    public CallDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        totalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
