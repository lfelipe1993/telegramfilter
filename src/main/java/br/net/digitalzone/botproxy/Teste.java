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
		Pattern getHasIphone = Pattern.compile("^.*\\biphone\\b.*$",Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
		//(-?[0-9]+([\\.])?[0-9]{3}([\,][0-9]{2})?)
		//Pattern pValores = Pattern.compile("(-?[0-9]+[\\.]*[0-9]+[\\,]+[0-9]*)");
		Pattern pValores = Pattern.compile("(-?[0-9]+([\\.])?[0-9]{3}([\\,][0-9]{2})?)");
			String text = "??? SO FUNCIONA ABRINDO O LINK PELO APLICATIVO AMERICANAS\r\n"
					+ "https://is.gd/fYDSWV INVERTER! 10% DE RETORNO PAGANDO VIA AMEDIGITAL Ar Condicionado Split High Wall Inverter Samsung Ultra So Frio 9000 BTUs AR09TVHZDWKNAZ R$1449,00 NO BOLETO OU R$1660,23 EM 12X SEM JUROS USAR CUPOM: VALE100  ??Pague via AMEDIGITAL e RECEBA R$166,02 DE VOLTA \r\n"
					+ "?? ATENCAO Apos instalar o APP e se logar nele, clique no link que o APP abre automaticamente. No Iphone , ao clicar no link, aparece a opcao no navegador safari de abrir o link pelo app, na parte superior da tela. so baixar a tela do safari que a opcao aparece logo acima da barra de endereco";
			
					//String text = "? Relogio Smartwatch Smartband Android Iwo iPhone Samsung Moto P68 (Prateado) ? "
							//+ "De: R$2?6?5?,?9?9? ? Por: R$238,30 ? Loja: #Amazon ? Acesse: https://amzn.to/3cdBE0N";
			
			

					if (Check.checkModel(getHasIphone.matcher(text)) != "") {
						System.out.println("Encontrou Iphone");
						modelo = Check.checkModel(getHasIphone.matcher(text));
						armaz = Check.checkArmaz(getHasIphone.matcher(text));
					}

					if (modelo != "" && armaz != "") {
						System.out.println("Modelo: " + modelo + armaz);
						modelos = Check.getModeloMethod(modelo , armaz);
					}

		
		if (Check.checkPrice(pValores.matcher(text.replace("_", "").replace("?", "")), modelos).isPresent()) {
			Check.checkPrice(pValores.matcher(text.replace("_", "").replace("?", "")), modelos).ifPresent(user -> {
			    System.out.println("User's name = " + user);    
			});
			System.out.println("Envio msg");
			
		}

	}

}
