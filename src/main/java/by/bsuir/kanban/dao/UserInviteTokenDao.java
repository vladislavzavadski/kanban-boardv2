package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.UserInviteToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInviteTokenDao extends JpaRepository<UserInviteToken, String>{
}
