@echo off
rmdir /s /q build\allure-results build\allure-report
call gradle test --tests "org.example.mailRu.MailRuMainTests" -Dallure_results=build/allure-results/MailRuMainTests
copy "build\environment.properties" "build\allure-results\MailRuMainTests"
call allure generate build/allure-results/MailRuMainTests --clean -o build/allure-report/MailRuMainTests

call gradle test --tests "org.example.mailRu.MailRuInAccountTests" -Dallure_results=build/allure-results/MailRuInAccountTests
copy "build\environment.properties" "build\allure-results\MailRuInAccountTests"
call allure generate build/allure-results/MailRuInAccountTests --clean -o build/allure-report/MailRuInAccountTests

call npx playwright test --headed
copy "build\environment.properties" "build\allure-results\playwright"
call allure generate --clean build/allure-results/playwright -o build/allure-report/playwright
call allure generate --merge build/allure-results/playwright build/allure-results/MailRuMainTests build/allure-results/MailRuInAccountTests -o build/allure-report/all --clean
echo Tests have finished!
