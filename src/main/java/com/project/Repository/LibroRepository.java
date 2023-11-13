package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.Entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
