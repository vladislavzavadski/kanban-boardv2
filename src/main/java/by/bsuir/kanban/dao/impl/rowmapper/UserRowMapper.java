package by.bsuir.kanban.dao.impl.rowmapper;

import by.bsuir.kanban.dao.impl.TableColumn;
import by.bsuir.kanban.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by ulza1116 on 6/12/2017.
 */
@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setUsername(resultSet.getString(TableColumn.USERNAME));
        user.setFirstName(resultSet.getString(TableColumn.FIRST_NAME));
        user.setLastName(resultSet.getString(TableColumn.LAST_NAME));
        user.setPicture(resultSet.getString(TableColumn.PICTURE));

        return user;
    }
}
