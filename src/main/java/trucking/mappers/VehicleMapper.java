package trucking.mappers;


import trucking.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleMapper extends AbstractMapper<Vehicle> {

    public VehicleMapper(Connection connection) {
        super(connection);
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

    @Override
    protected Vehicle collectValues(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle(
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getInt(6));
        vehicle.setId(resultSet.getLong(1));
        return vehicle;
    }

    @Override
    protected String insertTemplate() {
        return "INSERT INTO Vehicle (model, maxCargoWeight, maxCargoHeight, maxCargoWidth, maxCargoLength)" +
               " VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected String updateTemplate() {
        return "UPDATE Vehicle SET (model, maxCargoWeight, maxCargoHeight, maxCargoWidth, maxCargoLength) " +
               "= (?, ?, ?, ?, ?) WHERE id = ?;";
    }

    @Override
    protected String selectAllTemplate() {
        return "SELECT * FROM Vehicle";
    }

    @Override
    protected String selectByIdTemplate() {
        return "SELECT * FROM Vehicle WHERE id = ?";
    }

    @Override
    protected String existsByIdTemplate() {
        return "SELECT 1 FROM Vehicle WHERE id = ?";
    }

    @Override
    protected String deleteAllTemplate() {
        return "DELETE FROM Vehicle";
    }

    @Override
    protected String deleteByIdTemplate() {
        return "DELETE FROM Vehicle WHERE id = ?";
    }

}
