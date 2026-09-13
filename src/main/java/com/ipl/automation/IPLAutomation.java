package com.ipl.automation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IPLAutomation {

	public static void main(String[] args) {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver wd = new ChromeDriver(options);
		wd.get("file:///C:/Users/Welcome/Downloads/ipl_2025_points_table.html");

		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		// Step 1: find the entire Web table
		By tableLocator = By.tagName("table");
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

		// Step 2: Locate the tbody with the chaining of webElement
		By tbodyLocator = By.id("tableBody");
		WebElement tbody = table.findElement(tbodyLocator);

		// Step 3: find all table rows
		By tableRowLocator = By.tagName("tr");
		List<WebElement> tableRowList = tbody.findElements(tableRowLocator);
//		System.out.println(tableRowList.size());

		//
		List<Team> teamList = new ArrayList<Team>();
		for (WebElement row : tableRowList) {
			By tableDataRowLocator = By.tagName("td");
			List<WebElement> tableDataList = row.findElements(tableDataRowLocator);

//			for (WebElement tableData : tableDataList) {
//				System.out.println(tableData.getText());

			By tdChild = By.tagName("span");
			WebElement tdChildList = tableDataList.get(1).findElement(tdChild);
			String teamName = tdChildList.getText();

			double nrr = Double.parseDouble(tableDataList.get(8).getText());
			int pts = Integer.parseInt(tableDataList.get(7).getText());

			Team team = new Team(tableDataList.get(0).getText(), teamName, nrr, pts);
			teamList.add(team);

//			}
		}

//		for (Team data : teamList) {
//			System.out.println(data);
//		}

		
		
//		teamList.stream().forEach(i -> System.out.println(i));
//
//		teamList.stream().map(i -> i.getTeamName()) // trnformation Stream<Team> to Stream<String>
//				.forEach(i -> System.out.println(i)); // terminal - last
//
//		teamList.stream().map(i -> i.getPoints()) // trnformation Stream<Team> to Stream<String>
//				.forEach(i -> System.out.println(i));
		

		
		// Filteration
		// Max
		int maxPoints = teamList.stream()
		.mapToInt(i -> i.getPoints())
		.max().orElse(0);

		teamList.stream()
		.filter(i -> i.getPoints() == maxPoints)
		.map(i -> i.getTeamName())
		.forEach(i -> System.out.println(i));
		

		Team maxTeamNRR = teamList.stream()
		.filter(i -> i.getPoints() == maxPoints)
		.max(Comparator.comparingDouble(i -> i.getNrr()))
		.orElse(null);
		System.out.println(maxTeamNRR);
	}

}
