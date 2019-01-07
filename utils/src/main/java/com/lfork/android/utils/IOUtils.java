package com.lfork.android.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.*;

/*
 * Created by 98620 on 2017/11/3.
 */

public class IOUtils {

//    private static final String TAG = "PersonsFileHelper";
//    public static String ExternalCacheDirStringURL;
//    public static String SDRootPath;
//
//    public static String KeyPath, ValuePath;
//    private static boolean IsDirInitialed = false;
//
//    static {
//        SDRootPath = Environment.getExternalStorageDirectory().toString();
//        ExternalCacheDirStringURL = Environment.getDownloadCacheDirectory().toString();
//    }


    /**
     * 将文件输转换成字符串
     * @param filePath 源文件路径
     * @return 字符串结果
     */
    public static String loadStr(@NonNull String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }
        StringBuilder result = new StringBuilder();


        BufferedReader in = null;
        try {
            // 当该文件不存在时再创建一个新的文件
            file.createNewFile();

            FileInputStream fis = new FileInputStream(file);

            // 这样写的话可以指定文本的编码格式 *****
            InputStreamReader isr = new InputStreamReader(fis);

            in = new BufferedReader(isr);

            // 这里是按照字符流进行的读取

            String str = null;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();

    }

    /**
     * 将文件输转换成字符串
     * @param file 源文件
     * @return 字符串结果
     */
    public static String loadStr(@NonNull File file,@NonNull String encoding) {
        if (!file.exists()) {
            return null;
        }
        StringBuilder result = new StringBuilder();


        BufferedReader in = null;
        try {
            // 当该文件不存在时再创建一个新的文件
            file.createNewFile();

            FileInputStream fis = new FileInputStream(file);

            // 这样写的话可以指定文本的编码格式 *****
            InputStreamReader isr = new InputStreamReader(fis,encoding);

            in = new BufferedReader(isr);

            // 这里是按照字符流进行的读取

            String str = null;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();

    }

    /**
     * 递归删除目录下的所有文件及子目录下的所有文件
     *
     * @param dir 将要删除的文件目录
     * @return 如果所有文件都删除成功则返回true, 有一个文件删除失败就停止删除并返回false
     */
    public static boolean deleteDir(@NonNull File dir) {
        boolean success = false;
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file1 : files) {//递归删除文件或目录
                    success = deleteDir(file1);
                }
            } else {
                //File.delete()用于删除“某个文件或者空目录”！
                success = dir.delete();
            }
        } else {
            success = false;
        }
        return success;
    }

    /**
     * 把一个文件转化为字节
     * @param filePath 源文件
     * @return byte[]
     */
    public static byte[] loadBytes(@NonNull String filePath) throws Exception {

        if (TextUtils.isEmpty(filePath)){
            throw new Exception("filePath cannot be null");
        }

        File file = new File(filePath);
        byte[] bytes = null;
        InputStream is = null;
        if (file != null) {
            try {
                is = new FileInputStream(file);
                int length = (int) file.length();
                if (length >= Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
                {
                    System.out.println("this file is max ");
                    return null;
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead;
                while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }
                //如果得到的字节长度和file实际的长度不一致就可能出错了
                if (offset < bytes.length) {
                    System.out.println("file length is error");
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * @param data     byte数据
     * @param filePath 文件保存的路径
     * @return <code>true</code> if the data was
     * successfully saved; <code>false</code> if wrote failed
     */
    public static boolean writeBytes(byte[] data, @NonNull String filePath) {
        if (data == null) {
            return false;
        }
        FileOutputStream out = null;
        //资源申请
        File file = new File(filePath);
        try {
            //如果文件存在的话，那么将会覆盖之前的文件
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
