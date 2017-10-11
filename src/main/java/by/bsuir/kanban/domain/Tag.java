package by.bsuir.kanban.domain;

import lombok.Data;

/**
 * Created by vladislav on 22.05.17.
 */
@Data
public class Tag {
    private int id;
    private Project project;
    private String tagName;
    private String color;
}
