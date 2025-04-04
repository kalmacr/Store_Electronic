
package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.service.ItemService;
import com.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping("/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item) {
        Item item2 = itemService.getItem(item);
        
        //Si no existe el item en el carrito, se busca la info del producto
        if(item2==null){
            Producto p = productoService.getProducto(item);
            item2 = new Item(p);
        }
        //finalmente se guarda en la variable de session el producto del carrito
        itemService.save(item2);
        
        //Se crea elemento para mostrar en el fragmento "verCarrito"
        var lista = itemService.getItems();
        var totalCompra = itemService.getTotal();
        
        model.addAttribute("listaItems", lista);
        model.addAttribute("totalCompra", totalCompra);
        model.addAttribute("totalProductos", lista.size());
        
        
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
    
    @GetMapping("/listado")
    public String listado(Model model){
        var lista = itemService.getItems();
        model.addAttribute("listaItems", lista);
        
        return "/carrito/listado";
    }
}
