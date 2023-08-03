package common.template;

import java.io.StringReader;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public abstract class TemplateEngineMustache implements TemplateEngine {

	private final MustacheFactory factory;

	public TemplateEngineMustache() {
		factory = new DefaultMustacheFactory();
	}

	@Override
	public String eval(String name, Object context) {
		Template template = getTemplate(name);
		if (template == null) {
			return null;
		} else {
			return template.evaluate(context);
		}
	}

	protected Template compile(String name, String source) {
		try (StringReader reader = new StringReader(source)) {
			return new TemplateMustache(factory.compile(reader, name));
		}
	}

	protected abstract Template getTemplate(String name);

}
