it('Import a file', function () {
    cy.viewport(1920,1080)

    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Import').click()

    cy.get('input[type="file"]').click()

    //cy.contains('Submit').click()
})