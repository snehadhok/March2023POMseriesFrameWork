package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class LoginPageTest extends BaseTest {
	@Test(priority = 1)
	public void LoginPageTitleTest() {
		String actTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE);
	}
	@Test(priority = 2)
	public void LoginPageUrlTest() {
		String actUrl=loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	@Test(priority = 3)
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	@Test(priority = 4)
	public void doLoginTest() {
	 accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	 Assert.assertEquals(accPage.isLogoutLinkExist(),true);
		
		
	}

}
