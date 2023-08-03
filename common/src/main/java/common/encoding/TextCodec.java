package common.encoding;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public interface TextCodec {

	Map<String, Object> decodeAsMap(String source);

	Map<String, Object> decodeAsMap(Reader reader);

	void encode(Object object, Writer writer);

	String encode(Object object);

}
