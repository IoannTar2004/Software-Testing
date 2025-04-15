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

const clickOnFrame = async (page, button, src) => {
  await page.frameLocator('[data-test-id="mail-widgets-button-frame"]')
      .locator(".WidgetButton_container__85TTS").click();
  const frameMain = await page.frameLocator("[data-test-id='mail-widgets-main-frame']")
  await frameMain.locator(`button[title='${button}']`).click()

  return await frameMain.frameLocator(`iframe[src*='${src}']`)
}

const createTask = async (page, frameContent) => {
  await frameContent.locator(".Burger_contrastIcon__DBIMO.Burger_blackIcon__3lwZS").click()

  await frameContent.locator("input[placeholder='Новый список']").fill("Мой список")
  await page.keyboard.press('Enter')
  await frameContent.locator("p").getByText("Мой список").first().click()
  await frameContent.locator("div[placeholder='Добавить описание']").fill("Тестовое описание")
}

test.beforeEach(async ({page}) => {
  await page.goto('/');
  await page.locator(".balloon-limbs__close.svelte-1mdtsha").click()
})

test("Создать папку", async ({page}) => {
  const folder = await page.locator(".nav__folder-name__txt").getByText("cypress")

  await page.getByText("Новая папка").click()
  await page.locator("input[placeholder='Название']").fill("cypress")
  await page.getByText("Добавить папку").click()
  await sendToMyself(page)

  await page.locator(".nav__folder-name__txt").getByText("Письма себе").click()
  await page.waitForTimeout(2000)
  await page.getByText("Тестовая тема").first().click({force: true})
  await page.locator(".new-menu.content-header").getByText("В папку").click()
  await page.locator(".list-item__text").getByText("cypress").click()
  await folder.click()
  await expect(page.getByText("Тестовая тема").first()).toBeVisible()

  await folder.click({button: "right"})
  await page.getByText("Удалить папку").click()
  await page.locator("div.button2__txt").getByText("Удалить").click()
})

test("Переместить в группы", async ({page}) => {
  const groups = ["Социальные сети", "Рассылки", "Госписьма", "Новости", "Чеки"]
  await sendToMyself(page)

  await page.locator(".nav__folder-name__txt").getByText("Письма себе").click()
  await page.waitForTimeout(1000)
  for (const group of groups) {
    await page.getByText("Тестовая тема").first().click()
    await page.locator(".new-menu.content-header").getByText("В папку").click()
    await page.locator(".list-item__text").getByText(group).click()
    await page.waitForTimeout(500)
    await page.locator(".JdaB0qsX5i3OC71Q8VT0n").click()
    await page.locator(".nav__folder-name__txt").getByText(group).click()
    await expect(page.getByText("Тестовая тема").first()).toBeVisible()
  }
})

test("Создать новое событие в календаре", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
  await frameContent.getByText("Новое событие").click()

  await frameContent.locator("textarea[placeholder='Придумайте название']").fill("playwright")
  await frameContent.locator("[data-test-id='dateNativeStart-native-date-time-input'] input").fill("2025-04-16")
  await frameContent.locator("[data-test-id='dateNativeEnd-native-date-time-input'] input").fill("2025-04-17")
  await frameContent.locator("[data-test-id='timeEnd-input']").fill("21:00")
  await frameContent.getByText("Цвет события").click()
  await frameContent.locator("[data-test-id='event-color-button-G02']").click()
  await frameContent.getByText("Сохранить").click()

  await expect(frameContent.locator("[data-test-id='date-16']").getByText("playwright")).toBeVisible()
  await expect(frameContent.locator("[data-test-id='date-17']").getByText("playwright")).toBeVisible()
  await frameContent.locator("[data-test-id='date-16']").getByText("playwright").click()
  await frameContent.locator("[data-test-id='more_options-button']").click()
  await frameContent.getByText("Удалить").click()
})

test("Добавить праздники", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
  await frameContent.locator("[data-test-id='sidebar-button']").click()
  await frameContent.locator("[data-test-id='sidebar-create-calendar-HOLIDAYS']").click()
  await frameContent.getByText("Праздники разных стран").click()
  await frameContent.getByText("Праздники России", { exact: true }).click()
  await frameContent.getByText("Сохранить").click()

  await frameContent.locator("[data-test-id='sidebar-button']").click()
  await expect(frameContent.getByText("Праздники России")).toBeVisible()
  await frameContent.locator("[data-test-id='sidebar-create-calendar-HOLIDAYS']").click()
  await frameContent.getByText("Праздники разных стран").click()
  await frameContent.locator("[data-test-id='public-calendars-calendar-title']")
      .getByText("Праздники России", { exact: true }).click()
  await frameContent.getByText("Сохранить").click()
})

test("Отображать праздники", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Календарь", "touch.calendar.mail.ru")
  await frameContent.locator("[data-test-id='sidebar-button']").click()

  const checkbox = frameContent.locator("[data-test-id*='sidebar-calendar-checkbox-#EAF277']")
  await checkbox.click()
  await checkbox.click()
  await frameContent.locator("[data-test-id='cross-button']").click()
  await expect(frameContent.getByText("Великий пост").first()).toBeVisible()
})

test("Создать новую заметку", async ({page}) => {
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
  await frameContent.locator("[data-test-id='notes-list']").getByText("Playwright").first().click({button: "right"})
  await frameContent.getByText("Удалить заметку").click()
  await frameContent.getByText("Удалить", {exact: true}).click()
})

