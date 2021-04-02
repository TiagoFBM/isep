it('Create a Vehicle', function () {

    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Genetic Algorithm').click()

    cy.get('input[name="vehicleName"]').type('12')

    cy.get('input[name="generationNumber"]').type('2')

    cy.get('input[name="dimension"]').type('2')

    cy.get('input[name="crossingProbability"]').type('2')

    cy.get('input[name="mutationProbability"]').type('2')

    cy.get('input[name="generate"]')
    
})