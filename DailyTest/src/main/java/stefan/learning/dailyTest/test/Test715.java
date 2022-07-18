package stefan.learning.dailyTest.test;

import com.alibaba.fastjson.JSONObject;

public class Test715 {
    public static void main(String[] args) {
        String s = "Date: Fri, 15 Jul 2022 10:06:11 GMT Server: Kestrel Content-Type: application/json; charset=UTF-8 Cache-Control: no-store, no-cache, max-age=0 Pragma: no-cache Keep-Alive: timeout=5, max=100 Connection: Keep-Alive Transfer-Encoding: chunked {\"email\":\"itctest01@hkust-gz.edu.cn\",\"name\":\"itctest01\",\"department\":\"ITD\",\"display_name\":\"Test ITC 01\",\"type\":\"staff\",\"campus\":\"gz\",\"emp_id\":\"GZ0000321\",\"sub\":\"90f6a1e4-6eac-437b-9b6b-261564838ce9\"}\n" +
                "";
        s = s.substring(s.indexOf("{"), s.indexOf("}") + 1);
        JSONObject json = JSONObject.parseObject(s);
        String id = json.getString("sub");
        String name = json.getString("name");
        System.out.println(s);
        System.out.println(id);
        System.out.println(name);
    }
}
