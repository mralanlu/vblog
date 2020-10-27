package com.northbund.vblog.utils;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author lukai
 * @description 上传帮助类
 */
@Component
public class UploadHelper {


    @Value("${uploadFileUrl}")
    private String uploadFileUrl;

    //static String  path = "C:\\home\\fileupload\\";

    /**
     * @descrption 根据HttpServletRequest对象获取MultipartFile集合
     * @author lukai
     * @param file
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return MultipartFile集合
     */
    public MultipartFile getFileSet(MultipartFile file, long maxLength, List allowExtName) throws CommonException{
        if (!validateFile(file, maxLength, allowExtName)) {
            throw new CommonException(ResultCodeEnum.UPLOAD_ERROR);
        }
        return file;
    }

    /**
     * @descrption 保存文件
     * @author lukai
     * @param file
     *            MultipartFile对象
     *            保存路径，如“D:\\File\\”
     * @return 保存的全路径 如“D:\\File\\2345678.txt”
     * @throws Exception
     *             文件保存失败
     */
    public  String uploadFile(MultipartFile file)
            throws Exception {
        String  path = "/www/wwwroot/yxrz-api.jtculture.com.cn/";
        String dateFolder=new SimpleDateFormat("yyyyMMdd").format(new Date());
        path = path+dateFolder;
        String pathAu = path;
        String url = uploadFileUrl+dateFolder;
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        String lastFileName = UUID.randomUUID().toString()+extName;
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        if (!url.endsWith(File.separator)) {
            url = url + File.separator;
        }
        File temp = new File(path);
        if (!temp.isDirectory()) {
            temp.mkdir();
        }
        // 图片存储的全路径
        String fileFullPath = path + lastFileName;
        url = url+lastFileName;
        File file1 =  new File(fileFullPath);
        file1.setReadable(true);
        FileCopyUtils.copy(file.getBytes(), file1);
        Runtime.getRuntime().exec("chmod -R 777 "+pathAu);
        return url;
    }



    /**
     * @descrption 验证文件格式，这里主要验证后缀名
     * @author lukai
     * @param file
     *            MultipartFile对象
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return 文件格式是否合法
     */
    private  boolean validateFile(MultipartFile file, long maxLength,
                                  List allowExtName) throws CommonException {
        if (file.getSize() < 0 || file.getSize() > maxLength){
           throw new CommonException(ResultCodeEnum.UPLOAD_OUTBOUND);
        }
        String filename = file.getOriginalFilename();

        // 处理不选择文件点击上传时，也会有MultipartFile对象，在此进行过滤
        if (filename == "") {
            return false;
        }
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        if (allowExtName == null || allowExtName.size() == 0
                || allowExtName.contains(extName)) {
            return true;
        } else {
            throw new CommonException(ResultCodeEnum.UPLOAD_ILLEGAL);
        }
    }

}