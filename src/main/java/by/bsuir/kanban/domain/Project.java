package by.bsuir.kanban.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by vladislav on 08.04.17.
 */
@Data
public class Project implements Serializable {
    private int id;
    private String name;
    private User lead;
    private String description;
    private String logo;
    private int usersNumber;
    private List<Task> tasks;
    private List<User> users;
    private int totalTaskCount;
    private Company company;
}
