package org.acme.data.util;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.data.DummyDataCreator;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@QuarkusTest
public class RandomGroupGeneratorTest {

    @InjectMock
    Session session;

    @BeforeEach
    public void setup() {
        Mockito.doNothing().when(session).persist(Mockito.any());
    }

    @Test
    public void testGenerateRandomMeetingDateTime() {

        try (MockedStatic<RandomGroupGenerator> staticRandomGenerator =
                     Mockito.mockStatic(RandomGroupGenerator.class)
        ) {
            staticRandomGenerator.when(() -> RandomGroupGenerator.generateRandomLong(Mockito.anyInt(), Mockito.anyInt())).thenReturn(12L);

            long plusTime = 12L;

            staticRandomGenerator.when(() -> RandomGroupGenerator.generateRandomMeetingDateTime(Mockito.any(), Mockito.any())).thenCallRealMethod();

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

    @Test
    public void testPopulateGroupWithMembers() {
        try (MockedStatic<RandomGroupGenerator> staticRandomGenerator =
                     Mockito.mockStatic(RandomGroupGenerator.class)
        ) {
            PanacheMock.mock(Person.class);
            PanacheMock.mock(Group.class);

            Person dummyPersonOne = DummyDataCreator.createDummyPerson();

            Person dummyPersonTwo = DummyDataCreator.createDummyPerson();
            dummyPersonTwo.id = 2L;

            Person dummyPersonThree = DummyDataCreator.createDummyPerson();
            dummyPersonThree.id = 3L;

            Mockito.when(Person.findById(Mockito.anyLong()))
                    .thenReturn(dummyPersonOne)
                    .thenReturn(dummyPersonTwo)
                    .thenReturn(dummyPersonThree);

            staticRandomGenerator.when(() -> RandomGroupGenerator.populateGroupWithMembers(Mockito.any(), Mockito.any(), Mockito.anyLong())).thenCallRealMethod();

            Set<Long> memberIDs = new HashSet<>();
            memberIDs.add(dummyPersonOne.id);
            memberIDs.add(dummyPersonTwo.id);
            memberIDs.add(dummyPersonThree.id);

            Group actual = DummyDataCreator.createDummyGroup();
            RandomGroupGenerator.populateGroupWithMembers(memberIDs, actual, 3L);

            Group expected = DummyDataCreator.createDummyGroup();
            expected.getMembers().add(dummyPersonOne);
            expected.getMembers().add(dummyPersonTwo);
            expected.getMembers().add(dummyPersonThree);

            Assertions.assertEquals(actual.getMembers().size(), expected.getMembers().size());
        }
    }
}
