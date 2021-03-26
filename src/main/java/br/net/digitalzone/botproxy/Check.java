package br.net.digitalzone.botproxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.net.digitalzone.botproxy.model.Modelos;
import br.net.digitalzone.botproxy.model.exceptions.EnumException;

public class Check {

	public static String checkModel(Matcher matPhr) {
		Pattern i5 = Pattern.compile("(?i)\\b(?:iphone 5)\\b");
		Pattern i5S = Pattern.compile("(?i)\\b(?:iphone 5s)\\b");
		Pattern i6 = Pattern.compile("(?i)\\b(?:iphone 6)\\b");
		Pattern i6S = Pattern.compile("(?i)\\b(?:iphone 6s|iphone Apple 6s)\\b");
		Pattern i7 = Pattern.compile("(?i)\\b(?:iphone 7)\\b");
		Pattern i7P = Pattern.compile("(?i)\\b(?:iphone 7 Plus)\\b");
		Pattern i8 = Pattern.compile("(?i)\\b(?:iphone 8|iphone  8)\\b");
		Pattern i8P = Pattern.compile("(?i)\\b(?:iphone 8 Plus)\\b");
		Pattern iSE = Pattern.compile("(?i)\\b(?:iphone SE)\\b");
		Pattern iX = Pattern.compile("(?i)\\b(?:iphone X)\\b");
		Pattern iXR = Pattern.compile("(?i)\\b(?:iphone XR)\\b");
		Pattern iXS = Pattern.compile("(?i)\\b(?:iphone XS)\\b");
		Pattern iXSM = Pattern.compile("(?i)\\b(?:iphone XS Max)\\b");
		Pattern i11 = Pattern.compile("(?i)\\b(?:iphone 11)\\b");
		Pattern i11P = Pattern.compile("(?i)\\b(?:iphone 11 Pro)\\b");
		Pattern i11PM = Pattern.compile("(?i)\\b(?:iphone 11 Pro Max)\\b");
		Pattern i12M = Pattern.compile("(?i)\\b(?:iphone 12 Mini)\\b");
		Pattern i12 = Pattern.compile("(?i)\\b(?:iphone 12)\\b(?!.*mini)");
		Pattern i12P = Pattern.compile("(?i)\\b(?:iphone 12 Pro)\\b");
		Pattern i12PM = Pattern.compile("(?i)\\b(?:iphone 12 Pro Max)\\b");

		try {
			while (matPhr.find()) {
				if (i5S.matcher(matPhr.group()).find()) {
					return "5S";
				} else if (i5.matcher(matPhr.group()).find()) {
					return "5";
				} else if (i6S.matcher(matPhr.group()).find()) {
					return "6S";
				} else if (i6.matcher(matPhr.group()).find()) {
					return "6";
				} else if (i7P.matcher(matPhr.group()).find()) {
					return "7P";
				} else if (i7.matcher(matPhr.group()).find()) {
					return "7";
				} else if (i8P.matcher(matPhr.group()).find()) {
					return "8P";
				} else if (i8.matcher(matPhr.group()).find()) {
					return "8";
				} else if (iSE.matcher(matPhr.group()).find()) {
					return "SE";
				} else if (iXSM.matcher(matPhr.group()).find()) {
					return "XSM";
				} else if (iXS.matcher(matPhr.group()).find()) {
					return "XS";
				} else if (iXR.matcher(matPhr.group()).find()) {
					return "XR";
				} else if (iX.matcher(matPhr.group()).find()) {
					return "X";
				} else if (i11PM.matcher(matPhr.group()).find()) {
					return "11PM";
				} else if (i11P.matcher(matPhr.group()).find()) {
					return "11P";
				} else if (i12PM.matcher(matPhr.group()).find()) {
					return "12PM";
				} else if (i12P.matcher(matPhr.group()).find()) {
					return "12P";
				} else if (i12.matcher(matPhr.group()).find()) {
					return "12";
				} else if (i12M.matcher(matPhr.group()).find()) {
					return "12M";
				} else if (i11.matcher(matPhr.group()).find()) {
					return "11";
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static String checkArmaz(Matcher matPhr) {
		Pattern p16GB = Pattern.compile("(?i)\\b(?:16|16GB)\\b");
		Pattern p32GB = Pattern.compile("(?i)\\b(?:32|32GB)\\b");
		Pattern p64GB = Pattern.compile("(?i)\\b(?:64|64GB)\\b");
		Pattern p128GB = Pattern.compile("(?i)\\b(?:128|128GB)\\b");
		Pattern p256GB = Pattern.compile("(?i)\\b(?:256|256GB)\\b");
		Pattern p512GB = Pattern.compile("(?i)\\b(?:512|512GB)\\b");

		try {
			while (matPhr.find()) {
				if (p512GB.matcher(matPhr.group()).find()) {
					return "512";
				} else if (p256GB.matcher(matPhr.group()).find()) {
					return "256";
				} else if (p128GB.matcher(matPhr.group()).find()) {
					return "128";
				} else if (p64GB.matcher(matPhr.group()).find()) {
					return "64";
				} else if (p32GB.matcher(matPhr.group()).find()) {
					return "32";
				}else if (p16GB.matcher(matPhr.group()).find()) {
					return "16";
				}
			}
		} catch (

		IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static Optional<Double> checkPrice(Matcher mPrices, Modelos model) {
		Optional<Double> checkPrice = Optional.empty();

		if (model == null || mPrices == null) {
			return checkPrice;
		}

		List<String> list = new ArrayList<>();

		while (mPrices.find()) {
			list.add(mPrices.group());
		}

		checkPrice = list.stream().map(x -> x.replaceAll("(?:[^\\d\\,])", "").replace(",", "."))
				.map(Double::parseDouble).filter(x -> x < model.getLimitPrice() && x > 1800 && x < 13000).findAny();

		return checkPrice;
	}

	public static Modelos getModeloMethod(String model, String armaz) {
		Modelos modelo = null;
		
		if (model == null || armaz == null) {
			return modelo;
		}

		try {
			modelo = Modelos.toEnum(model.trim() + armaz.trim());
		} catch (EnumException e) {
			e.getMessage();
		}
		
		return modelo;
	}
	
	
}
