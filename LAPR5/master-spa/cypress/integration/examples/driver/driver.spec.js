it('Create a Driver', function () {
    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains(/^Driver$/).click()

    cy.contains('Add Driver').click()

    cy.get('input[name="mecanographicNumber"]').type('1181012')

    cy.get('input[name="driverName"]').type('Jose')

    cy.get('input[name="birthDate"]')

    cy.get('input[name="citizenCardNumber"]').type(15153436)

    cy.get('input[name="driverNIF"]').type(12345678)
    
    cy.get('input[name="entranceDate"]')

    cy.get('input[name="departureDate"]')

    cy.get('input[name="numberDriverLicense"]').type('1181012')

    cy.get('input[name="dateDriverLicense"]')

    cy.get('select[name="driverType"]')

    cy.get('textarea[name="paths"]')
    
    cy.contains('Submit').click()
})