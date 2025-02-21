//esta funcion carga uan imagen local en la pagina html
function readURL(input){
    if(input.files && input.files[0]) {
        //nos paseçaron un archivo de imagen
        var lector = new FileReader();
        lector.onload = function(e){
          $('#blah').attr('src',e.target.result)
                  .height(200);
        };
        lector.readAsDataURL(input.files[0]);
        
    }
        
}

