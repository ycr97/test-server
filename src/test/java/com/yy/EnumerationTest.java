package com.yy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yy.quality.common.constant.SeasonEnum;
import com.yy.quality.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author ycr
 * @date 2022/6/8 22:05
 */
public class EnumerationTest {

    @Test
    public void testEnum() {
        String season = "summer";
        SeasonEnum seasonEnum = SeasonEnum.getInstance(season);
        switch (seasonEnum) {
            case SPRING:
                System.out.println("spring");
                break;
            case SUMMER:
                System.out.println("summer");
                break;
            case AUTUMN:
                System.out.println("autumn");
                break;
            case WINTER:
                System.out.println("winter");
                break;
            default:
                break;
        }
    }

    @Test
    public void testJson() throws IOException {
//

        char[] chars = new char[1024];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        String jsonStr = null;
        try (FileReader stream = new FileReader("C:/Users/YCR/Desktop/test.json");) {
            while ((len = stream.read(chars)) != -1) {
                String s = new String(chars, 0, len);
                sb.append(s);
            }
            jsonStr = sb.toString();
            System.out.println(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Menu> list = JSONObject.parseObject(jsonStr, new TypeReference<List<Menu>>() {
        });

        System.out.println(list);
        recursion(list);


    }

    private void recursion(List<Menu> list) {
        list.forEach(this::recursion);
        for (Menu menu : list) {
            System.out.println(menu);
        }
    }

    private void recursion(Menu menu) {
        List<Menu> children = menu.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            return;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < children.size(); i++) {
                Integer id = children.get(i).getId();
                if (i != children.size() - 1) {
                    sb.append(id).append(",");
                } else {
                    sb.append(id);
                }
            }
            menu.setIncludeTo(sb.toString());
        }
        for (Menu child : children) {
            recursion(child);
        }

    }

    private void getAllData(Menu menu, List<Menu> allDataList) {
        List<Menu> children = menu.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            allDataList.add(menu);
        } else {
            for (Menu child : children) {
                getAllData(child, allDataList);
            }
        }
    }

}
