it('Create a Path', function () {
    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Best Path').click()

    cy.get('input[name="initialNode"]').type("Node:11")

    cy.get('input[name="finalNode"]').type("Node:12")

    cy.get('input[name="schedule"]').type(2)

    cy.contains('Submit').click()
})