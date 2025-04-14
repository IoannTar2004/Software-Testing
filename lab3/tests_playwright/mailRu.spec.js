// @ts-check
import {expect, test} from '@playwright/test';

const sendToMyself = async (page) => {
  await page.getByText("Написать письмо").click()
  await page.locator(".container--hmD9c input").fill("iwan.tarasow2004.st@mail.ru")
  await page.locator("input[name='Subject']").fill("Тестовая тема")
  await page.getByText("Отправить").click()
  await page.locator("div[data-test-id='confirmation\\:empty-letter'] ")
      .getByRole('button', { name: 'Отправить' }).click()
  await page.locator(".ico.ico_16-close.ico_size_s").click()
}

const clickOnFrame = async (page, button, frame) => {
  await page.frameLocator('[data-test-id="mail-widgets-button-frame"]')
      .locator(".WidgetButton_container__85TTS").click();
  const frameMain = page.frameLocator("[data-test-id='mail-widgets-main-frame']")
  await frameMain.locator(`button[title='${button}']`).click()

  return frameMain.frameLocator(`iframe[src*='${frame}']`)
}

test.beforeEach(async ({page}) => {
  await page.goto('/');
  await page.locator(".balloon-limbs__close.svelte-1mdtsha").click()
})

// test("create folder", async ({page}) => {
//   const folder = page.locator(".nav__folder-name__txt").getByText("cypress")
//
//   await page.getByText("Новая папка").click()
//   await page.locator("input[placeholder='Название']").fill("cypress")
//   await page.getByText("Добавить папку").click()
//   await sendToMyself(page)
//
//   await page.getByText("Письма себе").click()
//   await page.getByText("Тестовая тема").first().click({force: true})
//   await page.locator(".new-menu.content-header").getByText("В папку").click()
//   await page.locator(".list-item__text").getByText("cypress").click()
//   await folder.click()
//   await expect(page.getByText("Тестовая тема").first()).toBeVisible()
//
//   await folder.click({button: "right"})
//   await page.getByText("Удалить папку").click()
//   await page.locator("div.button2__txt").getByText("Удалить").click()
// })

// test("move to groups", async ({page}) => {
//   const groups = ["Социальные сети", "Рассылки", "Госписьма", "Новости", "Чеки"]
//   await sendToMyself(page)
//
//   await page.locator(".nav__folder-name__txt").getByText("Письма себе").click()
//   for (const group of groups) {
//     await page.getByText("Тестовая тема").first().click()
//     await page.locator(".new-menu.content-header").getByText("В папку").click()
//     await page.locator(".list-item__text").getByText(group).click()
//     await page.locator(".JdaB0qsX5i3OC71Q8VT0n").click()
//     await page.locator(".nav__folder-name__txt").getByText(group).click()
//     await expect(page.getByText("Тестовая тема").first()).toBeVisible()
//   }
// })

// test("add new holidays", async ({page}) => {
//   const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
//   await frameContent.locator("[data-test-id='sidebar-button']").click()
//   await frameContent.locator("[data-test-id='sidebar-create-calendar-HOLIDAYS']").click()
//   await frameContent.getByText("Праздники разных стран").click()
//   await frameContent.getByText("Праздники России", { exact: true }).click()
//   await frameContent.getByText("Сохранить").click()
//
//   await frameContent.locator("[data-test-id='sidebar-button']").click()
//   await expect(frameContent.getByText("Праздники России")).toBeVisible()
//   await frameContent.locator("[data-test-id='sidebar-create-calendar-HOLIDAYS']").click()
//   await frameContent.getByText("Праздники разных стран").click()
//   await frameContent.locator("[data-test-id='public-calendars-calendar-title']")
//       .getByText("Праздники России", { exact: true }).click()
//   await frameContent.getByText("Сохранить").click()
// })

// test("create new event", async ({page}) => {
//   const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
//   await frameContent.getByText("Новое событие").click()
//
//   await frameContent.locator("textarea[placeholder='Придумайте название']").fill("playwright")
//   await frameContent.locator("[data-test-id='dateNativeStart-native-date-time-input'] input").fill("2025-04-16")
//   await frameContent.locator("[data-test-id='dateNativeEnd-native-date-time-input'] input").fill("2025-04-17")
//   await frameContent.locator("[data-test-id='timeStart-input']").fill("20:00")
//   await frameContent.locator("[data-test-id='timeEnd-input']").fill("21:00")
//   await frameContent.getByText("Цвет события").click()
//   await frameContent.locator("[data-test-id='event-color-button-G02']").click()
//   await frameContent.getByText("Сохранить").click()
//
//   await expect(frameContent.locator("[data-test-id='date-16']").getByText("playwright")).toBeVisible()
//   await expect(frameContent.locator("[data-test-id='date-17']").getByText("playwright")).toBeVisible()
//   await frameContent.locator("[data-test-id='date-16']").getByText("playwright").click()
//   await frameContent.locator("[data-test-id='more_options-button']").click()
//   await frameContent.getByText("Удалить").click()
// })

// test("holidays on", async ({page}) => {
//   const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
//   await frameContent.locator("[data-test-id='sidebar-button']").click()
//
//   const checkbox = frameContent.locator("[data-test-id*='sidebar-calendar-checkbox-#EAF277']")
//   await checkbox.click()
//   await checkbox.click()
//   await frameContent.locator("[data-test-id='cross-button']").click()
//   await expect(frameContent.getByText("Великий пост").first()).toBeVisible()
// })

test("create new note", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Заметки", "https://notes.mail.ru")
  await frameContent.getByText("Новая заметка").click()

  await frameContent.locator("div[contenteditable='true'] h1").fill("Playwright")
  await frameContent.locator("p[data-placeholder='Напишите заметку']").click()
  await frameContent.locator("button[aria-label='Заголовок 1']").click()
  await frameContent.locator("div[contenteditable='true'] h2").fill("Заголовок 1\n")

  await frameContent.locator("button[aria-label='Заголовок 2']").click()
  await frameContent.locator("div[contenteditable='true'] h3").fill("Заголовок 2\n")
  await frameContent.locator("div[contenteditable='true'] p").fill("Текст\n")

  await frameContent.locator("button[aria-label='Список дел']").click()
  await frameContent.locator("ul[data-type='taskList']").fill("чекбокс 1\nчекбокс 2")

  await frameContent.locator(".custom-icon").first().click()
  await expect(frameContent.locator(".custom-icon")).toHaveCount(2)

  await frameContent.locator("button[aria-label='Таблица']").click()
  for (let row = 1; row <= 2; row++)
    for (let col = 1; col <= 2; col++)
      await frameContent.locator(`div[contenteditable='true'] table tr:nth-of-type(${row}) td:nth-of-type(${col}) p`)
          .fill(`${row}.${col}`)

  await frameContent.locator("button[aria-label='Назад']").click()
  await frameContent.locator("[data-test-id='notes-list']").getByText("Playwright").click({button: "right"})
  await frameContent.getByText("Удалить заметку").click()
  await frameContent.getByText("Удалить", {exact: true}).click()
  // await page.waitForTimeout(10000)
})