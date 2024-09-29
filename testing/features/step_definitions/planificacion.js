const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const assert = require('assert');


Given('el {string}', function (nombreProducto) {
    this.nombreProducto = nombreProducto;
});
Given('la lista de talleres ordenada alfabéticamente por código', function () {
    //let req = request('GET', 'http://backend:8080/taller/ordenados')
    //this.talleres=JSON.parse(req.body, 'utf8').data;
});

When('se solicita recuperar el primer taller que cumple con la condición de fabricación', function () {
    let req = request('GET', encodeURI(`http://backend:8080/productos/recuperarTallerPertinente/${this.nombreProducto}`))
    this.actualAnswer = JSON.parse(req.body, 'utf8');
});

When('se solicita recuperar la lista de talleres que cumplen con la condición de fabricación', function () {
    let req = request('GET', encodeURI(`http://backend:8080/productos/recuperarTalleresPertinentes/${this.nombreProducto}`))
    this.actualAnswer = JSON.parse(req.body, 'utf8');
});



Then('se obtiene el {string} con {int} y {string}', function (taller, status, respuesta) {
    if (this.actualAnswer.data != null) {

        assert.equal(this.actualAnswer.data.codigo, taller);
    }
    assert.equal(this.actualAnswer.status, status);
    assert.equal(this.actualAnswer.message, respuesta);

});
Then('se obtiene la lista de {string} con {int} y {string}', function (talleres, status, respuesta) {

    talleres = talleres.replace('[', '').replace(']', '');
    const arrayTalleresFeature = talleres.split(',').map(taller => taller.trim());

    if (this.actualAnswer.data != null) {
        const codigosTalleres = this.actualAnswer.data.map(taller => taller.codigo);
        assert.deepEqual(arrayTalleresFeature, codigosTalleres);
    }

    assert.equal(this.actualAnswer.status, status);
    assert.equal(this.actualAnswer.message, respuesta);

});