package com.project.Entity;

import java.time.LocalDate;


import java.util.List;

import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_libro")
	private Integer id;

	@NotBlank
	private String titulo;

	@NotBlank
	@Column(name = "descripcion", length = 1000)
	private String descripcion;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaPublicacion;

	@NotBlank
	private String libroLink;

	private String rutaPortada;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "genero_libro", 
	joinColumns = @JoinColumn(name = "id_libro"),
	inverseJoinColumns = @JoinColumn(name = "id_genero"))
	private List<Genero> generos;
	
	@Transient
	private MultipartFile portada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getLibroLink() {
		return libroLink;
	}

	public void setLibroLink(String libroLink) {
		this.libroLink = libroLink;
	}

	public String getRutaPortada() {
		return rutaPortada;
	}

	public void setRutaPortada(String rutaPortada) {
		this.rutaPortada = rutaPortada;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public MultipartFile getPortada() {
		return portada;
	}

	public void setPortada(MultipartFile portada) {
		this.portada = portada;
	}

	public Libro(Integer id, @NotBlank String titulo, @NotBlank String descripcion, @NotNull LocalDate fechaPublicacion,
			@NotBlank String libroLink, String rutaPortada, @NotEmpty List<Genero> generos, MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.libroLink = libroLink;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}

	public Libro(@NotBlank String titulo, @NotBlank String descripcion, @NotNull LocalDate fechaPublicacion,
			@NotBlank String libroLink, String rutaPortada, @NotEmpty List<Genero> generos, MultipartFile portada) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.libroLink = libroLink;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}

	public Libro() {
		super();
	}

	
	
	
	
}
