//package com.tf.sdk.third;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//
///**
// * 
// * @author chenjx
// * @Version 1.0.0
// * @time   2018年5月12日
// */
//public class TwoCodeUtil {
//	private static final Logger logger = LoggerFactory.getLogger(TwoCodeUtil.class);
//	
//	private static final int BLACK = 0xFF000000;
//	private static final int WHITE = 0xFFFFFFFF;
//	private static final String IMAGE_FORMAT = "png";
//	private static final String CHARACTER_SET = "UTF-8";
//	
//	
//	/**
//	 * 将内容生成二维码指定到生成路径
//	 * @param content
//	 * @param pathFile
//	 * @return
//	 */
//	public static boolean encodeQrcode(String content, String pathFile) {
//		if(		content==null || content.length() == 0
//				|| pathFile == null || pathFile.length() == 0) { 
//	        return false;  
//		}
//	    int width = 300;
//        int height = 300;
//        //定义二维码的参数
//        HashMap<Object,Object> map = new HashMap<Object,Object>();
//        //设置编码
//        map.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
//        //设置纠错等级
//        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//        map.put(EncodeHintType.MARGIN, 2);
//
//        try {
//            //生成二维码
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
//            Path file = new File(pathFile).toPath();
//            MatrixToImageWriter.writeToPath(bitMatrix, IMAGE_FORMAT, file);
//        } catch (WriterException e) {
//            logger.error(e.getMessage());
//            return false;
//        } catch (IOException e) {
//        	logger.error(e.getMessage());
//        	return false;
//        }
//        
//        return true;
//	}
//	
//	/**
//	 * 将指定内容生成二维码流到response
//	 * @param content
//	 * @param response
//	 * @return
//	 */
//	public static boolean encodeQrcode(String content, HttpServletResponse response) {
//		if(content==null || content.length() == 0 || response == null) { 
//	        return false;  
//		}
//	   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
//	   Map<EncodeHintType, Object>  hints = new HashMap<EncodeHintType, Object>();  
//	   hints.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET); //设置字符集编码类型  
//	   BitMatrix bitMatrix = null;  
//	   try {  
//	       bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);  
//	       BufferedImage image = toBufferedImage(bitMatrix);  
//	       //输出二维码图片流  
//	       ImageIO.write(image, IMAGE_FORMAT, response.getOutputStream());  
//       } catch (IOException e) {  
//    	   logger.error(e.getMessage());
//    	   return false; 
//	   } catch (WriterException e) {  
//    	   logger.error(e.getMessage());
//    	   return false;  
//	   }
//	   
//	   return true;
//	}
//	
//	
//
//	private static BufferedImage toBufferedImage(BitMatrix matrix) {
//	     int width = matrix.getWidth();
//	     int height = matrix.getHeight();
//	     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//	     for (int x = 0; x < width; x++) {
//	       for (int y = 0; y < height; y++) {
//	         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
//	       }
//	     }
//	     return image;
//	 }
//}
