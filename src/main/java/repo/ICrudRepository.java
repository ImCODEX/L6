package repo;

import customexceptions.CustomExceptions;

import java.sql.SQLException;
import java.util.List;

/**
 * Absolute Base Repository
 * @param <T>
 */
public interface ICrudRepository<T> {

    T add(T obj) throws SQLException;

    List<T> getAll() throws SQLException;

    T find(Integer id) throws CustomExceptions, SQLException;

    T update(T oldObject, T newObject) throws SQLException;

    void delete(T obj) throws SQLException;
}
