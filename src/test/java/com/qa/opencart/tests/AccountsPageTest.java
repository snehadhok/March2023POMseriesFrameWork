package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {
	@BeforeClass
	public void accPagesetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	@Test
	public void accPageTitleTest() {
		String actAccPageTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actAccPageTitle,AppConstants.ACCOUNTS_PAGE_TITLE);		
	}
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	@Test
	public void accPageHeadersCountTest() {
		int actAccPageHeadersCount =accPage.getAccountsPageHeaderCount();
		System.out.println("Actual Acc Page Headers count = " + actAccPageHeadersCount);
		Assert.assertEquals(actAccPageHeadersCount,AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	@Test
	public void accPageHeaderTest() {
		List<String> actAccPageHeadersList = accPage.getAccountsPageHeader();
		Assert.assertEquals(actAccPageHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);	
	}
	@DataProvider
	public Object[][]SearchTestData(){
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2},
			{"canon",1},
		};		
	}
	@Test(dataProvider = "SearchTestData")
	public void dosearch(String searchKey, int productCount) {
		searchResPage = accPage.doSearch(searchKey);
		int actResultsCount =searchResPage.getSearchProductResultCount();
		Assert.assertEquals(actResultsCount,productCount);
	}
	
	
	
	
	
	
}
