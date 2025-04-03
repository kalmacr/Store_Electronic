package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoRepository.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    //se crean los metodos para un CRUD Create Reaqd Update Delete
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        producto = productoRepository.findById(producto.getIdProducto()).orElse(null);
        return producto;
    }

    @Transactional
    public void delete(Producto producto) {
        //elimina el registro que tiene el idProducto pasado en el objeto producto
        productoRepository.delete(producto);

    }

    @Transactional
    public void save(Producto producto) {
        //si el idProducto tiene un valor... se actualiza el registro de ese idProducto
        //si el idProducto no tiene valor.. se inserta un registro con la informacion
        productoRepository.save(producto);

    }
    //Metodo Consulta ampliada
    @Transactional(readOnly = true)
    public List<Producto> consultaAmpliada(double precioInf,double precioSup) {
        
        return productoRepository.findByPrecioBetweenOrderByPrecio(precioInf, precioSup);
    }
    //Metodo Consulta JPQL
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf,double precioSup) {
        
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
    //Metodo Consulta SQL
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf,double precioSup) {
        
        return productoRepository.consultaSQL(precioInf, precioSup);
    }

}
