package common.encoding;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TextCodecJackson implements TextCodec {

	private final ObjectMapper mapper;

	public TextCodecJackson() {
		mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> decodeAsMap(String source) {
		try {
			return mapper.readValue(source, Map.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> decodeAsMap(Reader reader) {
		try {
			return mapper.readValue(reader, Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void encode(Object object, Writer writer) {
		try {
			mapper.writeValue(writer, object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String encode(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
