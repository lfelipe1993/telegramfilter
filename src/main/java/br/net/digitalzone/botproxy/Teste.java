package br.net.digitalzone.botproxy;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import br.net.digitalzone.botproxy.model.Modelos;

public class Teste {

	public static String modelo = "";
	public static String armaz = "";
	public static Modelos modelos = null;

	public static void main(String[] args) {
		String pHasIphone = "(?si).*\\biphone\\b.*";
		Pattern getHasIphone = Pattern.compile("^.*\\biphone\\b.*$", Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
		// (-?[0-9]+([\\.])?[0-9]{3}([\,][0-9]{2})?)
		// Pattern pValores = Pattern.compile("(-?[0-9]+[\\.]*[0-9]+[\\,]+[0-9]*)");
		Pattern pValores = Pattern.compile("(-?[0-9]+([\\.])?[0-9]{3}([\\,][0-9]{2})?)");
		String text = "??? SO FUNCIONA ABRINDO O LINK PELO APLICATIVO SHOPTIME https://afl.b2w.io/aLEQ Smart TV 50\" LG 50UN7310 UHD 4K Wifi Bluetooth Hdr Inteligencia Artificial Thinq "
				+ "Ai R$2149,99 EM 1X NO CARTAO OU R$2269,99 EM 12X SEM JUROS USAR CUPOM: ESQUENTA200 ?? ATENCAO Apos instalar o APP e se logar nele, "
				+ "clique no link que o APP abre automaticamente. No Iphone , ao clicar no link, aparece a opcao "
				+ "no navegador safari de abrir o link pelo app, na parte superior da tela. so baixar a tela do safari que a opcao aparece logo acima da barra de endereco";


		if (text.matches(pHasIphone)) {
			System.out.println("Encontrou iphone phasiphone");
			
			
			if (Check.checkModel(getHasIphone.matcher(text)) != "") {
				System.out.println("getHasIphone match");
				modelo = Check.checkModel(getHasIphone.matcher(text));
				armaz = Check.checkArmaz(getHasIphone.matcher(text));
			}

			if (modelo != "" && armaz != "") {
				System.out.println("Modelo: " + modelo + armaz);
				modelos = Check.getModeloMethod(modelo, armaz);
			}

			if (Check.checkPrice(pValores.matcher(text.replace("_", "").replace("?", "")), modelos).isPresent()) {
				Check.checkPrice(pValores.matcher(text.replace("_", "").replace("?", "")), modelos).ifPresent(user -> {
					System.out.println("User's name = " + user);
				});
				System.out.println("Envio msg");

			}

		}

	}

}
