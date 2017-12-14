package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserDao extends JpaRepository<UserProject, UserProject.Id> {
}
