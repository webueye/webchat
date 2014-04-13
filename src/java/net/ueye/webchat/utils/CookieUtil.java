package net.ueye.webchat.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.webchat.util.StringUtils;

public class CookieUtil {

	public static String getChatID(HttpServletRequest request,
			HttpServletResponse response) {
		String chatIDKey = "chatID";
		Cookie cookie = getCookie(request, chatIDKey);
		if (cookie == null) {
			Cookie chatCookie = new Cookie(chatIDKey, genChatID());
			chatCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(chatCookie);
			return chatCookie.getValue();
		}
		if (cookie.getValue() == null || "".equals(cookie.getValue())) {
			cookie.setValue(genChatID());
		}
		return cookie.getValue();
	}

	public static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (key.equalsIgnoreCase(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	public static String genChatID() {
		return StringUtils.randomString(10);
	}

}
