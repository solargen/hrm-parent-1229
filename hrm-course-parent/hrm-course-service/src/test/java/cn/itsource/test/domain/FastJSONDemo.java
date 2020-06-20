package cn.itsource.test.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-20
 */
public class FastJSONDemo {

    /**
     *
     * java对象转json字符串
     * JSONObject.toJSONString(obj)
     *
     * json字符串转java对象
     * JSONObject.parseObject(str,class)
     *
     * json数组转java集合
     * JSONObject.parseArray(str,clsss)
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        //对象转json
        //{"id":1,"name":"admin"}
//        User user = new User(1L,"admin");
//        System.out.println(JSONObject.toJSONString(user));

        //json字符串转java对象
//        String str = "{\"id\":1,\"name\":\"admin\"}";
//        User user = JSONObject.parseObject(str, User.class);
//        System.out.println(user);

        //java的List转json数组
        //[{"id":1,"name":"aaa"},{"id":2,"name":"bbb"}]
//        List<User> users = Arrays.asList(
//                new User(1L,"aaa"),
//                new User(2L,"bbb")
//        );
//        System.out.println(JSONObject.toJSONString(users));


        //json字符串数组转java的List
        String str = "[{\"id\":1,\"name\":\"aaa\"},{\"id\":2,\"name\":\"bbb\"}]";
        List<User> users = JSONObject.parseArray(str, User.class);
        System.out.println(users);
    }

}
