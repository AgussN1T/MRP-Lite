const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const assert = require('assert');


Given('el pedido con {string} {string} {string} {string} {int}', function (cuit, fechaPedido, fechaEntrega, producto, cantidad) {
    this.nombreProducto = producto;
    this.fechaPedido=fechaPedido;
    this.cantidad = cantidad;
});

Given('el taller para planificar producto {string}', function (taller) {
    this.nombreTaller = taller;
});

Given('tomando como base de planificación del producto la fecha {string} para el producto', function (fecha) {
    this.fecha = fecha;
});

Given('tomando como base de planificación la fecha de entrega {string} para el producto', function (fecha) {
    this.fechaTardia = fecha;
});

When('se solicita planificar con esquema de pronta entrega para el producto', function () {
    let req = request('GET', encodeURI(`http://backend:8080/planificarTest/pedido/${this.nombreProducto}/${this.fecha}/${this.cantidad}`));
    this.actualAnswer = JSON.parse(req.body, 'utf8');
});

When('se solicita planificar con entrega tardía EDF para el producto', function () {
    return 'pending'
   // let req = request('GET', encodeURI(`http://backend:8080/planificarTest/pedido/${this.nombreProducto}/${this.fecha}/${this.cantidad}`));
    //this.actualAnswer = JSON.parse(req.body, 'utf8');
});

Then('se obtiene la {int} para el producto con {int} y {string}', function (planificacion, status, respuesta) {
    assert.equal(this.actualAnswer.status, status);
    assert.equal(this.actualAnswer.message, respuesta);
});
