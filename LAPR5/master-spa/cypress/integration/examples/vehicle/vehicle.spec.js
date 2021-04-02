it('Create a Vehicle', function () {

    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains(/^Vehicle$/).click()

    cy.contains('Add Vehicle').click()

    cy.get('input[name="registration"]').type('AA11AA')

    cy.get('input[name="vin"]').type('123456789aaaaaaa')
    
    cy.get('input[name="entranceDate"]')

    cy.get('select[name="vehicleType"]')
    
    cy.contains('Submit').click()
})