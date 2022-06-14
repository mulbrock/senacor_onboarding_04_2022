package org.acme.data.util;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.data.DummyDataCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@QuarkusTest
public class RandomGroupGeneratorTest {

    @Test
    public void testGenerateRandomMeetingDateTime() {

        try (MockedStatic<RandomGroupGenerator> staticRandomGenerator =
                     Mockito.mockStatic(RandomGroupGenerator.class)
        ) {
            staticRandomGenerator.when(() -> {
                RandomGroupGenerator.generateRandomLong(Mockito.anyInt(), Mockito.anyInt());
            }).thenReturn(12L);

            long plusTime = 12L;

            staticRandomGenerator.when(() -> {
                RandomGroupGenerator.generateRandomMeetingDateTime(Mockito.any(), Mockito.any());
            }).thenCallRealMethod();

            LocalDateTime dummyDateTime = DummyDataCreator.groupMeetingTime;

            LocalDateTime expectedDateTime = dummyDateTime.plusHours(plusTime);
            LocalDateTime actualDateTime = RandomGroupGenerator.generateRandomMeetingDateTime(RandomGroupGenerator.DateOffset.HOURS, dummyDateTime);
            Assertions.assertEquals(expectedDateTime, actualDateTime);

            expectedDateTime = dummyDateTime.plusDays(plusTime);
            actualDateTime = RandomGroupGenerator.generateRandomMeetingDateTime(RandomGroupGenerator.DateOffset.DAYS, dummyDateTime);
            Assertions.assertEquals(expectedDateTime, actualDateTime);

            expectedDateTime = dummyDateTime.plusWeeks(plusTime);
            actualDateTime = RandomGroupGenerator.generateRandomMeetingDateTime(RandomGroupGenerator.DateOffset.WEEKS, dummyDateTime);
            Assertions.assertEquals(expectedDateTime, actualDateTime);
        }


    }
}
