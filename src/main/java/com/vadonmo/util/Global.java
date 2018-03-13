package com.vadonmo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class Global {
	public static String ACCESS_TOKEN = "7_9wknxo0NjIUCj4BC2Tz1KAzvAq5u9prRMkaV6YmRYKGl879HGZAJriEFsLSvQq9a4Bs7tGlmz5Nxkxb06IxzKcjpJENfTfPyciUZLZC7ZZhqErfhHLWnAEgO81P1CFbjkEmN6dgbIiDCaTM9SZAaAFAIPQ";
	public static Long ACCESS_TOKEN_UPDATE_TIME = 1520830699814L;
	public static String APPID = "wx0b1fe344d1930600";
	public static String APPSECRET = "cafcd6c58562b968c1bd3215f4a7d06f";

	public static String toStringInfo(HttpEntity entity, String defaultCharset) throws Exception, IOException {
		final InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
					"HTTP entity too large to be buffered in memory");
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Charset charset = null;

			if (charset == null) {
				charset = Charset.forName(defaultCharset);
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			final Reader reader = new InputStreamReader(instream, charset);
			final CharArrayBuffer buffer = new CharArrayBuffer(i);
			final char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} finally {
			instream.close();
		}
	}
}
