package br.net.digitalzone.botproxy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import br.net.digitalzone.botproxy.model.Modelos;

public class Main {
	public static String modelo = "";
	public static String armaz = "";
	public static Modelos modelos = null;
	public static Integer count = 0;
	public static final Integer limitCount = 100;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		System.out.println("Iniciando a aplicação [" + LocalDateTime.now() + "]");
		TelegramBot bot = new TelegramBot("");
		GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);

		//(?si)^.*\biphone\b.*$ -- pega a linha toda que tem iphone
		Pattern getHasIphone = Pattern.compile("^.*\\biphone\\b.*$",Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
		String pHasIphone = "(?si).*\\biphone\\b.*";
		//Pattern pValores = Pattern.compile("(-?[0-9]+[\\.]*[0-9]+[\\,]+[0-9]*)");
		//Pattern pValores = Pattern.compile("(-?[0-9]+([\\.])?[0-9]{3}([\\,][0-9]{2})?)");
		Pattern pValores = Pattern.compile("(-?[0-9]+[\\.]*[0-9]+[\\,]+[0-9]*)|(-?[0-9]{1,2}[\\.][0-9]{3})");
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
					
					//System.out.println("Update:" + update);

					String text = "";
					if (update.message() != null) {
						text = update.message().text();

					} else if (update.channelPost() != null) {
						text = update.channelPost().text();
					}

					if (text != null) {
						if (text.matches(pHasIphone)) {

							System.out.println("Match word iphone");

							if (Check.checkModel(getHasIphone.matcher(text)) != "") {
								System.out.println("Encontrou Iphone");
								modelo = Check.checkModel(getHasIphone.matcher(text));
								armaz = Check.checkArmaz(getHasIphone.matcher(text));
							}

							if (modelo != "" && armaz != "") {
								System.out.println("Modelo: " + modelo + armaz);
								modelos = Check.getModeloMethod(modelo , armaz);
							}
							
							Optional<Double> bp = Check.checkPrice(pValores.matcher(text
									.replace("_", "").replace("?", "")), 
									modelos);

							//replace por conta de bug em caracteres especiais
							if (bp.isPresent()) {
								System.out.println("Envio msg");
								bot.execute(new SendMessage("-1001344323551", "***FILTRADA***" + "\n " + text));
							

							if(bp.get() < (modelos.getLimitPrice() * 0.90)) {
								System.out.println("Acordou todo mundo!!!");
								TelegramNotifier.sendNotification(modelos,bp.get());
							};
							
							}
						}
					}

				}
				
				return UpdatesListener.CONFIRMED_UPDATES_ALL;
			}
		});
	}

}
