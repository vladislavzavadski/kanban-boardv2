package by.bsuir.kanban.domain;

import lombok.Data;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Data
public class ChangeStatusPermission {
    private int id;
    private Status from;
    private Status to;
}
