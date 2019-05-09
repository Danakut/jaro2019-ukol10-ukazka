package cz.czechitas.webapp;

import java.io.IOException;
import java.sql.*;
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

    private PanenkaRepository panenkaRepository = new PanenkaRepository();

    @RequestMapping("/")
    public ModelAndView zobrazIndex() throws SQLException {
        return new ModelAndView("index");
    }

    @RequestMapping("/seznam.html")
    public ModelAndView zobrazSeznam() throws SQLException {
        return new ModelAndView("seznam");
    }

    @RequestMapping("/hledat.html")
    public ModelAndView zobrazHledani() throws SQLException {
        return new ModelAndView("hledat");
    }

    @RequestMapping("/nova.html")
    public ModelAndView zobrazNova() throws SQLException {
        ModelAndView moavi = new ModelAndView("nova");
        moavi.addObject("seznamVrsku", topPaths);
        moavi.addObject("seznamSpodku", bottomPaths);
        return moavi;
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
            //throw Exception?
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
