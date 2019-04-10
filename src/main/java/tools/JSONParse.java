package tools;

import com.google.gson.Gson;
import entity.JsonParseEntity;
import entity.TrainActivityUnifiedConfiguration;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class JSONParse {
    public static int index = 1;
    public List<TrainActivityUnifiedConfiguration> finallist = null;
    //@Autowired
 //   TrainActivityUnifiedConfigurationDao trainActivityUnifiedConfigurationDao;

    public static int add() {
        return index++;
    }



    /**
     * 解析完整json
     *
     * @param objJson
     * @param list
     * @param e
     * @return
     * @throws JSONException
     */
    public List<TrainActivityUnifiedConfiguration> analysisJson(Object objJson, List<TrainActivityUnifiedConfiguration> list, TrainActivityUnifiedConfiguration e) throws JSONException {
        // 如果obj为json数组
        if (objJson instanceof JSONArray) {

            //将接收到的对象转换为JSONArray数组
            JSONArray objArray = (JSONArray) objJson;
            System.out.println("run to here");
            //对JSONArray数组进行循环遍历

            for (int i = 0; i < objArray.length(); i++) {
                TrainActivityUnifiedConfiguration temp = (TrainActivityUnifiedConfiguration) e.clone();
                temp.setSort(i);
                analysisJson(objArray.get(i), list, temp);
            }
            // 如果为json对象
        } else if (objJson instanceof JSONObject) {
            //将Object对象转换为JSONObject对象
            JSONObject jsonObject = (JSONObject) objJson;
            //迭代多有的Key值
            Iterator it = jsonObject.keys();
            //遍历每个Key值
            while (it.hasNext()) {
                TrainActivityUnifiedConfiguration entity = new TrainActivityUnifiedConfiguration(e.getVid());
                //将key值转换为字符串
                String key = it.next().toString();
                //根据key获取对象
                Object object = jsonObject.get(key);
                entity.setParentField(e.getField());
                entity.setParentId(e.getId());
                entity.setField(key);
                // 如果得到的是数组
                if (object instanceof JSONArray) {
                    //将Object对象转换为JSONObject对象

                    //调用回调方法
                    entity.setFieldType(2);
                    entity.setSort(0);
                    int result = JSONParse.add();// trainActivityCongfigDataDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJson((JSONArray) object, list, entity);
                }
                // 如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    //调用回调方法
                    entity.setFieldType(1);
                    entity.setSort(e.getSort());
                    //TODO:
                    int result = JSONParse.add();//trainActivityCongfigDataDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJson((JSONObject) object, list, entity);
                } else {
                    //entity.setParentId(e.getId());
                    entity.setFieldType(0);
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

    /**
     * Json获取结果
     *
     * @param objJson
     * @param list
     * @param e
     * @return
     * @throws JSONException
     */
    public List<TrainActivityUnifiedConfiguration> analysisJsonOnce(Object objJson, List<TrainActivityUnifiedConfiguration> list, TrainActivityUnifiedConfiguration e) throws JSONException {
        // 如果obj为json数组
        if (objJson instanceof JSONArray) {

            //将接收到的对象转换为JSONArray数组
            JSONArray objArray = (JSONArray) objJson;
            System.out.println("run to here");
            //对JSONArray数组进行循环遍历
           /* for (int i = 0; i < objArray.length(); i++) {
                e.setSort(i);
                analysisJson(objArray.get(i), list, e);
            }*/
            e.setSort(0);
            analysisJsonOnce(objArray.get(0), list, e);
            // 如果为json对象
        } else if (objJson instanceof JSONObject) {
            //将Object对象转换为JSONObject对象
            JSONObject jsonObject = (JSONObject) objJson;
            //迭代多有的Key值
            Iterator it = jsonObject.keys();
            //遍历每个Key值
            while (it.hasNext()) {
                TrainActivityUnifiedConfiguration entity = new TrainActivityUnifiedConfiguration(e.getVid());
                //将key值转换为字符串
                String key = it.next().toString();
                //根据key获取对象
                Object object = jsonObject.get(key);
                entity.setParentField(e.getField());
                entity.setParentId(e.getId());
                entity.setField(key);
                // 如果得到的是数组
                if (object instanceof JSONArray) {
                    //将Object对象转换为JSONObject对象

                    //调用回调方法
                    entity.setFieldType(2);
                    entity.setSort(e.getSort());
                    int result = JSONParse.add();
                    //trainActivityUnifiedConfigurationDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJsonOnce((JSONArray) object, list, entity);
                }
                // 如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    //调用回调方法
                    entity.setFieldType(1);
                    entity.setSort(e.getSort());
                    int result = JSONParse.add();
                    //trainActivityUnifiedConfigurationDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                    analysisJsonOnce((JSONObject) object, list, entity);
                } else {
                    entity.setFieldType(0);
                    entity.setSort(e.getSort());
                    entity.setValue(object.toString());
                    int result = JSONParse.add();
                   // trainActivityUnifiedConfigurationDao.addConfig(entity);
                    entity.setId(result);
                    list.add(entity);
                }
            }
        }
        return list;
    }

    public HashMap<String, Object> parseJson(List<TrainActivityUnifiedConfiguration> list, HashMap<String, Object> map, int parentId, int sort) {

        // list = list.stream().sorted((x1, x2) -> x2.getId().compareTo(x1.getId())).collect(Collectors.toList());
        for (TrainActivityUnifiedConfiguration e : list
                ) {
            if (e.getFieldType() == 2 && e.getParentId() == parentId) {
                List<HashMap> entitylist = new ArrayList<>();
                List<TrainActivityUnifiedConfiguration> templ = list.stream().filter(x -> x.getParentId().equals(e.getId())).distinct().collect(Collectors.toList());//去重获取该parentId下的sort
                HashMap<String, HashMap<String, Object>> arrayItems = new HashMap<>();
                for (TrainActivityUnifiedConfiguration l : templ) {
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
            } else if (e.getFieldType() == 1 && e.getParentId() == parentId) {
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

    /**
     * 结果数据组合
     *
     * @return
     */
    public List<TrainActivityUnifiedConfiguration> combineData(String data, List<TrainActivityUnifiedConfiguration> unifiedConfigurationList) throws JSONException {
        TrainActivityUnifiedConfiguration entity = new TrainActivityUnifiedConfiguration(unifiedConfigurationList.get(0).getVid());
        List<TrainActivityUnifiedConfiguration> result = this.analysisJson(new JSONObject(data), new ArrayList<TrainActivityUnifiedConfiguration>(), entity);

        for (TrainActivityUnifiedConfiguration e : unifiedConfigurationList
                ) {
            //list2.stream().filter(x->x.getField().equals(e.getField())&&x.getParentFiled().equals(e.getField())).forEach(y->y.setName(e.getName()));
            for (TrainActivityUnifiedConfiguration e2 : result
                    ) {
                if (e.getField().equals(e2.getField()) && e.getParentField().equals(e2.getParentField())) {
                    e2.setName(e.getName());
                }
            }
        }
        return result;
    }


}
