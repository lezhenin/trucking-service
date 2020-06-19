package trucking.mappers;


import trucking.model.Driver;
import trucking.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper extends AbstractMapper<Driver> {

    private final VehicleMapper vehicleMapper;

    public DriverMapper(Connection connection) {
        super(connection);
        vehicleMapper = new VehicleMapper(connection);
    }

    protected void setValues(PreparedStatement statement, Driver driver, boolean setId) throws SQLException {
        statement.setString(1, driver.getFirstName());
        statement.setString(2, driver.getLastName());
        statement.setString(3, driver.getPhoneNumber());
        statement.setString(4, driver.getEmail());
        statement.setLong(5, driver.getVehicle().getId());
        if (setId) {
            statement.setLong(6, driver.getId());
        }
    }

    @Override
    protected Driver collectValues(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = vehicleMapper.selectById(resultSet.getLong(6)).get();
        Driver driver = new Driver(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                vehicle
        );
        driver.setId(resultSet.getLong(1));
        return driver;
    }

    @Override
    protected String insertTemplate() {
        return "INSERT INTO Driver (firstName, lastName, phoneNumber, email, vehicleId)" +
               " VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected String updateTemplate() {
        return "UPDATE Driver SET (firstName, lastName, phoneNumber, email, vehicleId)" +
                "= VALUES (?, ?, ?, ?, ?) WHERE id = ?;";
    }

    @Override
    protected String selectAllTemplate() {
        return "SELECT * FROM Driver";
    }

    @Override
    protected String selectByIdTemplate() {
        return "SELECT * FROM Driver WHERE id = ?";
    }

    @Override
    protected String existsByIdTemplate() {
        return "SELECT 1 FROM Driver WHERE id = ?";
    }

    @Override
    protected String deleteAllTemplate() {
        return "DELETE FROM Driver";
    }

    @Override
    protected String deleteByIdTemplate() {
        return "DELETE FROM Driver WHERE id = ?";
    }

}
