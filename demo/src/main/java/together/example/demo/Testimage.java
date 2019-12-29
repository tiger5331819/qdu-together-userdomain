package together.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Image")
public class Testimage {

    @RequestMapping("/imageUpload")
    public void uploadImage(@RequestBody String json) throws IOException {
        JSONObject obj=JSONObject.parseObject(json);
        byte[] image=Base64.decodeBase64(obj.getString("actpic"));
        File imageFile = new File("pic.jpg");  
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        outStream.write(image);
        outStream.close();

    }
}