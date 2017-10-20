package by.bsuir.kanban.dao;


import by.bsuir.kanban.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by ulza1116 on 6/12/2017.
 */
public interface PermissionDao extends JpaRepository<Permission, Integer>{

    @Query("select (count (per) > 0) from Permission per where per.userProject = :userProject and per.permissionType = :type")
    boolean isPermissionExist(@Param("userProject")UserProject userProject, @Param("type")PermissionType permissionType);

    @Query("select (count (per) > 0) from Permission per where per.userProject = :userProject and per.permissionType = :permission")
    boolean doesHavePermission(@Param("permission") PermissionType permissionType, @Param("userProject") UserProject userProject);

    @Query("select (count (per) > 0) from Permission per where per.userProject = :userProject and per.from = :fromStatus and per.to = :toStatus")
    boolean isPermissionExist(@Param("fromStatus") Status from, @Param("toStatus") Status to, @Param("userProject") UserProject userProject);

}
