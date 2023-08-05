package classic;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.encoding.TextCodec;
import common.encoding.TextCodecJackson;
import common.template.TemplateEngine;
import common.template.TemplateEngineMustacheLocal;

public class ClassicServlet extends HttpServlet {

	private static final long serialVersionUID = -5251088125261129626L;

	private final TextCodec codec;

	private final TemplateEngine engine;

	public ClassicServlet() {
		codec = new TextCodecJackson();
		engine = new TemplateEngineMustacheLocal();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "OPTIONS,POST");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		String contentType = request.getContentType();
		if (contentType.equals("application/json")) {
			Map<String, Object> data;
			try (Reader reader = request.getReader()) {
				data = codec.decodeAsMap(reader);
			}
			String text = engine.eval(request.getPathInfo(), data);
			if (text == null) {
				response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
			} else {
				response.setContentType("text/html");
				response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
				try (Writer writer = response.getWriter()) {
					writer.write(text);
				}
			}
		}
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "OPTIONS,POST");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	}
}
