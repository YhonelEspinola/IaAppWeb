package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.Entity.Libro;
import com.project.Repository.LibroRepository;

@Controller
@RequestMapping("")
public class HomeControllador {
	
	@Autowired
    private LibroRepository libroRepository;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio() {

        List<Libro> ultimosLibros = libroRepository.findAll(PageRequest.of(0, 4, Sort.by("fechaPublicacion").descending())).toList();

        return new ModelAndView("index")
                .addObject("ultimosLibros", ultimosLibros);
    }

    @GetMapping("/libros")
    public ModelAndView listarLibros(@PageableDefault(sort = "fechaPublicacion", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Libro> libros = libroRepository.findAll(pageable);

        return new ModelAndView("libros")
                .addObject("libros", libros);
    }

    @GetMapping("/libros/{id}")
    public ModelAndView mostrarDetallesDeLibro(@PathVariable Integer id) {

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

        return new ModelAndView("libro")
                .addObject("libro", libro);
    }
	
}
