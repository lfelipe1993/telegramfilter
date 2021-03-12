package br.net.digitalzone.botproxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.net.digitalzone.botproxy.model.Modelos;

public class Check {
	public static Pattern iSE = Pattern.compile("(?i)\\b(?:iphone SE)\\b");
	public static Pattern iXR = Pattern.compile("(?i)\\b(?:iphone XR)\\b");
	public static Pattern i11 = Pattern.compile("(?i)\\b(?:iphone 11)\\b");
	public static Pattern i12 = Pattern.compile("(?i)\\b(?:iphone 12)\\b(?!.*mini)");
	public static Pattern i12P = Pattern.compile("(?i)\\b(?:iphone 12 Pro)\\b");
	public static Pattern i12PM = Pattern.compile("(?i)\\b(?:iphone 12 Pro Max)\\b");

	public static Pattern p64GB = Pattern.compile("(?i)\\b(?:64|64GB)\\b");
	public static Pattern p128GB = Pattern.compile("(?i)\\b(?:128|128GB)\\b");
	public static Pattern p256GB = Pattern.compile("(?i)\\b(?:256|256GB)\\b");
	public static Pattern p512GB = Pattern.compile("(?i)\\b(?:512|512GB)\\b");

	public static String checkModel(Matcher matPhr) {

		try {
			while (matPhr.find()) {
				if (iSE.matcher(matPhr.group()).find()) {
					return "SE";
				} else if (iXR.matcher(matPhr.group()).find()) {
					return "XR";
				} else if (i11.matcher(matPhr.group()).find()) {
					return "11";
				} else if (i12.matcher(matPhr.group()).find()) {
					return "12";
				} else if (i12P.matcher(matPhr.group()).find()) {
					return "12P";
				} else if (i12PM.matcher(matPhr.group()).find()) {
					return "12PM";
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static String checkArmaz(Matcher matPhr) {

		try {
			while (matPhr.find()) {
				if (p64GB.matcher(matPhr.group()).find()) {
					return "64";
				} else if (p128GB.matcher(matPhr.group()).find()) {
					return "128";
				} else if (p256GB.matcher(matPhr.group()).find()) {
					return "256";
				} else if (p512GB.matcher(matPhr.group()).find()) {
					return "512";
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static Optional<Double> checkPrice(Matcher mPrices, Modelos model) {
		Optional<Double> checkPrice = Optional.empty();
		
		if(model ==null || mPrices ==null) {
			return checkPrice;
		}

		List<String> list = new ArrayList<>();

		while (mPrices.find()) {
			list.add(mPrices.group());
		}

		//list.forEach(System.out::println);

		checkPrice = list.stream().map(x -> x.replaceAll("(?:[^\\d\\,])", "").replace(",", "."))
				.map(Double::parseDouble).filter(
				x -> x < model.getLimitPrice() && x > 1800 && x < 13000).findAny();

		return checkPrice;
	}

	public static Modelos getModeloMethod(String model, String armaz) {
		if (model == null || armaz == null) {
			return null;
		}

		return Modelos.toEnum(model.trim() + armaz.trim());
	}

}
