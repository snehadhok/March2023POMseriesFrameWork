package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v112.network.model.PrivateNetworkRequestPolicy;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productMetaData = By.xpath("(//div [@id='content']//ul[@class='list-unstyled'])[1]//li");
	private By productPriceData = By.xpath("(//div [@id='content']//ul[@class='list-unstyled'])[2]//li");
	private By productHeader = By.xpath("//div[@id='content']//h1");
	private By productImages = By.xpath("//ul[@class='thumbnails']//img");
	// private By quantity = By.name("quantity");
	private By addToCartBtn = By.id("button-cart");
	private Map<String, String> productMap;

	// constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// Method
	public String getProductHeaderValue() {
		return eleUtil.doElementGetText(productHeader);
	}

	public int getProductImagesCount() {
		int actProductImagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("total product images for :" + getProductHeaderValue() + "===>" + actProductImagesCount);
		return actProductImagesCount;
	}

	public boolean isaddToCartBtnExist() {
		return eleUtil.waitForElementPresence(addToCartBtn, AppConstants.SHORT_TIME_OUT).isDisplayed();
	}

	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsVisible(productMetaData, AppConstants.SHORT_TIME_OUT);
		// Map<String, String> productMap = new HashMap<String, String>();
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String key = metaText.split(":")[0].trim();
			String value = metaText.split(":")[1].trim();
			productMap.put(key, value);
		}
		// return metaMap;
	}

	private void getProductPricingData() {
		List<WebElement> priceList = eleUtil.waitForElementsVisible(productPriceData, AppConstants.SHORT_TIME_OUT);
		// Map<String, String> productMap = new HashMap<String, String>();
		String actPrice = priceList.get(0).getText().trim();
		String exTax = priceList.get(1).getText().split(":")[0].trim();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("price", actPrice);
		productMap.put(exTax, exTaxValue);
		// return pricaMap;
	}

	public Map<String, String> getProductData() {
		productMap = new HashMap<String, String>();
		productMap.put("productHeader", getProductHeaderValue());
		productMap.put("productImages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPricingData();
		return productMap;

	}

}
