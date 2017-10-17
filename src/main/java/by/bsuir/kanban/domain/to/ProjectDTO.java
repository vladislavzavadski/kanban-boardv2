package by.bsuir.kanban.domain.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {

    private int id;
    private String name;
    private int usersNumber;
    private int totalTaskNumber;
    private UserDTO lead;

}
