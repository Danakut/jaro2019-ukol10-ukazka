package cz.czechitas.webapp;

public class Panenka {

    private Long id;
    private String jmeno;
    private String vrsek;
    private String spodek;
    private String datumVzniku;
    private int verze;

    public Panenka() {

    }

    public Panenka(String jmeno, String vrsek, String spodek, String datumVzniku) {
        this.jmeno = jmeno;
        this.vrsek = vrsek;
        this.spodek = spodek;
        this.datumVzniku = datumVzniku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getVrsek() {
        return vrsek;
    }

    public void setVrsek(String vrsek) {
        this.vrsek = vrsek;
    }

    public String getSpodek() {
        return spodek;
    }

    public void setSpodek(String spodek) {
        this.spodek = spodek;
    }

    public String getDatumVzniku() {
        return datumVzniku;
    }

    public void setDatumVzniku(String datumVzniku) {
        this.datumVzniku = datumVzniku;
    }

    public int getVerze() {
        return verze;
    }

    public void setVerze(int verze) {
        this.verze = verze;
    }
}
