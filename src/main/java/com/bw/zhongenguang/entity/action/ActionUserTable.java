package com.bw.zhongenguang.entity.action;

import com.bw.zhongenguang.entity.bean.UserTable;
import com.bw.zhongenguang.entity.dao.DaoMapper;
import com.bw.zhongenguang.entity.util.FileUpload2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by zhongenguang on 2017/7/28.
 */

@Controller
public class ActionUserTable {

    @Autowired
    private JavaMailSender mailSender;

    private File hu;

    private String huFileName;

    @Autowired
    public DaoMapper dao;

    @RequestMapping("emiall")
    public String sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13260283502@163.com");
        message.setTo("jiaozhiguang@126.com");
        message.setSubject("主题：测试");
        message.setText("钟恩广");
        mailSender.send(message);
        return "success";
    }

    /*
    下载
     */
    @RequestMapping("xiazai.do")
    public String dao(String id){

        String [] s= id.split(",");
        try {
            PrintStream ps = new PrintStream("F://user.txt");
            for (String string : s) {

                UserTable tableById = dao.findUserTableById(Integer.parseInt(string));
            /*    Yong yong = servicee.select2(Integer.parseInt(string));*/
                System.out.println(tableById+"+---------");
                ps.println(tableById.toString());
            }
            ps.flush();
            ps.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:select1.do";
    }








    /*
       删除
        */
    @RequestMapping("delete1.do")
    public String delete1(UserTable userTable ){
        dao.delete( userTable);
        return "redirect:select.do";
    }
    /*
    登录
     */
    @RequestMapping("ringup.do")
    public String ringup(String name,String password,Model model){
        String deng=null;

        List<UserTable> use = dao.findUserTableByNameAndPassword(name, password);
if (use.size()>0){
    deng="redirect:select.do";

}

        return deng;
    }
    @RequestMapping("select.do")
    public String selelct(String name,String password,Model model){
        List<UserTable> userTable = dao.findAll();

        model.addAttribute("userTable",userTable);
        return "look.html";
    }

    /**
     *添加
     */
    @RequestMapping("adD.do")
    public String adD(UserTable userTable, Model model) throws Exception {

        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(userTable.getPassword().getBytes("utf-8")));
        //加密后的密码放入数据库
            userTable.setPassword(newstr);

        /*上传图片  调用工具类FileUpload2*/
        System.out.println(hu);
        String upload = FileUpload2.upload(huFileName,hu);
        userTable.setTu(upload);


        UserTable save = dao.save(userTable);
        return "redirect:select.do";
    }
    @RequestMapping("Ye")
     public String ye(){     return "add";
    }
    @RequestMapping("ringup")
    public String yee(){     return "ringup.html";
    }


/*----------------------------------------------------------------------*/



    public File getHu() {
        return hu;
    }

    public void setHu(File hu) {
        this.hu = hu;
    }

    public String getHuFileName() {
        return huFileName;
    }

    public void setHuFileName(String huFileName) {
        this.huFileName = huFileName;
    }

    public DaoMapper getDao() {
        return dao;
    }

    public void setDao(DaoMapper dao) {
        this.dao = dao;
    }

    public ActionUserTable() {
    }

    public ActionUserTable( File hu, String huFileName) {

        this.hu = hu;
        this.huFileName = huFileName;
    }
}
