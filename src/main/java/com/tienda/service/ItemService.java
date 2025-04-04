
package com.tienda.service;

import com.tienda.domain.Item;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    //Se utiliza variable d session para guardar una lista
    
    @Autowired
    private HttpSession session;
    
    //Metodo para crear un item en la variable de session
    //Si no existe se crea
    public void save(Item item){
        //se recupera ñla variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        
        //se valida si la lista ya estaba como variable de session
        if(lista==null){
            lista = new ArrayList<>();
            
        }
        
        //Se busca s iel idProducto ya esta en la lista..
        boolean existe = false;
        for(Item i : lista){
            if (Objects.equals(item.getIdProducto(), i.getIdProducto())){
                existe = true;
                if(i.getCantidad()<i.getExistencias()){
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
                
            }
        }
        if (!existe){ //Si no estaba en la lista se crea en ella
            item.setCantidad(1);
            lista.add(item);
            
        }
        session.setAttribute("listaItems", lista);
    }
    
    //Metodo para recupera un item en la variable de session
    //Si no está retorna null
    public Item getItem(Item item){
        //se recupera ñla variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        
        //si la lista no estaba como variable de session, no hay items
        if(lista==null){
            return null;
            
        }
        
        //Se busca s iel idProducto ya esta en la lista..
        for(Item i : lista){
            if (Objects.equals(item.getIdProducto(), i.getIdProducto())){
                return i;
            }
        }
        return null;
    }
    
    //recupera el total de compra segun la lista
    public double getTotal(){
        //se recupera la variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        
        //si la lista no estaba como variable de session, no hay items
        if(lista==null){
            return 0;
            
        }
        
        //Se busca s iel idProducto ya esta en la lista..
        double total=0;
        for(Item i : lista){
            total += i.getCantidad() * i.getPrecio();
        }
        return total;
    }
    
    //recupera la lista completa desde la variabe de session
    public List<Item> getItems(){
        //se recupera ñla variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        
        return lista;
        }
        
        
    
}
