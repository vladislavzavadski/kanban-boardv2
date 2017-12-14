package by.bsuir.kanban.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Data
@Entity
public class ChangeStatusPermission extends Permission{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_status_id")
    private Status from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_status_id")
    private Status to;
}
