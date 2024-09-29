const assert = require('assert');
const { Then } = require('cucumber');


Then("se espera el siguiente {int} con la {string}", function (status, respuesta) {
    assert.equal(this.response.status, status);
    assert.equal(this.response.message, respuesta);
});
