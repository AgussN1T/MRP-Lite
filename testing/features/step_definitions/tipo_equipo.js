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

Given("que se ingresa el tipo de equipo con {string}", function (nombre) {
    this.tipoEquipo = JSON.stringify({
        "nombre": nombre
    });
});

When("presiono el botón de guardar", function () {
    this.response = hacerRequestHttp('POST', 'http://backend:8080/tipoequipos', this.tipoEquipo);
});