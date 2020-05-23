package core.gateway;


import core.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleTableGateway {

    private static final String sqlInsertTemplate =
            "INSERT INTO Vehicle (model, maxCargoWeight, maxCargoHeight, maxCargoWidth, maxCargoLength)" +
                    " VALUES (?, ?, ?, ?, ?);";

    private static final String sqlUpdateTemplate =
            "UPDATE Vehicle SET (model, maxCargoWeight, maxCargoHeight, maxCargoWidth, maxCargoLength) " +
                    "= (?, ?, ?, ?, ?) WHERE id = ?;";

    private static final String sqlSelectTemplate =
            "SELECT * FROM Vehicle";

    private static final String sqlSelectByIdTemplate =
            "SELECT * FROM Vehicle WHERE id = ?";

    private static final String sqlExistsByIdTemplate =
            "SELECT 1 FROM Vehicle WHERE id = ?";

    private static final String sqlDeleteByIdTemplate =
            "DELETE FROM Vehicle WHERE id = ?";

    private static final String sqlDeleteTemplate =
            "DELETE FROM Vehicle";


    private final Connection connection;

    public VehicleTableGateway(Connection connection) {
        this.connection = connection;
    }

    void insert(Vehicle vehicle) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(sqlInsertTemplate, Statement.RETURN_GENERATED_KEYS);
        setValues(statement, vehicle, false);
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        vehicle.setId(resultSet.getLong(1));

    }

    void update(Vehicle vehicle) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlUpdateTemplate);
        setValues(statement, vehicle, true);
        statement.executeUpdate();
    }

    List<Vehicle> select() throws SQLException {

        List<Vehicle> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectTemplate);
        while (resultSet.next()) {
            Vehicle vehicle = getValues(resultSet);
            result.add(vehicle);
        }

        return result;
    }

    Optional<Vehicle> selectById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlSelectByIdTemplate);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return Optional.empty();
        }

        Vehicle vehicle = getValues(resultSet);
        return Optional.of(vehicle);
    }

    public boolean existById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlExistsByIdTemplate);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public void deleteById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlDeleteByIdTemplate);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    public void delete() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlDeleteTemplate);
    }


    protected void setValues(PreparedStatement statement, Vehicle vehicle, boolean setId) throws SQLException {
        statement.setString(1, vehicle.getModel());
        statement.setInt(2, vehicle.getMaxCargoWeight());
        statement.setInt(3, vehicle.getMaxCargoHeight());
        statement.setInt(4, vehicle.getMaxCargoWidth());
        statement.setInt(5, vehicle.getMaxCargoLength());
        if (setId) {
            statement.setLong(6, vehicle.getId());
        }
    }

    protected Vehicle getValues(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle(
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getInt(6));
        vehicle.setId(resultSet.getLong(1));
        return vehicle;
    }

}
