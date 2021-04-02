it('Create a Vehicle', function () {

    cy.viewport(1920,1080)
    
    cy.visit("http://localhost:3000/api/NavBar.js")

    cy.get('input[name="email"]').type("email1@gmail.com")

    cy.get('input[name="password"]').type("password1")

    cy.get("input#submit").click()

    cy.contains(/^Driver Duty$/).click()

    cy.contains('Add Driver Duty').click()

    cy.get('input[name="code"]').type('driverDutyCode1')

    cy.get('select[name="vehicleDuty"]')
    
    cy.get('select[name="workblock"]')

    cy.get('textarea[name="workblocks"]')
    
    cy.contains('Submit').click()
})