package main.java.utils;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Busqueda {//steam,g2a,eneba,intant gaming

	public static void main(String[] args) {

		try {

			//Entrada de nombre de juego
			System.out.println("Enter a game name, the more specific the better");
			Scanner scanner = new Scanner(System.in);
			String searchString = scanner.nextLine();
			String steamSearchString = searchString.replaceAll(" ","+");
			String g2aSearchString = searchString.replaceAll(" ","%20");
			String instantSearchString = searchString.replaceAll(" ","%20");
			String enebaSearchString = searchString.replaceAll(" ","%20");
		
			//meter nombre en el formato de url de la pagina
			String g2aURL = String.format("https://www.g2a.com/en-gb/search?query=%s", g2aSearchString);
			String steamURL = String.format("https://store.steampowered.com/search/?term=%s", steamSearchString);
			String instantURL = String.format("https://www.instant-gaming.com/es/busquedas/?q=%s", instantSearchString);
			String enebaURL = String.format("https://www.eneba.com/us/marketplace?text=%s&sortBy=RELEVANCE_DESC", enebaSearchString);
			
			//Conseguir el html
			Document steamDoc = Scraping.getHtmlDocument(steamURL);
			steamDoc.select("strike").remove();
			Document g2aDoc = Scraping.getHtmlDocument(g2aURL);
			Document instantDoc = Scraping.getHtmlDocument(instantURL);
			Document enebaDoc = Scraping.getHtmlDocument(enebaURL);
			
			//sacar los elementos que nos hagan falta
			Elements steamGameTitle = steamDoc.select("span.title");
			Elements steamDiscountPrice = steamDoc.select("div.discount_final_price");

			Elements g2aGameTitle = g2aDoc.select("h3[class=sc-iqAclL sc-dIsUp dJFpVb eHDAgC sc-fcmMJX GYHUF]");
			Elements g2aDiscountPrice = g2aDoc.select("span[data-locator=zth-price]");
			
			Elements instantGameTitle = instantDoc.select("div.information").select("div.name");
			Elements instantDiscountPrice = instantDoc.select("div.information").select("div.price");
			
			Elements enebaGameTitle = enebaDoc.select("div.lirayz");
			Elements enebaDiscountPrice = enebaDoc.select("span.DTv7Ag");

			//sacar por pantalla los datos
			System.out.println("STEAM PRICES");
			
			for (int i = 0; i < steamDiscountPrice.size(); i++) {
				System.out.println("\n" + steamGameTitle.get(i).text() + " ---- "
						+ steamDiscountPrice.get(i).text());
			}

			System.out.println("\n-----------------------------------------");

			System.out.println("\nG2A PRICES");
			for (int i = 0; i < g2aDiscountPrice.size(); i++) {
				System.out.println(
						"\n" + g2aGameTitle.get(i).text() + " ---- " + g2aDiscountPrice.get(i).text());
			}
			
			System.out.println("\n-----------------------------------------");

			System.out.println("\nINSTANT GAMING PRICES");
			for (int i = 0; i < instantDiscountPrice.size(); i++) {
				System.out.println(
						"\n" + instantGameTitle.get(i).text() + " ---- " + instantDiscountPrice.get(i).text());
			}
			
			System.out.println("\n-----------------------------------------");

			System.out.println("\nENEBA PRICES");
			for (int i = 0; i < enebaDiscountPrice.size(); i++) {
				System.out.println(
						"\n" + enebaGameTitle.get(i).text() + " ---- " + enebaDiscountPrice.get(i).text());
			}

			//en caso de error indicar
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Your search returned 0 results");
		}

	}
}