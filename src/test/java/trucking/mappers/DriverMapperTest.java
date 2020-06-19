package trucking.mappers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trucking.model.Driver;
import trucking.model.Vehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DriverMapperTest {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/testdrivermapper";

    static final String USER = "sa";
    static final String PASS = "";

    static Connection connection;

    @BeforeAll
    static void initGlobal() throws SQLException, ClassNotFoundException {

        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE Vehicle " +
                "(id IDENTITY not NULL, " +
                " model VARCHAR(255), " +
                " maxCargoWeight INTEGER, " +
                " maxCargoHeight INTEGER, " +
                " maxCargoWidth  INTEGER, " +
                " maxCargoLength INTEGER, " +
                " PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
        statement.close();


        statement = connection.createStatement();
        sql = "CREATE TABLE Driver " +
                "(id IDENTITY not NULL, " +
                " firstName VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " phoneNumber VARCHAR(255), " +
                " email  VARCHAR(255), " +
                " vehicleId INTEGER, " +
                " PRIMARY KEY ( id )," +
                " FOREIGN KEY ( vehicleId ) REFERENCES Vehicle ( id ))";
        statement.executeUpdate(sql);
        statement.close();

    }

    @BeforeEach
    void init() throws SQLException {

        Statement statement = connection.createStatement();
        String sql = "DELETE FROM Driver ";
        statement.executeUpdate(sql);
        statement.close();

        statement = connection.createStatement();
        sql = "DELETE FROM Vehicle ";
        statement.executeUpdate(sql);
        statement.close();

    }


    @AfterAll
    static void releaseGlobal() throws SQLException {

        Statement statement = connection.createStatement();
        String sql = "DROP TABLE Driver ";
        statement.executeUpdate(sql);
        statement.close();


        statement = connection.createStatement();
        sql = "DROP TABLE Vehicle ";
        statement.executeUpdate(sql);
        statement.close();

        connection.close();

    }

    @Test
    void test_insert() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        Driver driver = new Driver("a", "b", "c", "d", vehicle);

        assertNull(driver.getId());

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicle);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driver);

        assertNotNull(driver.getId());

    }

    @Test
    void test_update() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        Driver driver = new Driver("a", "b", "c", "d", vehicle);

        assertNull(driver.getId());

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicle);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driver);

        Driver newDriver = new Driver("aa", "bb", "c", "d", vehicle);
        newDriver.setId(driver.getId());
        driverMapper.update(newDriver);

        Optional<Driver> optional = driverMapper.selectById(vehicle.getId());

        assertTrue(optional.isPresent());
        driver = optional.get();

        assertEquals(newDriver.getId(), driver.getId());
        assertEquals(newDriver.getFirstName(), driver.getFirstName());
        assertEquals(newDriver.getLastName(), driver.getLastName());
        assertEquals(newDriver.getPhoneNumber(), driver.getPhoneNumber());
        assertEquals(newDriver.getEmail(), driver.getEmail());
        assertEquals(newDriver.getVehicle().getId(), driver.getVehicle().getId());

    }

    @Test
    void test_existsById_clearIdentityMap() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        Driver driver = new Driver("a", "b", "c", "d", vehicle);

        assertNull(driver.getId());

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicle);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driver);

        driverMapper.identityMap.clear();

        assertTrue(driverMapper.existById(driver.getId()));
        assertFalse(driverMapper.existById(driver.getId() + 1));

    }


    @Test
    void test_selectById_clearIdentityMap() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        Driver driver = new Driver("a", "b", "c", "d", vehicle);

        assertNull(driver.getId());

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicle);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driver);

        driverMapper.identityMap.clear();

        Optional<Driver> optional = driverMapper.selectById(vehicle.getId() + 1);
        assertFalse(optional.isPresent());

        optional = driverMapper.selectById(vehicle.getId());
        assertTrue(optional.isPresent());

        Driver foundDriver = optional.get();

        assertEquals(driver.getId(), foundDriver.getId());
        assertEquals(driver.getFirstName(), foundDriver.getFirstName());
        assertEquals(driver.getLastName(), foundDriver.getLastName());
        assertEquals(driver.getPhoneNumber(), foundDriver.getPhoneNumber());
        assertEquals(driver.getEmail(), foundDriver.getEmail());
        assertEquals(driver.getVehicle().getId(), foundDriver.getVehicle().getId());

    }

    @Test
    void test_select() throws SQLException {

        Vehicle vehicleOne = new Vehicle("a", 5, 10, 15, 20);
        Driver driverOne = new Driver("a", "b", "c", "d", vehicleOne);

        Vehicle vehicleTwo = new Vehicle("b", 5, 15, 33, 25);
        Driver driverTwo = new Driver("e", "f", "c", "d", vehicleTwo);

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicleOne);
        vehicleMapper.insert(vehicleTwo);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driverOne);
        driverMapper.insert(driverTwo);

        List<Driver> result = driverMapper.selectAll();

        assertEquals(2, result.size());

        assertEquals(result.get(0).getId(), driverOne.getId());
        assertEquals(result.get(0).getVehicle().getId(), driverOne.getVehicle().getId());
        assertEquals(result.get(1).getId(), driverTwo.getId());
        assertEquals(result.get(1).getVehicle().getId(), driverTwo.getVehicle().getId());

    }

    @Test
    void test_deleteById() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        Driver driver = new Driver("a", "b", "c", "d", vehicle);

        assertNull(driver.getId());

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicle);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driver);

        assertTrue(driverMapper.existById(driver.getId()));

        driverMapper.deleteById(driver.getId());

        assertFalse(driverMapper.existById(driver.getId()));

    }

    @Test
    void test_delete() throws SQLException {

        Vehicle vehicleOne = new Vehicle("a", 5, 10, 15, 20);
        Driver driverOne = new Driver("a", "b", "c", "d", vehicleOne);

        Vehicle vehicleTwo = new Vehicle("b", 5, 15, 33, 25);
        Driver driverTwo = new Driver("e", "f", "c", "d", vehicleTwo);

        VehicleMapper vehicleMapper = new VehicleMapper(connection);
        vehicleMapper.insert(vehicleOne);
        vehicleMapper.insert(vehicleTwo);

        DriverMapper driverMapper = new DriverMapper(connection);
        driverMapper.insert(driverOne);
        driverMapper.insert(driverTwo);

        assertTrue(driverMapper.existById(driverOne.getId()));
        assertTrue(driverMapper.existById(driverTwo.getId()));

        driverMapper.deleteAll();

        assertFalse(driverMapper.existById(driverOne.getId()));
        assertFalse(driverMapper.existById(driverTwo.getId()));
    }



}
