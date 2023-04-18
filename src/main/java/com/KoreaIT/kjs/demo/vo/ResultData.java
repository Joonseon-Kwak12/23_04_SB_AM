package com.KoreaIT.kjs.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Object data1;
	
	public static ResultData from(String resultCode, String msg) { // 가져올 데이터가 없는 경우
		return from(resultCode, msg, null);
	}
	
	public static ResultData from(String resultCode, String msg, Object data1) {
		ResultData rd = new ResultData();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return !isSuccess();
	}
	
	public boolean isotropic() {
		return !isSuccess();
	}

	public static ResultData newData(ResultData joinRd, Object newData) {
		return from(joinRd.getResultCode(), joinRd.getMsg(), newData);
		// 원래 받은 데이터에서 resultCode와 msg는 그대로 갖고 오고, data1 자리에 있는 것만 교체해줌
	}

}
