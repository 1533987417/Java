package tools;

import com.google.gson.Gson;
import entity.JsonParseEntity;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class JSONParse {
    private static int index = 1;

    private static int add() {
        return index++;
    }

    public static List<JsonParseEntity> analysisJson(Object objJson, List<JsonParseEntity> list, JsonParseEntity e) throws org.codehaus.jettison.json.JSONException {
        // 如果obj为json数组
        if (objJson instanceof JSONArray) {

            //将接收到的对象转换为JSONArray数组
            JSONArray objArray = (JSONArray) objJson;
            System.out.println("run to here");
            //对JSONArray数组进行循环遍历
            for (int i = 0; i < objArray.length(); i++) {
                e.setSort(i);
                analysisJson(objArray.get(i), list, e);
            }
            // 如果为json对象
        } else if (objJson instanceof JSONObject) {
            //将Object对象转换为JSONObject对象
            JSONObject jsonObject = (JSONObject) objJson;
            //迭代多有的Key值
            Iterator it = jsonObject.keys();
            //遍历每个Key值
            while (it.hasNext()) {
                JsonParseEntity entity = new JsonParseEntity();
                //将key值转换为字符串
                String key = it.next().toString();
                //根据key获取对象
                Object object = jsonObject.get(key);
                entity.setParentId(e.getId());
                entity.setField(key);
                // 如果得到的是数组
                if (object instanceof JSONArray) {
                    //将Object对象转换为JSONObject对象

                    //调用回调方法
                    entity.setDataType(2);
                    entity.setSort(e.getSort());
                    int result = JSONParse.add();// trainActivityCongfigDataDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJson((JSONArray) object, list, entity);
                }
                // 如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    //调用回调方法
                    entity.setDataType(1);
                    entity.setSort(e.getSort());
                    int result = JSONParse.add();//trainActivityCongfigDataDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJson((JSONObject) object, list, entity);
                } else {
                    //entity.setParentId(e.getId());
                    entity.setDataType(0);
                    entity.setSort(e.getSort());
                    entity.setValue(object.toString());
                    int result = JSONParse.add();
                    entity.setId(result);
                    list.add(entity);
                }
            }
        }
        return list;
    }

    public static HashMap<String, Object> parseJson(List<JsonParseEntity> list, HashMap<String, Object> map, int parentId, int sort) {

        list = list.stream().sorted((x1, x2) -> x2.getParentId().compareTo(x1.getParentId())).collect(Collectors.toList());
        for (JsonParseEntity e : list
                ) {
            if (e.getDataType() == 2 && e.getParentId() == parentId) {
                List<HashMap> entitylist = new ArrayList<>();
                List<JsonParseEntity> templ = list.stream().filter(x -> x.getParentId() == e.getId()).distinct().collect(Collectors.toList());//去重获取该parentId下的sort
                HashMap<String, HashMap<String, Object>> arrayItems = new HashMap<>();
                for (JsonParseEntity l : templ) {
                    HashMap<String, Object> temp = null;
                    if (arrayItems.containsKey(l.getSort().toString())) {
                        temp = arrayItems.get(l.getSort().toString());
                    } else {
                        temp = new HashMap<String, Object>();
                        arrayItems.put(l.getSort().toString(), temp);
                    }
                    parseJson(list, temp, e.getId(), l.getSort());
                }
                if (arrayItems != null && arrayItems.size() > 0) {
                    arrayItems.values().forEach(p -> {
                        entitylist.add(p);
                    });
                }
                map.put(e.getField(), entitylist);
            } else if (e.getDataType() == 1 && e.getParentId() == parentId) {
                HashMap<String, Object> temp = new HashMap<>();
                parseJson(list, temp, e.getId(), 0);
                map.put(e.getField(), temp);
            } else {
                if (e.getParentId() == parentId && e.getSort() == sort) {
                    map.put(e.getField(), e.getValue());
                }
            }

        }
        return map;
    }

    public static void main(String[] args) {
        try {
            String str = "{\"code\":2002,\"logId\":\"e722a66e-1ba1-42d8-b7b5-4a7b911fbb7e\",\"message\":\"抢票单已完成\",\"result\":{\"couponFlag\":false,\"currentLevel\":1,\"endStation\":\"平凉南\",\"helperList\":[{\"name\":\"sjj\",\"age\":20,\"height\":180},{\"name\":\"zxh\",\"age\":21,\"height\":170}],\"helperPackages\":0,\"initiatorIcon\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erjgMHnYtRbPREiaXIRHttNS7R6dEMjmjSRbjbGZNF0ZfpAfOIyjg49p4SAVpgf3eCelib70nnP8o5g/132\",\"initiatorNickName\":\"moli\",\"levelPackagesData\":[{\"level\":1,\"name\":\"低速\",\"packages\":0},{\"level\":2,\"name\":\"快速\",\"packages\":10},{\"level\":3,\"name\":\"极速\",\"packages\":20},{\"level\":4,\"name\":\"闪电\",\"packages\":30},{\"level\":5,\"name\":\"星速\",\"packages\":40},{\"level\":6,\"name\":\"光速\",\"packages\":50},{\"level\":7,\"name\":\"VIP\",\"packages\":60}],\"needPackages\":10,\"nextLevel\":2,\"orderStatus\":9,\"startStation\":\"平凉\",\"totalPackages\":0}}";
            JSONObject obj = new JSONObject(str);
            JsonParseEntity entity = new JsonParseEntity();
            entity.setSort(0);
            entity.setId(0);
            entity.setName("");
            List<JsonParseEntity> result = JSONParse.analysisJson(obj, new ArrayList<JsonParseEntity>(), entity);
            Gson gson = new Gson();
            System.out.println(gson.toJson(result));
            HashMap<String, Object> d = JSONParse.parseJson(result, new HashMap<>(), 0, 0);
            System.out.println(gson.toJson(d));
        } catch (Exception ex) {

        }
    }
}
