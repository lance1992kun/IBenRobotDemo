package com.samton.IBenRobotSDK.print;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.samton.IBenRobotSDK.utils.StringUtils;

import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos80;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhg on 2017/7/6.
 * 打印内容构建
 */
public class PrintBuilder {

    /**
     * 给bitmap 设置新的宽 高
     *
     * @param origin    要设置的Bitamp
     * @param newWidth  宽
     * @param newHeight 高
     * @return 设置过新的宽 高的Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }

        int height = origin.getHeight();
        int width = origin.getWidth();
        //没有宽 高时 默认宽高 400
        if (newHeight <= 10 && newWidth <= 10) {
            newHeight = newWidth = 400;
        }
        // 芯烨 打印机 打印图片有宽度限制  超过限制会打印乱码
        if (newWidth > 1000) {
            newWidth = 576;
        }
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 固定宽 高为零时 高自适应
        if (scaleHeight <= 0) {
            scaleHeight = scaleWidth;
        }
        //固定高  宽为零时 宽自适应
        if (scaleWidth <= 0) {
            scaleWidth = scaleHeight;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }

    public static List<byte[]> printBitmap(Bitmap bitmap, int line) {
        ArrayList<byte[]> list = new ArrayList<>();
        list.add(DataForSendToPrinterPos80.selectAlignment(0));
        list.add(DataForSendToPrinterPos80.printRasterBmp(
                0, bitmap, BitmapToByteData.BmpType.Threshold, BitmapToByteData.AlignType.Center, 576));
        list.add(DataForSendToPrinterPos80.printAndFeedForward(line));
        return list;
    }

    /**
     * @param content 打印文字
     * @param align   打印位置
     * @param size    打印文字大小
     * @param line    打印之后换行数
     * @return
     */
    public static List<byte[]> printContent(String content, int align, int size, int line) {
        ArrayList<byte[]> list = new ArrayList<>();
        list.add(DataForSendToPrinterPos80.initializePrinter());
        // 文本内容转换
        byte[] data1 = StringUtils.str2bytes(content);
        // 设置字体大小
        list.add(DataForSendToPrinterPos80.selectCharacterSize(size));
        // 设置打印位置
        list.add(DataForSendToPrinterPos80.selectAlignment(align));
        // 设置打印内容
        list.add(data1);
        // 追加一个打印换行指令，因为，pos打印机满一行才打印，不足一行，不打印
        list.add(DataForSendToPrinterPos80.printAndFeedForward(line));
        return list;
    }
}
