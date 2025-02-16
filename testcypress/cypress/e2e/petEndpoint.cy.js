describe("Validate the pet methods", () => {
    beforeEach("authentication", () => {
        cy.login();
    });

    const createPet = (name, age) => {
        return cy.request({
            method: "POST",
            url: "http://localhost:8080/api/pet",
            body: {
                name: name,
                age: age,
            },
        });
    };

    it("POST: create_valid pet_succeeds", () => {
        createPet("popi", 5).then((response) => {
            expect(response.status).to.eq(200);
            expect(response.body.id).to.not.eq(null);
            expect(response.body.name).to.eq("popi");
            expect(response.statusText).to.eq("OK");
        });
    });

    it("Get: should get all pets", () => {
        createPet("Manchis", 10);
        createPet("Rayis", 4);
        createPet("Popi", 8);
        cy.request({
            method: "GET",
            url: "http://localhost:8080/api/pet",
        }).then((response) => {
            expect(response.status).to.eq(200);
            expect(response).to.not.empty;
        });
    });

    it("Get: should get a pet by id",  () => {
        createPet("pepino", 2).then((response) => {
            cy.request({
                method: "GET",
                url: "http://localhost:8080/api/pet/" + response.body.id,
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body.name).to.eq("pepino");
                expect(response.body.age).to.eq(2);
            });
        });
    });

    it("PUT: update pet_name and pet_age and pet_updates", () => {
        createPet("popi", 6).then((response) => {
            const updatedPetData = {
                name: "papitas",
                age: 12,
            };
            cy.request({
                method: "PUT",
                url: "http://localhost:8080/api/pet/" + response.body.id,
                body: updatedPetData,
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body.name).to.eq("papitas");
                expect(response.body.age).to.eq(12);
            });
        });
    });

    it("PATCH: update pet_name and pet_updates", () => {
        createPet("tati").then((response) => {
            const updatePetPartially = {
                name: "patas"
            }
            cy.request({
                method: "PATCH",
                url: "http://localhost:8080/api/pet/" + response.body.id,
                body: updatePetPartially,
            }).then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body.name).to.eq("patas");
            });
        });
    });

    it("DELETE: deletes an existing pet", () => {
        createPet("popito", 10).then((response) => {
            cy.request({
                method: "DELETE",
                url: "http://localhost:8080/api/pet/" + response.body.id,
            }).then((response) => {
                expect(response.status).to.eq(200);
            });
        });
    });
});
