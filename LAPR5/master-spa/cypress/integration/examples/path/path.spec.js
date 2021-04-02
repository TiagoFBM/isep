it('Create a Path', function () {
    cy.viewport(1920,1080)

    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Path').click()

    cy.contains('Add name').click()

    cy.get('input[name="code"]').type("CYPTESTpath20")

    cy.get('input[name="isEmpty"]').check()

    cy.get('select[name="firstNode"]').select("AGUIA")
    //cy.get('select[name="firstNode"]')
    cy.get('select[name="secondNode"]').select("BESTR")
    //cy.get('select[name="secondNode"]')

    cy.get('input[name="travelTime"]').type(20)

    cy.get('input[name="distance"]').type(1234)

    cy.get('textarea[name="segments"]')

    cy.contains('Submit').click()
})