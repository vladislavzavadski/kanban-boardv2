package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenDao extends JpaRepository<RegistrationToken, String> {
}
