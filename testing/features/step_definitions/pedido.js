const assert = require('assert');
const { Given, When } = require('cucumber');
const request = require('sync-request');

const { defineParameterType } = require('cucumber');

defineParameterType({
  name: 'Date',
  regexp: /\d{4}-\d{2}-\d{2}/,
  transformer: string => new Date(string)
});



Given('que existe el {string}', function (nombreProducto) {
    let productoReq = request('GET', encodeURI(`http://backend:8080/productos/nombre/${nombreProducto}`));
    this.producto = JSON.parse(productoReq.body, 'utf8').data;
});

Given('el cliente con {int}', function (cuitCliente) {
    let clienteReq = request('GET',`http://backend:8080/clientes/cuit/${cuitCliente}`);
    this.cliente = JSON.parse(clienteReq.body, 'utf8').data;
});

When('se solicita generar un pedido al cliente {int} con fecha de pedido {string} para entregar en la fecha {string} la cantidad de {int} del producto {string}', function (cuitCliente, nuevaFechaPedido, nuevaFechaEntrega, cantidad, nombreProducto) {
   
    const fechaPedido = new Date(nuevaFechaPedido);
    const fechaEntrega = new Date(nuevaFechaEntrega);
    this.pedido = {
        id: 0,
        fechaPedido: fechaPedido,
        fechaEntrega: fechaEntrega,
        cantidad: cantidad,
        cliente: this.cliente,
        producto: this.producto
    }
    let res = request('POST', 'http://backend:8080/pedidos', {
        json: this.pedido
    });
    this.response = JSON.parse(res.body, 'utf8');
})