package net.bmw.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import net.bmw.model.Group;
import net.bmw.repository.GroupRepository;
import net.bmw.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GroupResourceTest {

    @InjectMock
    GroupService groupService;

    @InjectMock
    GroupRepository groupRepository;

    @Inject
    GroupResource groupResource;

    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group();
        group.setId(5L);
        group.setCreatingTime("12.12.2012");
        group.setMeetingTime("13.12.2022");
    }

    @Test
    void getAll() {
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        Mockito.when(groupService.getAll()).thenReturn(groups);
        Response response = groupResource.getAll();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Group> receivedGroups = (List<Group>) response.getEntity();
        assertFalse(receivedGroups.isEmpty());
        assertEquals(receivedGroups.size(), groups.size());
        assertEquals("12.12.2012", groups.get(0).getCreatingTime());
    }

    @Test
    void getGroup_thenGroupShouldBeFound() {
        Mockito.when(groupService.getById(5L)).thenReturn(group);

        Response response = groupResource.getGroup(5L);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getGroup_thenGroupShouldNotBeFound() throws Exception {
        Mockito.when(groupService.getById(5L)).thenThrow(new NotFoundException());

        Response response = groupResource.getGroup(5L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    void getAllPersonsByGroupId() {
    }

    @Test
    void createGroup() {
    }

    @Test
    void addPerson() {
    }

    @Test
    void updateGroup() {
    }

    @Test
    void deleteGroupById() {
    }

    @Test
    void deleteAllGroups() {
    }

    @Test
    void countGroups() {
    }
}