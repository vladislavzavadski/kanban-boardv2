package by.bsuir.kanban.domain.to;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String picture;
    private String password;
    private String email;
    private boolean canCreateProject;
}
