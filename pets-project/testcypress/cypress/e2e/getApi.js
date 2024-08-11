describe("Test Methods", () => {
    it("passes", () => {
        cy.request("http://localhost:8080/").then((response) => {
            expect(response.status).to.eq(200);
        });
      });
})