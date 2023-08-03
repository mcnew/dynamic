package common.template;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * https://raw.githubusercontent.com/j-easy/easy-rules/master/pom.xml
 */
public class TemplateEngineMustacheRemote extends TemplateEngineMustache {

	private static final String REPOSITORY;

	private final HttpClient client;

	static {
		String branch = System.getenv("BRANCH");
		if (branch == null) {
			throw new IllegalArgumentException("No BRANCH defined");
		}
		String repository = System.getenv("REPOSITORY");
		if (repository == null) {
			throw new IllegalArgumentException("No REPOSITORY defined");
		} else {
			REPOSITORY = repository + '/' + branch;
		}
	}

	public TemplateEngineMustacheRemote() {
		client = HttpClient.newBuilder().build();
	}

	@Override
	protected Template getTemplate(String name) {
		try {
			String uri = REPOSITORY + name + ".mustache";
			HttpResponse<String> response = client.send(HttpRequest.newBuilder().uri(URI.create(uri)).build(),
					BodyHandlers.ofString());
			if (response.statusCode() == HttpURLConnection.HTTP_OK) {
				return compile(name, response.body());
			} else {
				return null;
			}
		} catch (IOException | InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
