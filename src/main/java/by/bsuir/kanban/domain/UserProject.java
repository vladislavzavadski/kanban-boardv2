package by.bsuir.kanban.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
@Getter
public class UserProject {

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable{
        @Column(name = "project_id")
        private int id;

        @Column(name = "username")
        private String username;

    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private User user;

    public void setProject(Project project){
        this.project = project;
        id.id = project.getId();
    }

    public void setUser(User user){
        this.user = user;
        id.username = user.getUsername();
    }

}
