package common.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TemplateEngineMustacheLocal extends TemplateEngineMustache {

	private static final String TEMPLATES;

	static {
		String source = System.getenv("TEMPLATES");
		if (source == null || source.isBlank()) {
			throw new IllegalStateException("no TEMPLATES defined");
		} else {
			TEMPLATES = source;
		}
	}

	@Override
	protected Template getTemplate(String name) {
		String path = TEMPLATES + name + ".mustache";
		File file = new File(path);
		if (file.isFile()) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
				StringBuilder builder = new StringBuilder();
				char[] buffer = new char[1000];
				while (true) {
					int count = reader.read(buffer);
					if (count < 0) {
						break;
					} else {
						for (int i = 0; i < count; i++) {
							builder.append(buffer[i]);
						}
					}
				}
				return compile(name, builder.toString());
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		} else {
			return null;
		}
	}

}
