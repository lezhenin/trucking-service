package trucking.mappers;

import trucking.model.Entity;

import java.sql.*;
import java.util.*;

public abstract class AbstractMapper<T extends Entity> {

    protected final Connection connection;
    protected final Map<Long, T> identityMap = new HashMap<>();

    public AbstractMapper(Connection connection) {
        this.connection = connection;
    }

    protected abstract void setValues(PreparedStatement statement, T item, boolean setId) throws SQLException;

    protected abstract T collectValues(ResultSet resultSet) throws SQLException;

    protected abstract String insertTemplate();
    protected abstract String updateTemplate();
    protected abstract String selectAllTemplate();
    protected abstract String selectByIdTemplate();
    protected abstract String existsByIdTemplate();
    protected abstract String deleteAllTemplate();
    protected abstract String deleteByIdTemplate();


    void insert(T item) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(insertTemplate(), Statement.RETURN_GENERATED_KEYS);
        setValues(statement, item, false);
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        item.setId(resultSet.getLong(1));

        identityMap.put(item.getId(), item);

    }

    void update(T item) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(updateTemplate());
        setValues(statement, item, true);
        statement.executeUpdate();
        identityMap.put(item.getId(), item);
    }

    List<T> selectAll() throws SQLException {

        List<T> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAllTemplate());
        while (resultSet.next()) {
            T item = collectValues(resultSet);
            identityMap.put(item.getId(), item);
            result.add(item);
        }

        return result;
    }

    Optional<T> selectById(Long id) throws SQLException {

        if (identityMap.containsKey(id)) {
            return Optional.of(identityMap.get(id));
        }

        PreparedStatement statement = connection.prepareStatement(selectByIdTemplate());
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return Optional.empty();
        }

        T item = collectValues(resultSet);
        identityMap.put(item.getId(), item);
        return Optional.of(item);
    }

    public boolean existById(Long id) throws SQLException {

        if (identityMap.containsKey(id)) {
            return true;
        }

        PreparedStatement statement = connection.prepareStatement(existsByIdTemplate());
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();

    }

    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteAllTemplate());
        identityMap.clear();
    }

    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(deleteByIdTemplate());
        statement.setLong(1, id);
        statement.executeUpdate();
        identityMap.remove(id);
    }



}
