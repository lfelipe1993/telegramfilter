package br.net.digitalzone.botproxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.net.digitalzone.botproxy.model.Modelos;

public class Program {

	public static void main(String[] args) {
		TelegramBot bot = new TelegramBot("");

		GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
		// sync
		GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
		List<Update> updates = updatesResponse.updates();

		// async
		bot.execute(getUpdates, new Callback<GetUpdates, GetUpdatesResponse>() {

			public void onResponse(GetUpdates request, GetUpdatesResponse response) {
				List<Update> updates = response.updates();
			}

			public void onFailure(GetUpdates request, IOException e) {

			}
		});

		bot.setUpdatesListener(new UpdatesListener() {
			@Override
			public int process(List<Update> updates) {

				for (Update update : updates) {
					String text = update.message().text();
					//System.out.println(text);
					//Patern de valores
					Pattern pValores = Pattern.compile("(-?[0-9]+[\\.][0-9]+[\\,]+[0-9]*)"); 
					Pattern getHasIphone = Pattern.compile("(?i).*\\biphone\\b.*");
					Pattern getLine = Pattern.compile("(?i)^.*\\biphone\\b.*$");
					
					Pattern iSE = Pattern.compile("(?si)(iphone SE)");
					Pattern iXR = Pattern.compile("(?si)(iphone XR)");
					Pattern i11 = Pattern.compile("(?si)(iphone 11)");
					String i12 = "(?i)\\b(?:iphone 12)\\b";
					Pattern pI12 = Pattern.compile("(?i)\\b(?:iphone 12)\\b");
					Pattern i12P = Pattern.compile("(?si)(iphone 12 Pro)");
					Pattern i12PM = Pattern.compile("(?si)(iphone 12 Pro Max)");
					
					
					
					Pattern p64GB = Pattern.compile("(64*GB)|(64* )|(64 GB)");
					Pattern p128GB = Pattern.compile("(?i)\\b(?:128)\\b");
					Pattern p256GB = Pattern.compile("(256*GB)|(256* )|(256 GB)");
					Pattern p512GB = Pattern.compile("(256*GB)|(256* )|(256 GB)");
					
					String modelo = "";
					String armaz="";
					Modelos modelos = null;
					
					
					//pattern para pegar valores
					//(-?[0-9]+[\\.][0-9]+[\\,]?+[0-9]*)
					
					//pegar linha com a palavra iphone (descrição)
					//(?i).*\b(iphone|apple)\b.*\n
					
					//String pHasIphone = "(?i)^.*\\biphone\\b.*$";
					String pHasIphone = "(?si).*\\biphone\\b.*";
					if (text.matches(pHasIphone)) {
						System.out.println("Match word iphone");
						String msg = "";
						
						Matcher mPhraseContainsIphone = getHasIphone.matcher(text);
						
						if(Check.checkModel(getHasIphone.matcher(text))!= ""){
							modelo = Check.checkModel(getHasIphone.matcher(text));
							armaz = Check.checkArmaz(getHasIphone.matcher(text));
						}
						
						if(modelo != "" && armaz != "") {
							modelos = modelos.toEnum(modelo+armaz);
						}
						
						System.out.println(modelos.getSigla());
						
						
						while(mPhraseContainsIphone.find()) {
							System.out.println(mPhraseContainsIphone.group());
							
							if(pI12.matcher(mPhraseContainsIphone.group()).find()) {
								System.out.println(Check.checkArmaz(getHasIphone.matcher(text)));
								if(p128GB.matcher(mPhraseContainsIphone.group()).find()) {
									System.out.println("Iphonezinho 12 128 encontrado na string");
									msg += "Iphone 12 encontrado porra!";
								}
							}
						}
						
						
						
						//buscar preços
						Matcher mPrices = pValores.matcher(text);
					
						List<String> list = new ArrayList<>();
						
						while(mPrices.find()) {
				            list.add(mPrices.group());
				        }
						
						StringBuilder strb = new StringBuilder();
						
						strb.append("\n\nLISTA DE VALORES ENCONTRADOS");
						list.forEach(x -> {
							strb.append("\n");
							strb.append(x);
						});
						
						//Locale locale = new Locale("pt","BR");
						
						Optional<Double> listPrices = list.stream()
								.map(x -> x.replaceAll("(?:[^\\d\\,])", "").replace(",", "."))
								.map(Double::parseDouble).filter(x -> x > 6100)
								.findAny();

						if(listPrices.isPresent()) {
							System.out.println("Existe preço dentro dos parametros");
						}
						
						
						SendResponse response = bot.execute(new SendMessage("-1001344323551", "EDITADA " + msg + "\n "+ text + "\n " + strb));
					}

				}

				return UpdatesListener.CONFIRMED_UPDATES_ALL;
			}
		});

	}

}