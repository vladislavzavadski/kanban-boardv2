package by.bsuir.kanban.domain;

import lombok.Data;

/**
 * Created by ulza1116 on 5/2/2017.
 */
@Data
public class Notification {
    private String from;
    private String to;
    private String subject;
    private String body;
}
