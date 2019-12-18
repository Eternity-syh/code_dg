package com.jjld.code;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class QRCodeUTil {
	private static final String CHARSET="utf-8";
	private static final String FORMAT_NAME="JPG";
	//二维码尺寸
	private static final int QRCODE_SIZE =300;
	// LOGO宽度
	private static final int WIDTH = 60;
	// LOGO高度
	private static final int HEIGHT = 60;
	
	@SuppressWarnings("unchecked")
	private static BufferedImage createImage(String content,String imgPath,boolean needCompress) throws IOException, WriterException {
		Hashtable<EncodeHintType, Comparable> hints= new Hashtable<>();
		//EncodeHintType编码类型
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		//创建二维码
		// MultiFormatWriter  多格式写 encode  编码
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image  = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				image.setRGB(x, y, bitMatrix.get(x, y)? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if(imgPath==null||"".equals(imgPath)) {
			return image;
		}
		QRCodeUTil.insertImage(image, imgPath, needCompress);
		return image;
	}
	private static void insertImage(BufferedImage source,String imgPath,boolean needCompress) throws IOException  {
		File file = new File(imgPath);
		if(!file.exists()) {
			System.out.println(""+imgPath+ "   该文件不存在！");
			return ;
		}
		//提供read()和write()静态方法，读写图片，比以往的InputStream读写更方便。
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if(needCompress) {
			if(width>WIDTH) {
				width=WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			//获取尺度
			Image image = src.getScaledInstance(width, height,Image.SCALE_SMOOTH );
			//缓冲图片
			BufferedImage tag  = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
			//获得在图像上绘图的Graphics对象
			Graphics g = tag.getGraphics();
			g.drawImage(image,0,0,null);
			g.dispose();
			src = image;
		}
		//插入LOGO
		//创建绘图
		Graphics2D graph = source.createGraphics();
		int x=(QRCODE_SIZE-width)/2;
		int y=(QRCODE_SIZE-height)/2;
		// 绘制缩小后的图
		graph.drawImage(src, x, y, width, height, null);
		//创建绘制矩形
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		//把绘制矩形放到Graphics对象中
		graph.draw(shape);
		//处理
		graph.dispose();
	}
	
	public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws IOException, WriterException {
		BufferedImage image = QRCodeUTil.createImage(content, imgPath, needCompress);
		mkdirs(destPath);
		// String file = new Random().nextInt(99999999)+".jpg";
		// ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
		ImageIO.write(image, FORMAT_NAME, new File(destPath));
		
	}
	public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception {
		BufferedImage image = QRCodeUTil.createImage(content, imgPath, needCompress);
		return image;
	}


	private static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if(!file.exists()&&!file.isDirectory()) {
			file.mkdirs();
		}
		
	}
	public static void encode(String content, String imgPath, String destPath) throws Exception {
		QRCodeUTil.encode(content, imgPath, destPath, false);
	}
	// 被注释的方法
	/*
	 * public static void encode(String content, String destPath, boolean
	 * needCompress) throws Exception { QRCodeUtil.encode(content, null, destPath,
	 * needCompress); }
	 */
 
	public static void encode(String content, String destPath) throws Exception {
		QRCodeUTil.encode(content, null, destPath, false);
	}
 
	public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
			throws Exception {
		BufferedImage image = QRCodeUTil.createImage(content, imgPath, needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}
 
	public static void encode(String content, OutputStream output) throws Exception {
		QRCodeUTil.encode(content, null, output, false);
	}
 
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		//谷歌给的工具类
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		// BinaryBitmap是ZXing用来表示1 bit data位图的类，Reader对象将对它进行解析
		//HybridBinarizer和GlobalHistogramBinarizer 是zxing在对图像进行二值化算法的两个自带类。
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, String> hints = new Hashtable<>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		// MultiFormatReader 多格式读  decode 解码
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}
 
	public static String decode(String path) throws Exception {
		return QRCodeUTil.decode(new File(path));
	}

}
