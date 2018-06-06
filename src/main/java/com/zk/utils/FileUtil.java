
package com.zk.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    private static Integer countRead = 0;

    private static Integer countWrite = 0;

    private static Integer countDelete = 0;

    private static Integer countCopy = 0;

    private FileUtil() {}

    /**
     * 创建多级目录文件
     * @param path 文件路径
     */
    private static void createFile(String path) {
        if (null == path || "".equals(path)) {
            logger.info("function() createFile: the filePath for send is " + path);
            return;
        }
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error("function() createFile: create file for send failed! ", e);
        }
    }

    public static void writeFile(String filePath, String content){
        countWrite++;
        logger.info("function() writeFile in : countWrite  is " + countWrite);
        if(content==null){
            logger.info("function() writeFile: the content for writeFile is null! ");
            return;
        }
        File file = new File(filePath);
        FileOutputStream fos = null;
        FileChannel fileChannel = null;
        try {
            fos = new FileOutputStream(file);
            fileChannel = fos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byte[] bytes=content.getBytes("utf-8");
            byteBuffer.put(bytes);
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
        } catch (IOException e) {
            logger.error("function() writeFile: write file failed! ", e);
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != fileChannel) {
                    fileChannel.close();
                }
            } catch (IOException e) {
                logger.error("function() writeFile: fos or fileChannel close failed! ", e);
            }
        }
    }

    public static String readFile(String filePath) {
        logger.info("function() readFile: file path is " + filePath);
        countRead++;
        logger.info("function() readFile in : countRead  is " + countRead);
        FileInputStream fis = null;
        FileChannel channel = null;
        StringBuilder content = new StringBuilder();
        try {
            fis = new FileInputStream(new File(filePath));
            channel = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (channel.read(byteBuffer) != -1) {
                /*
                 * 注意，读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                 */
                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
                content.append(new String(bytes,"utf-8"));
            }
            logger.info("function() readFile: read file content is " + content);
        } catch (IOException e) {
            logger.error("function() readFile: read file failed! ", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                logger.error("function() readFile: fis or fileChannel close failed! ", e);
            }
        }
        return content.toString();
    }

    public  static  void deleteFile(String filePath){
        logger.info("function() deleteFile in, the filePath for delete is "+ filePath);
        countDelete++;
        logger.info("function() deleteFile in : countDelete  is " + countDelete);
        File file = new File(filePath);
        file.delete();
    }

    public static List<String> getFileList(String folderPath, String regex) {
        logger.info("function() getRecvFileList in");
        List<String> list = new ArrayList<String>();
        File folder = new File(folderPath);
        String[] filePathArr = folder.list();
        if (null == filePathArr || filePathArr.length == 0) {
            logger.info("function() getRecvFileList: the filePathArr is null");
            return list;
        }
        for(String fileName : filePathArr){
            if(fileName.matches(regex)) {
                list.add(folderPath+fileName);
            }
        }
        return list;
    }

    public static void copyFile(String srcFilePath, String destFilePath) {
        logger.info("function() copyFile: the srcFilePath is "+ srcFilePath + ", the destFilePath is "+ destFilePath);
        countCopy++;
        logger.info("function() copyFile in : countCopy  is " + countCopy);
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(srcFile).getChannel();
            outputChannel = new FileOutputStream(destFile).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            logger.error("function() copyFile: copy file failed! ", e);
        } finally {
            try {
                if (null != inputChannel) {
                        inputChannel.close();
                }
                if (null != outputChannel) {
                    outputChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
