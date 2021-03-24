package br.net.digitalzone.botproxy;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.net.digitalzone.botproxy.model.Modelos;
import br.net.digitalzone.botproxy.model.Usuarios;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

public class TelegramNotifier {

	public static void sendNotification(Modelos model, Double bp) {

		List<Usuarios> usuariosList = Stream.of(Usuarios.values()).collect(Collectors.toList());

		usuariosList.stream().forEach(x -> {
			String msg = "Olá " + x.getNome() + "," + model.getNome() + " " + model.getArmaz() + "GB "
					+ "no precinho por " + String.format("%.0f", bp);

			String user = x.getUser();

			sendNotification(msg, user);
		});

	}

	public static void sendNotification(String message, String user) {

		Client client = ClientBuilder.newClient();

		UriBuilder builder = UriBuilder.fromUri("https://api.callmebot.com/start.php").queryParam("user", user)
				.queryParam("text", message).queryParam("lang", "pt-BR-Standard-A").queryParam("rpt", "2");

		Future<Response> future = client.target(builder).request().async().get();
		
		// block until complete
		Response res = null;
		try {
			res = future.get(5, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally {
			if(res != null) {
				res.close();
			}
		}
		
		if(res != null) {
			System.out.println("Status the Call for " + user + " : " + res.getStatus());
		}else {
			System.out.println("Não foi possível enviar mensagem para o user "  + user);
		}
	}
	
	public static void sendNotificationOld(String message, String user) {

		HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
				.version(HttpClient.Version.HTTP_2).build();

		UriBuilder builder = UriBuilder.fromUri("https://api.callmebot.com/start.php").queryParam("user", user)
				.queryParam("text", message).queryParam("lang", "pt-BR-Standard-A").queryParam("rpt", "2");

		HttpRequest request = HttpRequest.newBuilder().GET().uri(builder.build()).timeout(Duration.ofSeconds(10))
				.build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(request.toString());
		System.out.println(response.statusCode());
		// System.out.println(response.body());

	}
}
