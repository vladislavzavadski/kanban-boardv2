package by.bsuir.kanban.domain.to;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComplexProjectDTO {

    private int id;
    private String name;
    private List<TaskDTO> tasks;

}
