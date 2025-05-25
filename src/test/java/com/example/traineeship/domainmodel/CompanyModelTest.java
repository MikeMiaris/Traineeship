package com.example.traineeship.domainmodel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyModelTest {

    @Test
    @DisplayName("Constructor should initialize fields and empty positions list")
    void testConstructorAndGetters() {
        Company company = new Company("comp123", "Acme Corp", "Athens");
        assertEquals("comp123", company.getUsername());
        assertEquals("Acme Corp", company.getCompanyName());
        assertEquals("Athens", company.getCompanyLocation());

        List<TraineeshipPosition> positions = company.getPositions();
        assertNotNull(positions, "positions list should be initialized");
        assertTrue(positions.isEmpty(), "new company should start with zero positions");
    }

    @Test
    @DisplayName("announcePosition should add to positions list")
    void testAnnouncePosition() {
        Company company = new Company("comp123", "Acme Corp", "Athens");
        TraineeshipPosition pos = new TraineeshipPosition(
            "Title", "Desc",
            LocalDate.now(), LocalDate.now().plusDays(30),
            "topic1,topic2", "skillA,skillB"
        );
        pos.setId(42);
        company.announcePosition(pos);
        List<TraineeshipPosition> positions = company.getPositions();
        assertEquals(1, positions.size(), "one position should have been added");
        assertSame(pos, positions.get(0), "the exact same object must be in the list");
    }

    @Test
    @DisplayName("removePosition should remove by matching ID")
    void testRemovePosition_existingId() {
        Company company = new Company("comp123", "Acme Corp", "Athens");

        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setId(1);
        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setId(2);

        company.announcePosition(p1);
        company.announcePosition(p2);
        assertEquals(2, company.getPositions().size());

        company.removePosition(1);

        assertEquals(1, company.getPositions().size());
        assertEquals(2, company.getPositions().get(0).getId());
    }

    @Test
    @DisplayName("removePosition with non‐existent ID leaves list unchanged")
    void testRemovePosition_nonexistentId() {
        Company company = new Company("comp123", "Acme Corp", "Athens");
        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setId(10);
        company.announcePosition(p1);
        assertEquals(1, company.getPositions().size());
        company.removePosition(999);
        assertEquals(1, company.getPositions().size(), "removing a non‐existent ID should do nothing");
        assertEquals(10, company.getPositions().get(0).getId());
    }
}
