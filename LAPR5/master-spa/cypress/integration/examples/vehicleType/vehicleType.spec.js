it('Create a Vehicle Type', function () {
    cy.viewport(1920,1080)

    cy.visit("http://localhost:3000/api/NavBar.js")
    
    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains('Vehicle Type').click()

    cy.contains('Add Vehicle Type').click()

    cy.get('input[name="code"]').type("CYPTESTcodeVehicle20")

    cy.get('textarea[name="description"]').type("Bus")

    cy.get('input[name="autonomy"]').type(6)

    cy.get('input[name="fuelType"]').type("Gasolina")

    cy.get('input[name="costPerKilometer"]').type(40)

    cy.get('input[name="averageConsuption"]').type(8)

    cy.get('input[name="averageSpeed"]').type(120)


    cy.contains('Submit').click()
})