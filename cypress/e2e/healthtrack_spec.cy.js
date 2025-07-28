describe('Prueba Funcional de HealthTrack', () => {

  it('Un usuario debe poder actualizar su peso en la interfaz', () => {
    // 1. Visitar nuestra página local. Recuerda tener el archivo index.html
    // en la raíz de tu proyecto.
    cy.visit('./index.html');

    // 2. Encontrar el campo de entrada, limpiarlo y escribir el nuevo peso
    cy.get('#peso-input').clear().type('78.5');

    // 3. Hacer clic en el botón "Guardar"
    cy.get('#guardar-peso').click();

    // 4. VERIFICAR: El texto del peso actual debe haber cambiado
    cy.get('#display-peso').should('contain.text', '78.5 kg');
  });

});