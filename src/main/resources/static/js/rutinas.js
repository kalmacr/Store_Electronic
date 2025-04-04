//esta funcion carga uan imagen local en la pagina html
function readURL(input) {
    if (input.files && input.files[0]) {
        //nos paseçaron un archivo de imagen
        var lector = new FileReader();
        lector.onload = function (e) {
            $('#blah').attr('src', e.target.result)
                    .height(200);
        };
        lector.readAsDataURL(input.files[0]);

    }

}

// Esta funcion carga el carrito de compras un producto
function addCart(formulario) {
    
    var idProducto = formulario.elements[0].value;
    var existencias = formulario.elements[1].value;
    if (existencias > 0) {
        //Se incluyen el producto en el carrito
        var ruta = "/carrito/agregar/" + idProducto;
        //window.alert("La ruta es:" + ruta);
        $("#resultBlock").load(ruta);
        
    } else {
        window.alert("No hay existencias..");
    }

}