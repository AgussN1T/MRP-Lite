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

Given("que se ingresa el cliente con {string} y {int}", function (razonSocial, cuit) {
    this.cliente = JSON.stringify({
        "razonSocial": razonSocial,
        "cuit": cuit
    });
});

When("presiono el botón de guardar clientes", function () {
    this.response = hacerRequestHttp('POST', 'http://backend:8080/clientes', this.cliente);
});