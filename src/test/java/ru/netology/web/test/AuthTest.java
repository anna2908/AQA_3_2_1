package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DbInteractionDbUtils;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.getAuthInfo;
import static ru.netology.web.data.DataHelper.getInvalidUserInfo;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        DbInteractionDbUtils.clearTables();
    }

    @Test
    @DisplayName("Should login in system")
    void shouldLogin() {
        val validUser = getAuthInfo();
        val loginPage = new LoginPage();
        val verificationPage = loginPage.validLogin(validUser);
        verificationPage.validVerify(DbInteractionDbUtils.getVerificationCode());
    }

    @Test
    @DisplayName("Should block system after 3 invalid data")
    void shouldBlock() {
        val invalidUser = getInvalidUserInfo();
        val loginPage = new LoginPage();
        loginPage.validLogin(invalidUser);
        loginPage.validLogin(invalidUser);
        loginPage.validLogin(invalidUser);
        loginPage.blockNotification();
    }

}