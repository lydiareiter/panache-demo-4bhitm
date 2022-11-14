package at.htl.control;

import at.htl.entity.Vehicle;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.output.impl.Output;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class VehicleRepositoryTest {
    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    AgroalDataSource ds;

    @Transactional
    @Test
    void createSimpleVehicle() {
        Vehicle opel = new Vehicle("Opel","Diplomat");
        vehicleRepository.persist(opel);

        Table table = new Table(ds, "vehicle");
        output(table).toConsole();
        assertThat(table).row(0)
                .column("brand").value().isEqualTo("Opel")
                .column("type").value().isEqualTo("Diplomat");
    }
}