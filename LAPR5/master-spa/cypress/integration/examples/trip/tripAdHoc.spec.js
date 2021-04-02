it('Create a Trip Ad Hoc', function () {
    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Trip').click()

    cy.contains('Add Trip Ad Hoc').click()

    //cy.get('select[name="line"]').select("")

    cy.get('select[name="line"]')

    cy.get('select[name="path"]')

    cy.get('input[name="departTime"]')

    cy.contains('Submit').click()
})