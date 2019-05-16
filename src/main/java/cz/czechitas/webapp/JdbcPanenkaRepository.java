package cz.czechitas.webapp;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.lookup.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class JdbcPanenkaRepository {

    private JdbcTemplate odesilacDotazu;
    private RowMapper<Panenka> prevodnik;

    public JdbcPanenkaRepository() {
        try {
            MariaDbDataSource konfiguraceDatabaze = new MariaDbDataSource();
            konfiguraceDatabaze.setUserName("student");
            konfiguraceDatabaze.setPassword("password");
            konfiguraceDatabaze.setUrl("jdbc:mariadb://localhost:3306/herokuSkladPanenek");

            odesilacDotazu = new JdbcTemplate(konfiguraceDatabaze);
            prevodnik = BeanPropertyRowMapper.newInstance(Panenka.class);
        } catch (SQLException e) {
            throw new DataSourceLookupFailureException("Nepodarilo se vytvorit DataSource", e);
        }
    }


    public List<Panenka> findAll() {
        List<Panenka> seznam = odesilacDotazu.query(
                "SELECT id, jmeno, vrsek, spodek, datumVzniku, verze FROM panenky",
                prevodnik);
        return seznam;
    }

    public Panenka findById(Long id) {
        Panenka panenka = odesilacDotazu.queryForObject(
                "SELECT id, jmeno, vrsek, spodek, datumVzniku, verze FROM panenky where id=?", prevodnik, id);
        return panenka;
    }

    public Panenka save(Panenka zaznamKUlozeni) {
        if (zaznamKUlozeni.getId() == null) {
            pridejNovyZaznam(zaznamKUlozeni);
        } else {
            updatujZaznam(zaznamKUlozeni);
        }
        return zaznamKUlozeni;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM panenky WHERE id=?";
        odesilacDotazu.update(sql, id);
    }

    private Panenka pridejNovyZaznam(Panenka zaznamKUlozeni) {
        GeneratedKeyHolder drzakNaVygenerovanyKlic = new GeneratedKeyHolder();
        String sql = "INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES (?,?,?,?)";
        odesilacDotazu.update((Connection con) -> {
                    PreparedStatement prikaz = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prikaz.setString(1, zaznamKUlozeni.getJmeno());
                    prikaz.setString(2, zaznamKUlozeni.getVrsek());
                    prikaz.setString(3, zaznamKUlozeni.getSpodek());
                    prikaz.setString(4, LocalDate.now().toString());
                    return prikaz;
                },
                drzakNaVygenerovanyKlic);
        zaznamKUlozeni.setId(drzakNaVygenerovanyKlic.getKey().longValue());
        return zaznamKUlozeni;
    }

    private Panenka updatujZaznam(Panenka zaznamKUlozeni) {
        String sql = "UPDATE panenky SET jmeno=?, vrsek=?, spodek=?, verze=? WHERE id=?";
        odesilacDotazu.update(sql,
                zaznamKUlozeni.getJmeno(),
                zaznamKUlozeni.getVrsek(),
                zaznamKUlozeni.getSpodek(),
                zaznamKUlozeni.getVerze() + 1,
                zaznamKUlozeni.getId());
        return zaznamKUlozeni;
    }
}