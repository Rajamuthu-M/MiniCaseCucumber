package stepDefs;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
public class DemoBlazeDef {
	static WebDriver driver;
	String verify;
	
	@BeforeAll
	public static void setup() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.demoblaze.com/");
	}
	
	@Given("User is on Launch Page")
	public void user_is_on_launch_page() {
		driver.findElement(By.xpath("//a[text()='Home ']")).click();
	}

	@When("User login")
	public void user_login() {
		driver.findElement(By.id("login2")).click();
		driver.findElement(By.id("loginusername")).sendKeys("RajaM");
    	driver.findElement(By.id("loginpassword")).sendKeys("123456");
    	driver.findElement(By.xpath("//button[text()='Log in']")).click();
	}

	@Then("Should display Home Page")
	public void should_display_home_page() {
		boolean isDisp = driver.findElement(By.xpath("//a[text()='Welcome RajaM']")).isDisplayed();
    	Assert.assertTrue(isDisp);
	}
	
	@When("Add an item {string} and {string} to cart")
	public void add_an_item_and_to_cart(String category, String itemName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		String currentCategory = "//a[text()='"+category+"']";
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(currentCategory)))).click();
    	String currentItem = "//a[text()='"+itemName+"']";
    	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(currentItem)))).click();
    	WebElement btn = driver.findElement(By.xpath("//a[text()='Add to cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(btn));
		btn.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		verify = itemName;
		
	}
	
	@Then("Items must be added to cart")
	public void items_must_be_added_to_cart() {
		driver.findElement(By.xpath("//a[text()='Cart']")).click();
		List<WebElement> cartList = driver.findElements(By.xpath("//td[2]"));
		boolean flag=false;
		for(WebElement list:cartList) {
			if(list.getText().equalsIgnoreCase(verify)) {
				Assert.assertEquals(list.getText(), verify);
				flag=true;
				Assert.assertTrue(flag);
			}
		}
	}

	@When("Delete an item from cart")
	public void delete_an_item_from_cart() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.xpath("//a[text()='Cart']")).click();
    	String priceBefore =driver.findElement(By.id("totalp")).getText();
    	System.out.println(priceBefore);
    	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(),'Delete')][1]")))).click();
		String priceAfter =driver.findElement(By.id("totalp")).getText();
    	System.out.println(priceAfter);
    	
    	if(priceBefore==priceAfter)
    		System.out.println("Item not deleted");
    	else
    		System.out.println("Item deleted");
	}

	@Then("Items should be deleted in cart")
	public void items_should_be_deleted_in_cart() {
		Assert.assertNotEquals("priceBefore", "priceAfter");
	}

	@When("Place Order")
	public void place_order() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.xpath("//a[text()='Cart']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")))).click();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Raja");
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Salem");
		driver.findElement(By.xpath("//input[@id='card']")).sendKeys("76875785");
		driver.findElement(By.xpath("//input[@id='month']")).sendKeys("April");
		driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2024");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")))).click();	
	}

	@Then("Purchase Items")
	public void purchase_items() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		boolean isDisp = driver.findElement(By.xpath("//h2[(text()='Thank you for your purchase!')]")).isDisplayed();
    	Assert.assertTrue(isDisp);
    	Thread.sleep(1000);
    	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'OK')]")))).click();
	}
	@After
	public void attachImgToReport(Scenario scenario) {
		if(scenario.isFailed()) {
			TakesScreenshot scr = (TakesScreenshot)driver;
			byte[] img = scr.getScreenshotAs(OutputType.BYTES);
			scenario.attach(img,  "image/png", "imageOne");
		}
	}
}

