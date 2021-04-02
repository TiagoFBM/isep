it('Create a Node', function () {
    cy.viewport(1920,1080)

    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Node').click()

    cy.contains('Add Node').click()

    cy.get('input[name="code"]').type("CYPTESTcodeNode11")

    cy.get('input[name="latitude"]').type("41.1937898023744")

    cy.get('input[name="longitude"]').type("-8.38716802227697")

    cy.get('input[name="name"]').type("Baltar")

    cy.get('input[name="shortName"]').type("BALTR")

    cy.get('input[name="isDepot"]').check()

    cy.get('input[name="isReliefPoint"]').check()

    cy.get('select[name="node"]').type("CYPTESTcodeNode11")

    cy.get('input[name="duration"]').type(120)

    cy.contains('Submit').click()
})