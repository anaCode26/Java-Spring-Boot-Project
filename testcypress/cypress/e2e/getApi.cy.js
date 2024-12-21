describe("template spec", () => {
    it("get owner and pets", () => {
      cy.request({
        method: "GET",
        url: "http://localhost:8080/owner/1",
      }).then((response) => {
        expect(response.status).to.eq(200);
      });
    }); 

    it.skip("create_valid pet_succeeds", () => {
      cy.request({
        method: "POST",
        url: "http://localhost:8080/pet",
        body: {
          name: "tititi",
          age: "2",
        },
      }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body.name).to.eq("tititi");
        expect(response.statusText).to.eq("OK");
      });
    });

    it.skip("update pet_name and age_updates", () => {
      cy.request({
        method: "PUT",
        url: "http://localhost:8080/pet/15",
        body: {
          name: "Angel",
          age: "4",
        },
      }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body.name).to.eq("Angel");
      });
    });

    it("PATCH request", () => {
      cy.request({
        method: "PATCH",
        url: "http://localhost:8080/pet/13",
        body: {
          name: "Tatiti",
        },
      }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body.name).to.eq("Tatiti");
      });
    });

    it("DELETE request", () => {
      cy.request({ method: "DELETE", 
        url: "http://localhost:8080/pet/12"}).then(
        (response) => {
          expect(response.status).to.eq(200);
        }
      );
    });
  });