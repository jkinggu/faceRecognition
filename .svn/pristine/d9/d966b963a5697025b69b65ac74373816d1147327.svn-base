package com.dx.util;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.MemoryImageSource;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageLoader {
    public static final boolean USING_FLOAT = false;
    
    public static BufferInfo getI420FromFile(String filePath) {
        byte[] yuv = null;
        int w = 0;
        int h = 0;
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            if (((img.getWidth() & 0x1) != 0) || ((img.getHeight() & 0x1) != 0)) {
                img = img.getSubimage(0, 0, img.getWidth() & 0xFFFFFFFE, img.getHeight() & 0xFFFFFFFE);
            }
            w = img.getWidth();
            h = img.getHeight();
            int[] bgra = img.getRGB(0, 0, w, h, null, 0, w);
            if(USING_FLOAT){
                yuv = BGRA2I420_float(bgra, w, h);
            }else{
                yuv = BGRA2I420(bgra, w, h);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return new BufferInfo(w, h, yuv);
    }
    
    public static BufferInfo getBGRFromFile(String filePath) {
        byte[] bgr = null;
        int width = 0;
        int height = 0;
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            width = img.getWidth();
            height = img.getHeight();
            BufferedImage bgrimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            bgrimg.setRGB(0, 0, width, height, img.getRGB(0, 0, width, height, null, 0, width), 0, width);
            bgr = ((DataBufferByte)bgrimg.getRaster().getDataBuffer()).getData();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return new BufferInfo(width, height, bgr);
    }
    
    //Full swing for BT.601
    public static byte[] BGRA2I420(int[] bgra, int width, int height) {

        byte[] yuv = new byte[width * height * 3 / 2];
        int u_offset = width * height;
        int y_offset = width * height * 5 / 4;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = bgra[i * width + j] & 0x00FFFFFF;
                int b = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int r = (rgb >> 16) & 0xFF;

                int y = ((77 * r + 150 * g + 29 * b + 128) >> 8);
                int u = (((-43) * r - 84 * g + 127 * b + 128) >> 8) + 128;
                int v = ((127 * r - 106 * g - 21 * b + 128) >> 8) + 128;

                y = y < 0 ? 0 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
                yuv[u_offset + (i >> 1) * (width >> 1) + (j >> 1)] = (byte) u;
                yuv[y_offset + (i >> 1) * (width >> 1) + (j >> 1)] = (byte) v;
            }
        }
        return yuv;
    } 
    
    // ITU-R standard for YCbCr
    // Y =  0.299R + 0.587G + 0.114B
    // U = -0.169R - 0.331G + 0.499B + 128
    // V =  0.499R - 0.418G - 0.0813B + 128
    
    public static byte[] BGRA2I420_float(int[] bgra, int width, int height) {

        byte[] yuv = new byte[width * height * 3 / 2];
        int u_offset = width * height;
        int y_offset = width * height * 5 / 4;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = bgra[i * width + j] & 0x00FFFFFF;
                float b = (rgb & 0xFF);
                float g = ((rgb >> 8) & 0xFF);
                float r = ((rgb >> 16) & 0xFF);

                float y = (0.299f * r + 0.587f * g + 0.114f * b) ;
                float u = (-0.169f) * r - 0.331f* g + 0.499f * b + 128.0f;
                float v = 0.499f * r - 0.418f * g - 0.0813f * b   + 128.0f;

                yuv[i * width + j] = (byte) y;
                yuv[u_offset + (i >> 1) * (width >> 1) + (j >> 1)] = (byte) u;
                yuv[y_offset + (i >> 1) * (width >> 1) + (j >> 1)] = (byte) v;
            }
        }
        return yuv;
    } 
    
    
    public void reader(String file,String filepath) {
        try {
          FileInputStream in = new FileInputStream(file);
          Image TheImage = read(in);
          int wideth = TheImage.getWidth(null);
          int height = TheImage.getHeight(null);
          BufferedImage tag = new BufferedImage(wideth / 2, height / 2,
                                                BufferedImage.TYPE_INT_RGB);
          tag.getGraphics().drawImage(TheImage, 0, 0, wideth / 2, height / 2, null);
          FileOutputStream out = new FileOutputStream(filepath + ".jpg");
          JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
          encoder.encode(tag);
          out.close();
        }
        catch (Exception e) {
          System.out.println(e);
        }
      }

      public static int constructInt(byte[] in, int offset) {
        int ret = ( (int) in[offset + 3] & 0xff);
        ret = (ret << 8) | ( (int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ( (int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ( (int) in[offset + 0] & 0xff);
        return (ret);
      }

      public static int constructInt3(byte[] in, int offset) {
        int ret = 0xff;
        ret = (ret << 8) | ( (int) in[offset + 2] & 0xff);
        ret = (ret << 8) | ( (int) in[offset + 1] & 0xff);
        ret = (ret << 8) | ( (int) in[offset + 0] & 0xff);
        return (ret);
      }

      public static long constructLong(byte[] in, int offset) {
        long ret = ( (long) in[offset + 7] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 6] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 5] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 4] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 3] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 2] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 1] & 0xff);
        ret |= (ret << 8) | ( (long) in[offset + 0] & 0xff);
        return (ret);
      }

      public static double constructDouble(byte[] in, int offset) {
        long ret = constructLong(in, offset);
        return (Double.longBitsToDouble(ret));
      }

      public static short constructShort(byte[] in, int offset) {
        short ret = (short) ( (short) in[offset + 1] & 0xff);
        ret = (short) ( (ret << 8) | (short) ( (short) in[offset + 0] & 0xff));
        return (ret);
      }

      static class BitmapHeader {
        public int iSize, ibiSize, iWidth, iHeight, iPlanes, iBitcount,
            iCompression, iSizeimage, iXpm, iYpm, iClrused, iClrimp;
        // 读取bmp文件头信息
        public void read(FileInputStream fs) throws IOException {
          final int bflen = 14;
          byte bf[] = new byte[bflen];
          fs.read(bf, 0, bflen);
          final int bilen = 40;
          byte bi[] = new byte[bilen];
          fs.read(bi, 0, bilen);
          iSize = constructInt(bf, 2);
          ibiSize = constructInt(bi, 2);
          iWidth = constructInt(bi, 4);
          iHeight = constructInt(bi, 8);
          iPlanes = constructShort(bi, 12);
          iBitcount = constructShort(bi, 14);
          iCompression = constructInt(bi, 16);
          iSizeimage = constructInt(bi, 20);
          iXpm = constructInt(bi, 24);
          iYpm = constructInt(bi, 28);
          iClrused = constructInt(bi, 32);
          iClrimp = constructInt(bi, 36);
        }
      }

      public static Image read(FileInputStream fs) {
        try {
          BitmapHeader bh = new BitmapHeader();
          bh.read(fs);
          if (bh.iBitcount == 24) {
            return (readImage24(fs, bh));
          }
          if (bh.iBitcount == 32) {
            return (readImage32(fs, bh));
          }
          fs.close();
        }
        catch (IOException e) {
          System.out.println(e);
        }
        return (null);
      }

      //24位
      protected static Image readImage24(FileInputStream fs, BitmapHeader bh) throws
          IOException {
        Image image;
        if (bh.iSizeimage == 0) {
          bh.iSizeimage = ( ( ( (bh.iWidth * bh.iBitcount) + 31) & ~31) >> 3);
          bh.iSizeimage *= bh.iHeight;
        }
        int npad = (bh.iSizeimage / bh.iHeight) - bh.iWidth * 3;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[ (bh.iWidth + npad) * 3 * bh.iHeight];
        fs.read(brgb, 0, (bh.iWidth + npad) * 3 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
          for (int i = 0; i < bh.iWidth; i++) {
            ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
                brgb, nindex);
            nindex += 3;
          }
          nindex += npad;
        }
        image = Toolkit.getDefaultToolkit().createImage
            (new MemoryImageSource(bh.iWidth, bh.iHeight,
                                   ndata, 0, bh.iWidth));
        fs.close();
        return (image);
      }

    //32位
      protected static Image readImage32(FileInputStream fs, BitmapHeader bh) throws
          IOException {
        Image image;
        int xwidth = bh.iSizeimage / bh.iHeight;
        int ndata[] = new int[bh.iHeight * bh.iWidth];
        byte brgb[] = new byte[bh.iWidth * 4 * bh.iHeight];
        fs.read(brgb, 0, bh.iWidth * 4 * bh.iHeight);
        int nindex = 0;
        for (int j = 0; j < bh.iHeight; j++) {
          for (int i = 0; i < bh.iWidth; i++) {
            ndata[bh.iWidth * (bh.iHeight - j - 1) + i] = constructInt3(
                brgb, nindex);
            nindex += 4;
          }
        }
        image = Toolkit.getDefaultToolkit().createImage
            (new MemoryImageSource(bh.iWidth, bh.iHeight, ndata, 0, bh.iWidth));
        fs.close();
        return (image);
      }

      public static Image load(String sdir, String sfile) {
        return (load(sdir + sfile));
      }

      public static Image load(String sdir) {
        try {
          FileInputStream fs = new FileInputStream(sdir);
          return (read(fs));
        }
        catch (IOException ex) {
          return (null);
        }
      }

      public ImageDialog getBmpImage(String filePath) throws IOException {
        if (filePath.equals("")) {
          System.out.println("输入bmp文件名");
          return null;
        }
        else {
          FileInputStream in = new FileInputStream(filePath);
          Image TheImage = read(in);
          ImageDialog container = new ImageDialog(new ImageIcon(TheImage));
          return container;
        }
      }

      public ImageIcon getBmpImageIcon(String filePath) throws IOException {
        if (filePath.equals("")) {
          System.err.println("输入bmp文件名");
          return null;
        }
        else {
          FileInputStream in = new FileInputStream(filePath);
          Image TheImage = read(in);
          return new ImageIcon(TheImage);
        }
      }

    
    public static void main(String[] args) {
    	ImageLoader il = new ImageLoader();
    	String sfzpho = "D:\\faceimages\\411502199012119323/411502199012119323zp.bmp" ;
    	il.reader(sfzpho,"D:\\faceimages\\411502199012119323/411502199012119323");
	}
    
    
    
    
    
    
}
