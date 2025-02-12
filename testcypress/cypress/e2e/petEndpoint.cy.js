describe("Validate the pet methods", () => {
    beforeEach("authentication", () => {
        cy.login();
    });

    it("Get: should get all pets", () => {
        cy.request({
            method: "GET",
            url: "http://localhost:8080/api/pet",
        }).then((response) => {
            expect(response.status).to.eq(200);
        });
    });

    it("Get: should get a pet by id", () => {
        cy.request({
            method: "GET",
            url: "http://localhost:8080/api/pet/1",
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.name).to.eq("manchis");
            expect(response.body.age).to.eq(8);
        });
    });

    it("POST: create_valid pet_succeeds", () => {
        cy.request({
            method: "POST",
            url: "http://localhost:8080/api/pet",
            body: {
                name: "popi",
                age: "2",
            },
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.name).to.eq("popi");
            expect(response.statusText).to.eq("OK");
        });
    });

    it("PUT: update pet_name and pet_age and pet_updates", () => {
        cy.request({
            method: "PUT",
            url: "http://localhost:8080/api/pet/1",
            body: {
                name: "manchis",
                age: "8",
            },
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.name).to.eq("manchis");
            expect(response.body.age).to.eq(8)
        });
    });

    it("PATCH: update pet_name and pet_updates", () => {
        cy.request({
            method: "PATCH",
            url: "http://localhost:8080/api/pet/4",
            body: {
                name: "tati",
            },
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.name).to.eq("tati");
        });
    });

    it("DELETE: deletes an existing pet", () => {
        cy.request({ method: "DELETE",
            url: "http://localhost:8080/api/pet/6"}).then(
            (response) => {
                expect(response.status).to.eq(200);
            }
        );
    });
});