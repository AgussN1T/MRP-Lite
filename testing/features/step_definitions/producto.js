const assert = require('assert');
const { Given, When } = require('cucumber');
const request = require('sync-request');


function hacerRequestHttp(method, url, body) {
    return JSON.parse(request(
        method,
        url,
        {
            body: body,
            headers: {
                'Content-Type': 'application/json'
            }
        }
    ).getBody('utf8'));
}

Given('que se ingresa el nuevo producto con {string}', function (nombre) {
    this.producto = JSON.stringify({
        "id": 0,
        "nombre": nombre
    });
});

Given('que existen los productos cuando se agrega la tarea con {string}, {int}, {int} y {string} para el producto {string}', function (nombreTarea, ordenTarea, tiempoTarea, tipoEquipoTarea, nombreProducto) {
    
    let productoReq = request('GET', encodeURI(`http://backend:8080/productos/nombre/${nombreProducto}`));
    let tipoEquipoReq = request('GET', encodeURI(`http://backend:8080/tipoequipos/nombre/${tipoEquipoTarea}`));
    
    this.producto = JSON.parse(productoReq.body, 'utf8').data;
    this.tipoEquipo = JSON.parse(tipoEquipoReq.body, 'utf8').data;
   
    this.producto.tareas.push({
        nombre: nombreTarea,
        orden: ordenTarea,
        tiempo: tiempoTarea,
        tipoEquipo: {
            id: this.tipoEquipo.id
        }
    })


});


When("presiono el botón de guardar producto", function () {
    this.response = hacerRequestHttp('POST', 'http://backend:8080/productos', this.producto);
});


When("presiono el botón de actualizar producto", function(){
    let res = request('PUT', 'http://backend:8080/productos', {
        json: this.producto
    });
    this.response = JSON.parse(res.body, 'utf8');
    
})


