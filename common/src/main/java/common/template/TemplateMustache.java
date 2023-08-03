package common.template;

import java.io.IOException;
import java.io.StringWriter;

import com.github.mustachejava.Mustache;

public class TemplateMustache implements Template {

	private final Mustache real;

	public TemplateMustache(Mustache mustache) {
		this.real = mustache;
	}

	@Override
	public String evaluate(Object context) {
		try (StringWriter writer = new StringWriter()) {
			real.execute(writer, context);
			writer.flush();
			return writer.toString();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
