describe("update pet_owner", () => {
    it.skip("validate update pet_owner_id", () => {
      cy.request({
        method: "PUT",
        url: "http://localhost:8080/owner/1/pet/11",
      }).then((response) => {
        expect(response.status).to.eq(200);
      });
    });

    it("validate with owner_id invalid", () => {
        cy.request({
          method: "PUT",
          url: "http://localhost:8080/owner/2/pet/11",
        }).then((response) => {
          expect(response.status).to.eq(200);
        });
      });
});