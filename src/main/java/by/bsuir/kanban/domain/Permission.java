package by.bsuir.kanban.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Data
@Entity
@Table(name = "permission")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "change_status", value = ChangeStatusPermission.class),
        @JsonSubTypes.Type(name = "original", value = Permission.class)
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "project_id"), @JoinColumn(name = "username")})
    private UserProject userProject;
}
