package cz.czechitas.webapp;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class HlavniController {

    private List<String> topPaths = getPaths("top");
    private List<String> bottomPaths = getPaths("bottom");

    public HlavniController() throws IOException {

    }

    private JdbcPanenkaRepository panenkaRepository = new JdbcPanenkaRepository();

    @RequestMapping("/")
    public ModelAndView zobrazIndex(){
        return new ModelAndView("index");
    }

    @RequestMapping("/seznam.html")
    public ModelAndView zobrazSeznam(){
        ModelAndView drzak = new ModelAndView("seznam");
        drzak.addObject("seznamPanenek", panenkaRepository.findAll());
        return drzak;
    }

    @RequestMapping("/hledat.html")
    public ModelAndView zobrazHledani(){
        return new ModelAndView("hledat");
    }

    @RequestMapping(value = "/detail-{id:[0-9]+}.html", method = RequestMethod.GET)
    public ModelAndView zobrazDetail(@PathVariable Long id) {
        ModelAndView drzak = new ModelAndView("detail");
        Panenka nalezenaPanenka = panenkaRepository.findById(id);
        drzak.addObject("panenka", nalezenaPanenka);
        drzak.addObject("seznamVrsku", topPaths);
        drzak.addObject("seznamSpodku", bottomPaths);
        return drzak;
    }

    @RequestMapping(value = "/detail-{id:[0-9]+}.html", method = RequestMethod.POST)
    public ModelAndView zpracujDetail(@PathVariable Long id, PanenkaForm formular) {
        Panenka nalezenaPanenka = panenkaRepository.findById(id);
        if (nalezenaPanenka != null) {
            nalezenaPanenka.setJmeno(formular.getJmeno());
            nalezenaPanenka.setVrsek(formular.getVrsek());
            nalezenaPanenka.setSpodek(formular.getSpodek());
            panenkaRepository.save(nalezenaPanenka);
        }
        return new ModelAndView("redirect:/seznam.html");
    }

    @RequestMapping(value = "/nova.html", method = RequestMethod.GET)
    public ModelAndView zobrazNova(){
        ModelAndView drzak = new ModelAndView("nova");
        drzak.addObject("seznamVrsku", topPaths);
        drzak.addObject("seznamSpodku", bottomPaths);
        return drzak;
    }

    @RequestMapping(value = "/nova.html", method = RequestMethod.POST)
    public ModelAndView zpracujNova(PanenkaForm formular) {
        String datum = LocalDate.now().toString();
        Panenka panenka = new Panenka(formular.getJmeno(), formular.getVrsek(), formular.getSpodek(), datum);
        panenkaRepository.save(panenka);
        return new ModelAndView("redirect:/seznam.html");
    }

    @RequestMapping(value = "/smazat-{id:[0-9]+}.html")
    public ModelAndView zpracujSmazani(@PathVariable Long id) {
        panenkaRepository.delete(id);
        return new ModelAndView("redirect:/seznam.html");
    }


    //parametr "type": "top" pro svrsky, "bottom" pro spodky
    public List<String> getPaths(String type) throws IOException {
        ResourcePatternResolver prohledavacSlozek = new PathMatchingResourcePatternResolver();
        List<Resource> soubory = Arrays.asList(prohledavacSlozek.getResources("classpath:/static/images/*"));
        Pattern vzorNazvu = null;

        if (type.equals("top")) {
            vzorNazvu = Pattern.compile("javagirl_top\\d+.png");
        } else if (type.equals("bottom")) {
            vzorNazvu = Pattern.compile("javagirl_bottom\\d+.png");
        } else {
            //throw Exception
        }

        List<String> paths = new ArrayList<>(soubory.size());
        for (Resource soubor : soubory) {
            String jmenoSouboru = soubor.getFilename();
            Matcher matcher = vzorNazvu.matcher(jmenoSouboru);
            if (matcher.find()) {
                paths.add(soubor.getFilename());
            }
        }
        return paths;
    }
}
