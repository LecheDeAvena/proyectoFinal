package main.java.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Buscador {//steam,g2a,eneba,intant gaming

	public static void sacarPrecios(String name) {

		String searchString = name;
		
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

		//mostrar por pantalla los datos
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
		
	}
	
	public static Map<String, String[]> buscarJuego(String name) {//buscar en steam todos los juegos con titulo similar a lo que el usuario busca
		
		String searchString = name;
		
		String steamSearchString = searchString.replaceAll(" ","+");
		
		String steamURL = String.format("https://store.steampowered.com/search/?term=%s", steamSearchString);

		Document steamDoc = Scraping.getHtmlDocument(steamURL);
		steamDoc.select("strike").remove();
		
		Elements gameTitle = steamDoc.select("span.title");
		Elements gameDate = steamDoc.select("div[class=col search_released responsive_secondrow]");
		Elements gameImgURL = steamDoc.select("div[class=col search_capsule]").select("img");
		Elements gameURL = steamDoc.select("div[id=search_resultsRows]").select("a");
		
		Map<String, String[]> datosMap = new LinkedHashMap <String, String[]>();
		
		for (int i = 0; i < gameTitle.size(); i++) {

			String[] placeholder= new String[3];
			placeholder[0]=gameDate.get(i).text();
			placeholder[1]=gameImgURL.get(i).absUrl("src");
			placeholder[2]=gameURL.get(i).absUrl("href");
			
			
			datosMap.put(gameTitle.get(i).text(), placeholder);
			
			/*System.out.println("\n" + gameTitle.get(i).text() + " ---- "
					+ gameDate.get(i).text()+ " ---- " +gameImgURL.get(i).absUrl("src"));*/
		}

		return datosMap;
	}
	
	public static Map<String, String> getInfoJuego(String url) {//Sacar la informacion básica del juego en steam

		Document gameDoc = Scraping.getHtmlDocument(url);
		gameDoc.select("strike").remove();
		

		Element gameTitle = gameDoc.select("div[id=appHubAppName]").first();
		Element gamePrice = gameDoc.select("div[class=game_area_purchase_game_wrapper]").select("div[class=game_purchase_price price]").first();
		Element gameDesc = gameDoc.select("div.game_description_snippet").first();
		Elements gameTags = gameDoc.select("div[id=genresAndManufacturer]").select("span");
		Element gameImg = gameDoc.select("img.game_header_image_full").first();
		
		if(gamePrice==(null))
			gamePrice = gameDoc.select("div[class=game_area_purchase_game_wrapper]").select("div[class=discount_final_price]").first();
		
		Map<String, String> infoDataMap = new HashMap<String, String>();
		infoDataMap.put("title", gameTitle.text());
		infoDataMap.put("price", gamePrice.text());
		infoDataMap.put("desc", gameDesc.text());
		infoDataMap.put("genre", gameTags.text());
		infoDataMap.put("img", gameImg.absUrl("src"));
		
		return infoDataMap;
	}
	
	public static Map<String, OfertaJuego[]> getOfertas(String gameTitle) throws IOException {//devolver un mapa con las distintas ofertas del juego en las paginas: g2a, instant gaming, eneba

		Map<String, OfertaJuego[]> infoDataMap=new HashMap<>();//array donde guardamos todas las ofertas [0->g2a,1->instant gamin,2->eneba]

		//ajustar titulo para ponerlo en la url
		String g2aSearchString = gameTitle.replaceAll(" ","%20");
		String instantSearchString = gameTitle.replaceAll(" ","%20");	
		String enebaSearchString = gameTitle.replaceAll(" ","%20");
		
		//meter nombre en el formato de url de la pagina
		String g2aURL = String.format("https://www.g2a.com/en-gb/search?query=%s", g2aSearchString);
		String instantURL = String.format("https://www.instant-gaming.com/es/busquedas/?q=%s", instantSearchString);
		String enebaURL = String.format("https://www.eneba.com/us/marketplace?text=%s&sortBy=RELEVANCE_DESC", enebaSearchString);

		//Conseguir el html
		//Document g2aDoc = Jsoup.connect(g2aURL).get();

		Document g2aDoc = Scraping.getHtmlDocument(g2aURL);
		Document instantDoc = Scraping.getHtmlDocument(instantURL);
		Document enebaDoc = Scraping.getHtmlDocument(enebaURL);

		System.out.println(g2aURL);
		//sacar los elementos que nos hagan falta

		g2aDoc.select("div[indexes__ContentWrapper-wklrsw-95]").remove();//elimina elementos de la pagina no necesarios
		Elements g2aGameTitle = g2aDoc.select("h3").select("a");
		Elements g2aDiscountPrice = g2aDoc.select("span[data-locator=zth-price]");
		Elements g2aDiscountPercent = g2aDoc.select("span[data-locator=zth-badge]");
		Elements g2aDiscountURL = g2aDoc.select("h3").select("a");
		
		Elements instantGameTitle = instantDoc.select("div.information").select("div.name");
		Elements instantDiscountPrice = instantDoc.select("div.information").select("div.price");
		Elements instantDiscountPercent = instantDoc.select("div.discount");
		Elements instantDiscountURL = instantDoc.select("a[class=cover]");
		
		Elements enebaGameTitle = enebaDoc.select("div.lirayz");
		Elements enebaDiscountPrice = enebaDoc.select("span.DTv7Ag");	
		//Elements enebaDiscountPercent = enebaDoc.select("span.PIG8fA");//Es necesario calcular
		Elements enebaDiscountURL = enebaDoc.select("a.oSVLlh");

		//Guardar datos
		//G2A
		//organizarse con mapas.
		OfertaJuego[] g2aPrices=new OfertaJuego[g2aDiscountPrice.size()];
		for (int i = 0; i < g2aDiscountPrice.size(); i++) {
			
			String[] placeholder= {g2aDiscountPrice.get(i).text(),g2aDiscountURL.get(i).absUrl("href")};//0-> precio,1-> URL
			OfertaJuego ph= new OfertaJuego();
			ph.setTitulo(g2aGameTitle.get(i).text());
			ph.setPrecio(placeholder[0]);
			ph.setUrl(placeholder[1]);
			ph.setDescuento("X%");
			g2aPrices[i]=ph;
		}
		infoDataMap.put("g2a", g2aPrices);
		

		//INSTANT GAMING
		OfertaJuego[] instantPrices=new OfertaJuego[instantDiscountPrice.size()];
		for (int i = 0; i < instantDiscountPrice.size(); i++) {
			
			String[] placeholder= {instantDiscountPrice.get(i).text(),instantDiscountURL.get(i).absUrl("href")};//0-> precio,1-> URL
			OfertaJuego ph= new OfertaJuego();
			ph.setTitulo(instantGameTitle.get(i).text());
			ph.setPrecio(placeholder[0]);
			ph.setUrl(placeholder[1]);
			ph.setDescuento("X%");
			instantPrices[i]=ph;
		}
		infoDataMap.put("instant", instantPrices);

		//ENEBA old
		/*Map<String,String[]> enebaPrices=new HashMap<String, String[]>();
		for (int i = 0; i < enebaDiscountPrice.size(); i++) {
			String[] placeholder= {enebaDiscountPrice.get(i).text(),enebaDiscountURL.get(i).absUrl("href")};//0-> precio, calcular porcentaje, 1-> URL
			enebaPrices.put(enebaGameTitle.get(i).text(), placeholder);
		}
		infoDataMap[2]=enebaPrices;*/
		
		//ENEBA
		OfertaJuego[] enebaPrices=new OfertaJuego[enebaDiscountPrice.size()];
		//recoger todos los datos necesarios y meterlos en un array
		for (int i = 0; i < enebaDiscountPrice.size(); i++) {
			
			String[] placeholder= {enebaDiscountPrice.get(i).text(),enebaDiscountURL.get(i).absUrl("href")};//0-> precio, 1-> URL
			//Objeto con datos de la oferta
			OfertaJuego ph= new OfertaJuego();
			ph.setTitulo(enebaGameTitle.get(i).text());
			placeholder[0]=placeholder[0].substring(1)+"€";//Ordenar el simbolo €
			ph.setPrecio(placeholder[0]);
			ph.setUrl(placeholder[1]);
			ph.setDescuento("X%");
			
			enebaPrices[i]=ph;
		}
		infoDataMap.put("eneba", enebaPrices);
		
		//System.out.println(g2aURL); POr ver (no va)
		//System.out.println(infoDataMap.keySet()+" // "+infoDataMap.get("instant")[0].getUrl());//SYSO PRUEBAS
		return infoDataMap;
		
	}
}


