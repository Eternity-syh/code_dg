package com.jjld.code;

public class QrTest {
	public static void main(String[] args) throws Exception {
		// 存放在二维码中的内容
		String text="http://192.168.31.203:8080/nutzb/index.html?wt=jjldu";
		//嵌入二维码的图片路径
		String imgPath="F:\\image\\20.png";
		//生成的二维码的路径及名称
		String destPath="D:\\jam.jpg";
		//生成二维码
		QRCodeUTil.encode(text, imgPath,destPath,true);
		//解析二维码
		String str = QRCodeUTil.decode(destPath);
		System.out.println(str);
	}
}
