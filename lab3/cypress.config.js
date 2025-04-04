const { defineConfig } = require("cypress");
const {allureCypress} = require("allure-cypress/reporter");

module.exports = defineConfig({
  defaultCommandTimeout: 15000,
  e2e: {
    baseUrl: "https://e.mail.ru",
    setupNodeEvents(on, config) {
      allureCypress(on, config, {
        resultsDir: "build/allure-results/cypress",
      });
      return config;
    },
    chromeWebSecurity: false,
    experimentalSessionAndOrigin: true
  },
});
