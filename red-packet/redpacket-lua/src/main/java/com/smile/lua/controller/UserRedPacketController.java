package com.smile.lua.controller;

import com.smile.lua.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 12780
 */
@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

    @Autowired
    private UserRedPacketService userRedPacketService;

    @RequestMapping("/grapRedPacketByRedis")
    @ResponseBody
    public Map<String, Object> grapRedPacketByRedis(Long redPacketId, Long userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long result = userRedPacketService.grabRedPacket(redPacketId, userId);
        boolean flag = result > 0;
        resultMap.put("result", flag);
        resultMap.put("message", flag ? "秒杀成功" : "秒杀异常");
        return resultMap;
    }
}
