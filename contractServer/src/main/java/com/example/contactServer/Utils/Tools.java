package com.example.contactServer.Utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import Decoder.BASE64Decoder;

public class Tools
{
    static public void base64StringToImage(String base64String,String filename) {
        try {
        	var decoder=new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64String.substring(22));
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File(filename);
            ImageIO.write(bi1, "png", f1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}