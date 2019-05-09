package cz.czechitas.webapp;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.lookup.*;

public class PanenkaRepository {

    private JdbcTemplate odesilacDotazu;
    private RowMapper<Panenka> prevodnik;

    public PanenkaRepository() {
        try {
            MariaDbDataSource konfiguraceDatabaze = new MariaDbDataSource();
            konfiguraceDatabaze.setUserName("dolluser");
            konfiguraceDatabaze.setPassword("dolluspass");
            konfiguraceDatabaze.setUrl("jdbc:mariadb://localhost:3306/dollbase");

            odesilacDotazu = new JdbcTemplate(konfiguraceDatabaze);
            prevodnik = BeanPropertyRowMapper.newInstance(Panenka.class);
        } catch (SQLException e) {
            throw new DataSourceLookupFailureException("Nepodarilo se vytvorit DataSource", e);
        }
    }


    public List<Panenka> findAll() {
        List<Panenka> panenky = odesilacDotazu.query(
                "SELECT id, jmeno, vrsek, spodek, datumVzniku, version FROM dollbase",
                prevodnik);
        return panenky;
    }

    public Panenka findById(Long id) {
        Panenka zakaznik = odesilacDotazu.queryForObject(
                "select ID, Firstname, Lastname, Address, Deleted, Version" +
                        " from Customers where ID=?",
                prevodnik,
                id);
        return zakaznik;
    }

    public Panenka save(Panenka zaznamKUlozeni) {
        // TODO
        throw new UnsupportedOperationException("Zatim nenaprogramovano");
    }

    public void delete(Long id, int version) {
        // TODO
        throw new UnsupportedOperationException("Zatim nenaprogramovano");
    }
}