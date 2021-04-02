it('Create a Driver Type', function () {
    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")
    
    cy.get("input#submit").click()

    cy.contains('Driver Types').click()

    cy.contains('Add Driver Type').click()

    cy.get('input[name="code"]').type("CYPTESTcodeNode11")

    cy.get('textarea[name="description"]').type("Motorista de Autocarros CP")

    cy.contains('Submit').click()
})