test("Создать новую задачу", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Задачи", "https://todo.mail.ru")

  await frameContent.locator("div[placeholder='Добавить название']").fill("Playwright")
  await frameContent.locator("div[placeholder='Добавить описание']").fill("Тестовое описание")
  await createTask(page, frameContent)
  for (let c of ["a", "b", "c"]) {
    await frameContent.locator("input[placeholder='Создать задачу']").fill(c)
    await page.keyboard.press('Enter')
  }

  await frameContent.locator(".ListHeader_baseListHeader__3Sj87 button").click()
  await frameContent.getByText("Сортировать по имени").click()
  const tasks = await frameContent.locator(".EditableText_fakeEdit__36Qx9 p").allTextContents()
  const isSorted = tasks.every((val, i, arr) => i === 0 || arr[i - 1] <= val);
  await expect(isSorted).toBeTruthy()

  await frameContent.getByText("c", {exact: true}).click({button: "right"})
  await frameContent.getByText("Удалить").click()
  await frameContent.locator(".Burger_contrastIcon__DBIMO.Burger_blackIcon__3lwZS").click()
  await frameContent.locator("p").getByText("Мой список").first().click({button: "right"})
  await frameContent.getByText("Удалить", {exact: "true"}).click()
})

test("Редактировать задачу", async ({page}) => {
  const frameContent = await clickOnFrame(page, "Задачи", "https://todo.mail.ru")
  await createTask(page, frameContent)
  await frameContent.locator(".ListHeader_baseListHeader__3Sj87 button").click()
  await frameContent.getByText("Редактировать").click()

  await frameContent.locator("div.ColorRadio_colorRadio__1dLNj[style='background-color: rgb(255, 123, 183);']").click()
  await frameContent.locator("input[name='name']").fill("Новый список")
  await frameContent.getByText("Тестовое описание").fill("Новое описание")
  await frameContent.getByText("Сохранить").click()

  await frameContent.locator(".Burger_contrastIcon__DBIMO.Burger_blackIcon__3lwZS").click()
  await page.waitForTimeout(500)
  await frameContent.locator("div").getByText("Новый список").first().click({button: "right"})
  await page.waitForTimeout(500)
  await frameContent.getByText("Удалить").click()
})

test("Посмотреть погоду", async ({page}) => {
  await page.frameLocator('[data-test-id="mail-widgets-button-frame"]')
      .locator(".WidgetButton_container__85TTS").click();
  const frameContent = await page.frameLocator("[data-test-id='mail-widgets-main-frame']")
  await frameContent.locator(`button[title='Погода']`).click()

  await expect(frameContent.locator(".WeatherShort_weatherShortRegion__pOlWP")
      .getByText("Санкт-Петербург")).toBeVisible()
  await expect(frameContent.locator(".WeatherShort_weatherShort__yhodk").getByText("Сегодня"))
      .toBeVisible()
  await expect(frameContent.locator(".WeatherShort_weatherShort__yhodk").getByText("Завтра"))
      .toBeVisible()
})

test("Управление умной сортировкой", async ({page}) => {
  await page.getByText("Настройки").click()
  await page.getByText("Умная сортировка").click()

  const groups = ["Рассылки", "Социальные сети", "Письма себе", "Чеки", "Новости", "Госписьма"]
  for (let group of groups) {
    let button = await page.locator(`//p[text()='${group}']/../..//div[@data-test-id='metathread-switch']`)
    await button.click()
    await page.waitForTimeout(400)
    await button.click()
  }
})

test("Изменение личных данных профиля", async ({page}) => {
  await page.locator(".ph-avatar-img").click()
  await page.getByText("Личные данные").click()
  const oldData = {
    firstName: "Иван",
    lastName: "Тарасов"
  }
  const newData = {
    firstName: "Вася",
    lastName: "Пупкин"
  }

  const editPersonalData = async (page, data) => {
    await page.locator("[data-test-id='firstname-field-input']").fill(data.firstName)
    await page.locator("[data-test-id='lastname-field-input']").fill(data.lastName)
    await page.locator("[data-test-id='nickname-field-input']")
        .fill(data.firstName + " " + data.lastName)
    await page.getByText("Сохранить").click()
  }

  await editPersonalData(page, newData)
  await expect(page.locator("[data-test-id='navigation-menu-header'] h4")
      .getByText(newData.firstName + " " + newData.lastName)).toBeVisible()
  await editPersonalData(page, oldData)
})

test("Добавить телефон и резервную почту", async ({page}) => {
  await page.locator(".ph-avatar-img").click()
  await page.getByText("Личные данные").click()
  await page.locator(".NavigationItem-module__text--qpycu").getByText("Контакты и адреса").click()

  await page.locator("[data-test-id='recovery-addPhone-button']").getByText("Добавить").click()
  await page.locator("[data-test-id='phone-input']").fill("9999999999")
  await page.getByText("Отменить").click()

  await page.locator("[data-test-id='recovery-addEmail-button']").getByText("Добавить").click()
  await page.locator("input[placeholder='Резервная почта']").fill("islambek@tajik.ru")
  await page.getByText("Продолжить").click()
  await page.getByText("Закрыть").click()

  await page.locator("[data-test-id='recovery-delete-email-button']").click()
  await page.locator("[data-test-id='recovery-deleteEmail-submit']").getByText("Удалить").click()
})