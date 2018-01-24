package com.samton.ibenrobotdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos80;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhg on 2017/7/6.
 */
public class PrintBuilder {

    /**
     * 给bitmap 设置新的宽 高
     *
     * @param origin 要设置的Bitamp
     * @return 设置过新的宽 高的Bitmap
     */
//    public static Bitmap scaleBitmap(Bitmap origin) {
//        if (origin == null) {
//            return null;
//        }
//
//        int height = origin.getHeight();
//        int width = origin.getWidth();
//        float scaleWidth;
//        float scaleHeight;
//        if (width > height) {
//            scaleWidth = 400 / width;
//            scaleHeight = scaleWidth;
//        } else {
//            scaleHeight = 400 / height;
//            scaleWidth = scaleHeight;
//        }
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
//
//        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
//        return newBM;
//    }
//
//    public static Bitmap scaleBitmaps(Bitmap bitmap, int newWidth, int newHeight) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//        int bitmapSize = os.toByteArray().length / 1024;
//        int ratio = 5000 / bitmapSize;
//        Log.e("Liu", "图片大小: KB" + bitmapSize);
//        if (os.toByteArray().length / 1024 > 64) {
//            os.reset();
//            if (ratio > 100 || ratio < 10) {
//                ratio = 50;
//            }
//            Log.e("Liu", "压缩比例" + ratio + "%");
//            bitmap.compress(Bitmap.CompressFormat.JPEG, ratio, os);
//        }
//        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了    
//        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
//        BitmapFactory.decodeStream(is, null, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        if (newHeight >= 400) {
//            newHeight = 400;
//        }
//        if (newWidth >= 400) {
//            newWidth = 400;
//        }
//
//        if (newWidth == newHeight && newHeight == 0) {
//            newWidth = 400;
//            newHeight = 400;
//        }
//
//
//        int hh = newHeight;// 设置高度为240f时，可以明显看到图片缩小了  
//        int ww = newWidth;// 设置宽度为120f，可以明显看到图片缩小了  
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可    
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放 
//            if (ww != 0) {
//                be = (newOpts.outWidth / ww);
//            }
//
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放    
//            if (hh != 0)
//                be = (newOpts.outHeight / hh);
//        }
//        if (be <= 0) be = 1;
////        newOpts.inSampleSize = be;//设置缩放比
//        newOpts.inPreferredConfig = Bitmap.Config.ALPHA_8;
//        newOpts.inSampleSize = 4;//设置缩放比
////        Log.e("Liu", "设置缩放比: " + be);
//        is = new ByteArrayInputStream(os.toByteArray());
//        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
//        Log.e("Liu", "宽: " + bitmap.getHeight() + " 高 " + bitmap.getWidth());
//        //压缩好比例大小后再进行质量压缩  
//        return bitmap;
//    }


    /**
     * 给bitmap 设置新的宽 高
     *
     * @param origin    要设置的Bitamp
     * @param newWidth  宽
     * @param newHeight 高
     * @return 设置过新的宽 高的Bitmap
     */
    public static Bitmap scaleBitmaps(Bitmap origin, int newWidth, int newHeight) {
        origin = compressImage(origin);

        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 固定宽 高自适应
        if (scaleHeight <= 10) {
            scaleHeight = scaleWidth;
        }
        //固定高  宽自适应
        if (scaleWidth <= 10) {
            scaleWidth = scaleHeight;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;
        while (baos.toByteArray().length / 1024 > 50) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;
    }

    public static List<byte[]> printBitmap(Bitmap bitmap, int align, int line) {
        BitmapToByteData.AlignType mAlign;
        switch (align) {
            case 1:
                mAlign = BitmapToByteData.AlignType.Left;
                break;
            case 2:
                mAlign = BitmapToByteData.AlignType.Center;
                break;
            case 3:
                mAlign = BitmapToByteData.AlignType.Right;
                break;
            default:
                mAlign = BitmapToByteData.AlignType.Center;
                break;
        }
        ArrayList<byte[]> list = new ArrayList<>();
        list.add(DataForSendToPrinterPos80.initializePrinter());
        list.add(DataForSendToPrinterPos80.selectAlignment(0));
        list.add(DataForSendToPrinterPos80.printRasterBmp(
                0, bitmap, BitmapToByteData.BmpType.Threshold, mAlign, 570));
        list.add(DataForSendToPrinterPos80.printAndFeedForward(2));
        return list;
    }

    /**
     * @param content 打印文字
     * @param align   对齐方式
     * @param size    字体大小
     * @param line    自动换行(换几行)
     * @return 打印内容
     */
    public static List<byte[]> printContent(String content, int align, int size, int line) {
        ArrayList<byte[]> list = new ArrayList<>();
        list.add(DataForSendToPrinterPos80.initializePrinter());
        //文本内容转换
        byte[] data1 = strTobytes(content);
        //设置字体大小
        list.add(DataForSendToPrinterPos80.selectCharacterSize(size));
        //设置打印位置
        list.add(DataForSendToPrinterPos80.selectAlignment(align));
        //设置打印内容
        list.add(data1);
        //追加一个打印换行指令，因为，pos打印机满一行才打印，不足一行，不打印
        list.add(DataForSendToPrinterPos80.printAndFeedForward(line));
        return list;
    }


    /**
     * 字符串转byte数组
     */
    public static byte[] strTobytes(String str) {
        byte[] b = null, data = null;
        try {
            b = str.getBytes("utf-8");
            data = new String(b, "utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }


}
