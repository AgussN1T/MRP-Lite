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

Given("que se ingresa el nuevo taller con {string} y {string}", function (codigo, nombre) {
    this.taller = JSON.stringify({
        "codigo": codigo,
        "nombre": nombre
    });
});

Given('que existen los talleres cuando se agrega el equipo con {string} del {string} y {int} al taller {string}',
    function (codigoEquipo, tipoDeEquipo, capacidad, codigoTaller) {
        let tallerReq = request('GET', `http://backend:8080/talleres/codigo/${codigoTaller}`);
        let tipoEquipoReq = request('GET', `http://backend:8080/tipoequipos/nombre/${tipoDeEquipo}`);

        this.taller1 = JSON.parse(tallerReq.body, 'utf8');
        this.tipoEquipo = JSON.parse(tipoEquipoReq.body, 'utf8');
        
        this.taller2 = {
            id: this.taller1.data.id,
            codigo: codigoTaller,
            nombre: this.taller1.data.nombre,
            equipos: this.taller1.data.equipos
        };

        this.taller2.equipos.push({
            codigo: codigoEquipo,
            capacidad: capacidad,
            tipoEquipo: {
                id: this.tipoEquipo.data.id
            }
        });
    }

);

When("presiono el botón de guardar taller", function () {
    this.response = hacerRequestHttp('POST', 'http://backend:8080/talleres', this.taller);
});

When("presiono el botón de actualizar taller", function () {
    let res = request('PUT', 'http://backend:8080/talleres', {
        json: this.taller2
    });
    this.response = JSON.parse(res.body, 'utf8');
});
