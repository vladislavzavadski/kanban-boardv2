package by.bsuir.kanban.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ulza1116 on 5/2/2017.
 */
@Data
@AllArgsConstructor
public class Notification {
    private String from;
    private String to;
    private String subject;
    private String body;
}
