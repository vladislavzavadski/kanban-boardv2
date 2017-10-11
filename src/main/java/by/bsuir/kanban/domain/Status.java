package by.bsuir.kanban.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by vladislav on 09.04.17.
 */
@Data
public class Status implements Serializable {
    private int id;
    private Project project;
    private String name;
    private int order;
}
