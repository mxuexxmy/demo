package edu.gues.demo.debug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * debug 查找
 * @author <a href="mailto:mxuexxmy@gmail.com">mxuexxmy</a>
 */
public class DebugOne {

    public static void main(String[] args) {
        String str = "[{\"wkNo\":\"5209147614690967558\",\"taskStateCode\":\"2\",\"taskStateName\":\"无法检验\",\"editTaskTime\":\"2022-10-21 16:50:19\",\"prodWkNo\":\"GY-221021-02-GY(YGZ)-001-JJB-BZ-12\",\"isCheck\":\"0\",\"uncheckRemark\":\"测试周保\",\"uncheckReasonCode\":\"1001\",\"uncheckReasonName\":\"周保\",\"solveMethodCode\":\"\",\"solveMethodName\":\"\"}]";

        JSONArray jsonArray = JSON.parseArray(str);

        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if ("1".equals(jsonObject.getString("taskStateCode"))) {
                    System.out.println("taskStateCode:" + jsonObject.getString("taskStateCode"));
                    continue;
                }
                if ("2".equals(jsonObject.getString("taskStateCode"))){
                    System.out.println("taskStateCode:" + jsonObject.getString("taskStateCode"));
                    System.out.println("uncheckReasonName:" + jsonObject.getString("uncheckReasonName"));
                    continue;
                }
            }
        }

    }

}
