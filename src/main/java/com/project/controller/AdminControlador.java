package com.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.Entity.Genero;
import com.project.Entity.Libro;
import com.project.Repository.GeneroRepository;
import com.project.Repository.LibroRepository;
import com.project.Service.AlmacenServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private AlmacenServiceImpl servicio;
	
	
	@GetMapping("")
	public ModelAndView verPaginaDeInicio(@PageableDefault( size = 5,page=0) Pageable pageable, Model model) {
		Page<Libro> page = libroRepository.findAll(PageRequest.of(pageable.getPageNumber(),pageable.getPageSize()));
		
		model.addAttribute("page", page);
		var totalPages = page.getTotalPages();
		var currentPage = page.getNumber();
		
		var start = Math.max(1, currentPage);
		var end = Math.min(currentPage + 5, totalPages);
		
		if (totalPages > 0) {
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = start; i <= end; i++) {
				pageNumbers.add(i);
			}
			
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		
		List<Integer> pageSizeOptions = Arrays.asList(10,20, 50, 100);
		model.addAttribute("pageSizeOptions", pageSizeOptions);
		return new ModelAndView("admin/index").addObject("page", page);
	}
	
	@GetMapping("/libros/nuevo")
	public ModelAndView mostrarFormularioDeNuevaPelicula() {
		List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));
		return new ModelAndView("admin/nuevo-libro")
				.addObject("libro", new Libro())
				.addObject("generos", generos);
	}
	
	@PostMapping("/libros/nuevo")
	public ModelAndView registarLibros(@Validated Libro libro, BindingResult bindingResult ) {
		if(bindingResult.hasErrors() || libro.getPortada().isEmpty()) {
			if(libro.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}
			List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/nuevo-libro")
					.addObject("libro", libro)
					.addObject("generos", generos);
		}
		String rutaPortada = servicio.almacenarArchivo(libro.getPortada());
		libro.setRutaPortada(rutaPortada);
		
		libroRepository.save(libro);
		return new ModelAndView("redirect:/admin");
	}
	
	@GetMapping("/libros/{id}/editar")
	public ModelAndView mostrarEditarLibros(@PathVariable Integer id) {
		Libro libro = libroRepository.getOne(id);
		List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));
		
		return new ModelAndView("admin/editar-libro")
				.addObject("libro", libro)
				.addObject("generos", generos);
	}
	
	@PostMapping("/libros/{id}/editar")
	public ModelAndView actualizarLibro(@PathVariable Integer id,@Validated Libro libro, BindingResult bindingResult ) {
		if(bindingResult.hasErrors()) {
			List<Genero> generos = generoRepository.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/editar-libro")
					.addObject("libro", libro)
					.addObject("generos", generos);
		}
		Libro libroDB = libroRepository.getOne(id);
		libroDB.setTitulo(libro.getTitulo());
		libroDB.setDescripcion(libro.getDescripcion());
		libroDB.setFechaPublicacion(libro.getFechaPublicacion());
		libroDB.setLibroLink(libro.getLibroLink());
		libroDB.setGeneros(libro.getGeneros());
		
		if(!libro.getPortada().isEmpty()) {
			servicio.eliminarArchivo(libroDB.getRutaPortada());
			String rutaPortada = servicio.almacenarArchivo(libro.getPortada());
			libroDB.setRutaPortada(rutaPortada);
		}
		libroRepository.save(libroDB);
		return new ModelAndView("redirect:/admin");
		
	}
	
	@PostMapping("/libros/{id}/eliminar")
	public String eliminarLIbro(@PathVariable Integer id) {
		Libro libro=libroRepository.getOne(id);
		libroRepository.delete(libro);
		servicio.eliminarArchivo(libro.getRutaPortada());
		
		return "redirect:/admin";
	}
}
