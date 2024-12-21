describe("update pet_owner_id", () => {
  it.skip("Validate update with valid pet_id and valid owner_id", () => {
    cy.request({
      method: "PUT",
      url: "http://localhost:8080/owner/1/pet/11",
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });

  it("Validate update with valid pet_id and invalid owner_id", () => {
    cy.request({
      method: "PUT",
      url: "http://localhost:8080/owner/9000/pet/11",
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });

  it("Validate update with invalid pet_id and valid owner_id", () => {
    cy.request({
      method: "PUT",
      url: "http://localhost:8080/owner/2/pet/-4",
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });

  it("Validate update with invalid pet_id and invalid owner_id", () => {
    cy.request({
      method: "PUT",
      url: "http://localhost:8080/owner/2000/pet/11000",
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });
});