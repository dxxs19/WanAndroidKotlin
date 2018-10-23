package com.wei.wanandroidkotlin.imgcompress

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.activity.BaseActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 *  A代表透明度；R代表红色；G代表绿色；B代表蓝色。
 *
 *  ALPHA_8
 *  表示8位Alpha位图,即A=8,一个像素点占用1个字节,它没有颜色,只有透明度
 *
 *  ARGB_4444
 *  表示16位ARGB位图，即A=4,R=4,G=4,B=4,一个像素点占4+4+4+4=16位，2个字节
 *
 *  ARGB_8888
 *  表示32位ARGB位图，即A=8,R=8,G=8,B=8,一个像素点占8+8+8+8=32位，4个字节
 *
 *  RGB_565
 *  表示16位RGB位图,即R=5,G=6,B=5,它没有透明度,一个像素点占5+6+5=16位，2个字节
 *  ---------------------
 *  作者：HarryWeasley
 *  来源：CSDN
 *  原文：https://blog.csdn.net/HarryWeasley/article/details/51955467
 *  版权声明：本文为博主原创文章，转载请附上博文链接！
 */
class CompressActivity : BaseActivity() {

    private var ivResult: ImageView? = null

    override fun layoutResId(): Int {
        return R.layout.activity_compress
    }

    override fun initData() {
    }

    override fun initNavBar() {
    }

    override fun initView() {
        ivResult = findViewById(R.id.iv_result)
        compressImage()
    }

    private fun compressImage() {
        val dcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val imgPath = dcimDir.path.plus("/").plus("Camera/IMG_20180924_095612.jpg")

//        rgb565(imgPath)

        matrixCompress(imgPath, 0.5f, 0.5f)

//        matrixCompress(imgPath, 0.5f, 0.5f)

//        sampleCompress(imgPath, 600, 800)

//        val bitmap = BitmapFactory.decodeFile(imgPath)
//        // Bitmap所占内存大小 = 图片长度 x 图片宽度 x 1像素点占用的字节数；3个参数，任意减小一个的值，就达到了压缩的效果
//        // 此处 = 4608 * 3456 * 4 = 60.75M
//        val result = "压缩前图片的大小" + (bitmap.byteCount / 1024 / 1024) + "M 宽度为" + bitmap.width +
//                "高度为" + bitmap.height
//        // 压缩前图片的大小60M 宽度为3456高度为4608
//        Log.e(TAG, result)
//
//        qualityCompress(bitmap)
    }

    // 图片大小直接缩小了一半，长度和宽度也没有变，相比argb_8888减少了一半的内存。
    private fun rgb565(imgPath: String) {
        val option = BitmapFactory.Options()
        option.inPreferredConfig = Bitmap.Config.RGB_565
        val bm = BitmapFactory.decodeFile(imgPath, option)
        Log.e(TAG, "压缩后图片的大小 : " + (bm.byteCount / 1024 / 1024)
                + "M 宽度为 " + bm.width + " 高度为 " + bm.height)

        ivResult?.setImageBitmap(bm)
    }

    private fun matrixCompress(imgPath: String, widthScale: Float, heightScale: Float) {
        val matrix = Matrix()
        matrix.setScale(widthScale, heightScale)
        matrix.postRotate(45f)
        val bitmap = BitmapFactory.decodeFile(imgPath)
        val bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        Log.e(TAG, "压缩后图片的大小 : " + (bm.byteCount / 1024 / 1024)
                + "M 宽度为 " + bm.width + " 高度为 " + bm.height)

        ivResult?.setImageBitmap(bm)
    }

    /**
     *  采样率压缩
     *  压缩后图片的大小 : 15M 宽度为: 1728 高度为: 2304
     */
    private fun sampleCompress(path: String, width: Int, height: Int) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bitmap = BitmapFactory.decodeFile(path, options)
        options.inJustDecodeBounds = false

        val outWidth = options.outWidth
        val outHeight = options.outHeight

        var ratio = 1

        if (outWidth > outHeight && outWidth > width) {
            ratio = outWidth / width
        } else if (outHeight > outWidth && outHeight > height) {
            ratio = outHeight / height
        }

        if (ratio <= 0) {
            ratio = 1
        }

        options.inSampleSize = ratio
        bitmap = BitmapFactory.decodeFile(path, options)

        Log.e(TAG, "压缩后图片的大小 : " + (bitmap.byteCount / 1024 / 1024)
                + "M 宽度为: " + bitmap.width + " 高度为: " + bitmap.height)
//        ivResult?.setImageBitmap(bitmap)
        qualityCompress(bitmap, 200)
    }

    /**
     *  质量压缩：图片体积会变小，但占用内存大小不变。画质会随着质量值的变小而变差
     *  可以看到，图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，
     *  来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法。那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的。
     *  但是我们看到bytes.length是随着quality变小而变小的。这样适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内。
     *  这里要说，如果是bit.compress(CompressFormat.PNG, quality, baos);这样的png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，
     *  不能进行压缩。
     *  CompressFormat还有一个属性是，CompressFormat.WEBP格式，该格式是google自己推出来一个图片格式，更多信息，文末会贴出地址。
     */
    private fun qualityCompress(bitmap: Bitmap, limit: Int = 100) {
        val baos = ByteArrayOutputStream()
        var quality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)

        while (baos.toByteArray().size / 1024 > limit) {
            //重置baos即清空baos
            baos.reset()
            //这里压缩quality%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            quality -= 10
        }

        val bytes = baos.toByteArray()
        val bais = ByteArrayInputStream(bytes)
        val bm = BitmapFactory.decodeStream(bais)

//        val bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        // 压缩后图片的大小60M 宽度为3456 高度为4608  bytes.length = 3517KB quality=80
        // 压缩后图片的大小60M 宽度为3456 高度为4608  bytes.length = 2330KB quality=60
        // 压缩后图片的大小60M 宽度为3456 高度为4608  bytes.length = 1061KB quality=20
        // 压缩后图片的大小60M 宽度为3456 高度为4608  bytes.length = 169KB quality=0
        Log.e(TAG, "压缩后图片的大小 : " + (bm.byteCount / 1024 / 1024)
                + "M 宽度为" + bm.width + " 高度为" + bm.height
                + "  bytes.length = " + (bytes.size / 1024) + "KB"
                + " quality=" + quality)

        ivResult?.setImageBitmap(bm)
    }

}
