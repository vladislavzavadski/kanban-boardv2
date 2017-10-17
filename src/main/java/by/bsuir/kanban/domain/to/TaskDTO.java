package by.bsuir.kanban.domain.to;

import by.bsuir.kanban.domain.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private int id;
    private String name;
    private String summary;
    private Priority priority;
    private StatusDTO taskStatus;

}
