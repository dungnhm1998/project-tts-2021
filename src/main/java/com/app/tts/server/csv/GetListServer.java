package com.app.tts.server.csv;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.app.tts.services.BaseService;
import com.app.tts.util.ParamUtil;

public class GetListServer {
	public static int n = 0;
	public static List<Map> orders = new LinkedList();
	
	public static List<Map> getListOrder() throws SQLException {
		orders = BaseService.getOrder();
		return orders;
	}
}
