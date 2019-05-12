package cz.czechitas.webapp;

import java.util.List;

public interface PanenkaRepository {

    public List<Panenka> findAll();
    public Panenka findById(Long id);
    public Panenka save(Panenka zaznamKUlozeni);
    public void delete(Long id);
}
