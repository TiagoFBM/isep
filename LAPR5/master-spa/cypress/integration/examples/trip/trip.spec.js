it('Create a Trip', function () {
    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Trip').click()

    cy.contains('Add Trips').click()

    cy.get('select[name="line"]')

    cy.get('select[name="path"]')

    cy.get('select[name="path"]')

    cy.get('input[name="departTime"]')

    cy.get('input[name="frequency"]').type(20)

    cy.get('input[name="numberOfTrips"]').type(1234)

    cy.contains('Submit').click()
})