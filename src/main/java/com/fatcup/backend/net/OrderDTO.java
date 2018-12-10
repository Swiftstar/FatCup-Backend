package com.fatcup.backend.net;

import java.util.List;
import java.util.Map;

public class OrderDTO {
	public String uid;
	public Long orderId;
	public List<Map<String, Object>> orderslist;
	public String language;
	public double latitude;
	public double longitude;
	public String remark;
}
