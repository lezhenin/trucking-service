package trucking.mappers;


import trucking.model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleMapperTest {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/testvehiclemapper";

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
    }

    @BeforeEach
    void init() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM Vehicle ";
        statement.executeUpdate(sql);
        statement.close();
    }


    @AfterAll
    static void releaseGlobal() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "DROP TABLE Vehicle ";
        statement.executeUpdate(sql);
        statement.close();

        connection.close();
    }

    @Test
    void test_insert() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        assertNull(vehicle.getId());

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);

        assertNotNull(vehicle.getId());

        assertEquals(5, vehicle.getMaxCargoWeight());
        assertEquals(10, vehicle.getMaxCargoHeight());
        assertEquals(15, vehicle.getMaxCargoWidth());
        assertEquals(20, vehicle.getMaxCargoLength());

    }

    @Test
    void test_update() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        assertNull(vehicle.getId());

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);

        Vehicle newVehicle  = new Vehicle("a", 3, 15, 17, 21);
        newVehicle.setId(vehicle.getId());
        mapper.update(newVehicle);

        Optional<Vehicle> optional = mapper.selectById(vehicle.getId());

        assertTrue(optional.isPresent());
        vehicle = optional.get();

        assertEquals(newVehicle.getMaxCargoWeight(), vehicle.getMaxCargoWeight());
        assertEquals(newVehicle.getMaxCargoHeight(), vehicle.getMaxCargoHeight());
        assertEquals(newVehicle.getMaxCargoWidth(), vehicle.getMaxCargoWidth());
        assertEquals(newVehicle.getMaxCargoLength(), vehicle.getMaxCargoLength());

    }

    @Test
    void test_update_clearIdentityMap() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);
        assertNull(vehicle.getId());

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);
        mapper.identityMap.clear();

        Vehicle newVehicle = new Vehicle("a", 3, 15, 17, 21);
        newVehicle.setId(vehicle.getId());
        mapper.update(newVehicle);
        mapper.identityMap.clear();

        Optional<Vehicle> optional = mapper.selectById(vehicle.getId());

        assertTrue(optional.isPresent());
        vehicle = optional.get();

        assertEquals(3, vehicle.getMaxCargoWeight());
        assertEquals(15, vehicle.getMaxCargoHeight());
        assertEquals(17, vehicle.getMaxCargoWidth());
        assertEquals(21, vehicle.getMaxCargoLength());

    }

    @Test
    void test_existsById() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);

        assertTrue(mapper.existById(vehicle.getId()));
        assertFalse(mapper.existById(vehicle.getId() + 1));

    }

    @Test
    void test_existsById_clearIdentityMap() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);
        mapper.identityMap.clear();

        assertTrue(mapper.existById(vehicle.getId()));
        assertFalse(mapper.existById(vehicle.getId() + 1));

    }


    @Test
    void test_selectById() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);

        VehicleMapper mapper = new VehicleMapper(connection);

        mapper.insert(vehicle);

        Optional<Vehicle> optional = mapper.selectById(vehicle.getId() + 1);
        assertFalse(optional.isPresent());

        optional = mapper.selectById(vehicle.getId());
        assertTrue(optional.isPresent());

        Vehicle foundVehicle = optional.get();

        assertEquals(vehicle.getId(), foundVehicle.getId());
        assertEquals(vehicle.getModel(), foundVehicle.getModel());
        assertEquals(vehicle.getMaxCargoWeight(), foundVehicle.getMaxCargoWeight());
        assertEquals(vehicle.getMaxCargoHeight(), foundVehicle.getMaxCargoHeight());
        assertEquals(vehicle.getMaxCargoWidth(), foundVehicle.getMaxCargoWidth());
        assertEquals(vehicle.getMaxCargoLength(), foundVehicle.getMaxCargoLength());

    }

    @Test
    void test_selectById_clearIdentityMap() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);
        mapper.identityMap.clear();

        Optional<Vehicle> optional = mapper.selectById(vehicle.getId() + 1);
        assertFalse(optional.isPresent());

        optional = mapper.selectById(vehicle.getId());
        assertTrue(optional.isPresent());

        Vehicle foundVehicle = optional.get();

        assertEquals(vehicle.getId(), foundVehicle.getId());
        assertEquals(vehicle.getModel(), foundVehicle.getModel());
        assertEquals(vehicle.getMaxCargoWeight(), foundVehicle.getMaxCargoWeight());
        assertEquals(vehicle.getMaxCargoHeight(), foundVehicle.getMaxCargoHeight());
        assertEquals(vehicle.getMaxCargoWidth(), foundVehicle.getMaxCargoWidth());
        assertEquals(vehicle.getMaxCargoLength(), foundVehicle.getMaxCargoLength());

    }

    @Test
    void test_select() throws SQLException {

        Vehicle vehicleOne = new Vehicle("a", 5, 10, 15, 20);
        Vehicle vehicleTwo = new Vehicle("b", 5, 15, 33, 25);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicleOne);
        mapper.insert(vehicleTwo);

        List<Vehicle> result = mapper.selectAll();

        assertEquals(2, result.size());

        assertEquals(result.get(0).getId(), vehicleOne.getId());
        assertEquals(result.get(1).getId(), vehicleTwo.getId());

    }

    @Test
    void test_deleteById() throws SQLException {

        Vehicle vehicle = new Vehicle("a", 5, 10, 15, 20);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicle);

        assertTrue(mapper.existById(vehicle.getId()));

        mapper.deleteById(vehicle.getId());

        assertFalse(mapper.existById(vehicle.getId()));

    }

    @Test
    void test_delete() throws SQLException {

        Vehicle vehicleOne = new Vehicle("a", 5, 10, 15, 20);
        Vehicle vehicleTwo = new Vehicle("b", 5, 15, 33, 25);

        VehicleMapper mapper = new VehicleMapper(connection);
        mapper.insert(vehicleOne);
        mapper.insert(vehicleTwo);

        assertTrue(mapper.existById(vehicleOne.getId()));
        assertTrue(mapper.existById(vehicleTwo.getId()));

        mapper.deleteAll();

        assertFalse(mapper.existById(vehicleOne.getId()));
        assertFalse(mapper.existById(vehicleTwo.getId()));
    }

}
