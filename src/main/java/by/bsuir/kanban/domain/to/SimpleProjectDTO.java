package by.bsuir.kanban.domain.to;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SimpleProjectDTO {

    private int id;
    private String name;
    private int usersNumber;
    private int totalTaskNumber;
    private UserDTO lead;

}
