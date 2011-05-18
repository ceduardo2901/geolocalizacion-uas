package com.uas.geo;

import java.util.List;

import org.geonames.FeatureClass;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

public class Geolocalizacion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();

			//searchCriteria.setQ("Farmacia");
			ToponymSearchResult searchResult;
			WebService.setUserName("apor27");
			String[] featureCode = {"MALL","REST"};
			List<Toponym> list = WebService.findNearby(new Double("41.890280"),new Double("12.487464"), 5, FeatureClass.S, featureCode, "es", 100);
			//List<Toponym> list2 = WebService.findNearbyPlaceName(new Dosuble("-34.893478"),new Double("-56.165085"));
			//searchResult = WebService.search(searchCriteria);

			for (Toponym toponym : list) {
				System.out.println(toponym.getName() + " "
						+ toponym.getCountryName());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
