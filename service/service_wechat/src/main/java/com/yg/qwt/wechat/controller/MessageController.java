package com.yg.qwt.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.yg.qwt.wechat.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jcajce.provider.digest.SHA1;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    private static final String token = "xxqwt";

    @Resource
    private MessageService messageService;

    /**
     * 服务器有效性验证
     * @param request
     * @return
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature: {} nonce: {} echostr: {} timestamp: {}", signature, nonce, echostr, timestamp);
        if (this.checkSignature(signature, timestamp, nonce)) {
            log.info("token ok");
            return echostr;
        }
        return echostr;
    }

    /**
     * 接收微信服务器发送来的消息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {
        Map<String, String> param = this.parseXml(request);
        return messageService.receiveMessage(param);
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = Arrays.toString(DigestUtils.sha1(buffer.toString()));
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }

}
