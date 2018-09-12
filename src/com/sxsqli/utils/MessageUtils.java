package com.sxsqli.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageUtils {

	// 将对应信息转发到message.jsp
	public static void forwardToMessage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
}
