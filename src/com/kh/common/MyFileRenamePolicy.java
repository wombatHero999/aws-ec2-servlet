package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// 인터페이스를 구현해야함 => implements 키워드 이용
public class MyFileRenamePolicy implements FileRenamePolicy {

	// 반드시 미완성된 rename 메소드를 오버라이딩 해서 구현해야함!!
	// 기존의 파일을 전달받아서 파일명 수정작업 후에 수정된 파일을 반환해주는 메소드
	@Override
	public File rename(File originFile) {

		// 원본파일명 ("aaa.jpg")
		String originName = originFile.getName();

		// 수정파일명 : 파일업로드된시간(년월일시분초) + 5자리랜덤값(10000~99999) => 최대한 이름이 겹치지 않게
		// 확장자 : 원본파일의 확장자 그대로
		
		// 원본명       -->     수정명
		// aaa.jpg --> 2021091215175510245.jpg

		// 1. 파일업로드된시간 (년월일시분초) => String currentTime;
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // java.util 패키지에 있는 클래스
		
		// 2. 5자리랜덤값 => int ranNum;
		int ranNum = (int)(Math.random() * 90000) + 10000;
		
		// 3. 원본파일확장자 (String ext)
		// 파일명 중간에 . 이 들어가는 경우도 있기 때문에
		// 원본파일명에서 가장 맨 마지막의 . 의 인덱스를 기준으로 파일명과 확장자를 나눈다.
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext; // "2021091215175510245.jpg"
		
		// 원본파일(originFile) 을 수정된 파일명으로 적용시켜서 파일객체로 반환 
		return new File(originFile.getParent(), changeName);
	}
	
	

}
