package com.KoreaIT.kjs.demo.vo;

import lombok.Getter;

public class ResultData<DT> {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	@Getter
	private String data1Name;
	
	public static <DT> ResultData<DT> from(String resultCode, String msg) { // 가져올 데이터가 없는 경우
		return from(resultCode, msg, null, null);
	}
	
	public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
		ResultData<DT> rd = new ResultData<>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return !isSuccess();
	}

	public static <DT> ResultData<DT> newData(ResultData rd, String Data1Name, DT newData) {
		return from(rd.getResultCode(), rd.getMsg(), Data1Name, newData);
		// 원래 받은 데이터에서 resultCode와 msg는 그대로 갖고 오고, data1 자리에 있는 것만 교체해줌
	}

}