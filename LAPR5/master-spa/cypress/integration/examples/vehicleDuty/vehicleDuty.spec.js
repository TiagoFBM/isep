it('Create a Vehicle', function () {

    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains(/^Vehicle Duty$/).click()

    cy.contains('Add Vehicle Duty').click()

    cy.get('input[name="code"]').type('vehicleDutyCode1')
    
    cy.get('select[name="trip"]')

    cy.get('textarea[name="paths"]')
    
    cy.contains('Submit').click()
})