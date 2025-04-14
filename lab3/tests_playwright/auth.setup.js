import { test as setup, expect } from '@playwright/test';
import path from 'path';

const authFile = path.join(__dirname, '../playwright/.auth/user.json');

setup('authenticate', async ({ page }) => {
    await page.goto('/');

    await page.locator("input[placeholder='Имя аккаунта']").fill("iwan.tarasow2004.st@mail.ru");
    await page.locator("span").getByText("Другой").click();
    await page.locator("span").getByText("Войти").click();
    await page.locator("input[placeholder='Пароль']").fill("software_labs");
    await page.locator("span").getByText("Войти").click();

    await expect(page.getByText('Входящие')).toBeVisible({timeout: 60000})
    await page.context().storageState({ path: authFile });
});