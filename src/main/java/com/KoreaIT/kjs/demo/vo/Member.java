package com.KoreaIT.kjs.demo.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	
	private int id;
	private LocalDateTime regDate;
	private String title;
	private String body;
}