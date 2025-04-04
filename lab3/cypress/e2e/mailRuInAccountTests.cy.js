const data = require('e.mail.ru.cookies.json')

const sendToMyself = () => {
  cy.get("span").contains("Написать письмо").click()
  cy.get(".container--hmD9c input").type("iwan.tarasow2013@gmail.com")
  cy.get(".container--3QXHv").type("Тестовая тема")
  cy.get("span").contains("Отправить").click()
  cy.get(".popup--2yZiz").contains("Отправить").click()
  cy.get(".ico.ico_16-close.ico_size_s").click()
}

describe('email testing', () => {
  beforeEach(() => {
    cy.session('e-mail-session', () => {

      cy.fixture('e.mail.ru.cookies.json').then((cookies) => {
        cookies.forEach((cookie) => {
          cy.setCookie(cookie.name, cookie.value);
        });
      });
    });
  });

  it('createFolder', () => {
    cy.visit('https://e.mail.ru/inbox');
    cy.viewport(1920, 1080)
    // cy.get("div").contains("Новая папка").click()
    // cy.get("input[placeholder='Название']").type("cypress")
    // cy.get("span").contains("Добавить папку").click()
    // sendToMyself()
    //
    // cy.get("div").contains("Отправленные").click()
    // cy.get("span").contains("Тестовая тема").click()
    // cy.get("div").contains("В папку").click()
    // cy.get(".list-item__text").contains("cypress").click()
    // cy.get("div").contains("cypress").click()
    // cy.get("span").contains("Тестовая тема").should("be.visible")
    // cy.get("div").contains("cypress").click()
    console.log(cy.getCookie('Mpop'));
    cy.getCookie('Mpop').should("exist")
  })

  it("joj", () => {
    // cy.get("div").contains("cypress").click()
    console.log(cy.getCookie('Mpop'));
    cy.getCookie('Mpop').should("exist")
  })
})