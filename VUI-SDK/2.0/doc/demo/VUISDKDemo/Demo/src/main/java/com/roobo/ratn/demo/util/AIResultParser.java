package com.roobo.ratn.demo.util;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyijun on 2018/9/12.
 */

public class AIResultParser {
    private static final String TAG = AIResultParser.class.getSimpleName();

    public static String parserContextFromAIResultJSON(String resultJson) {
        if (resultJson != null) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                JSONObject semanticJsonObject = aiJsonObject.optJSONObject("semantic");
                JSONObject contextJsonObject = semanticJsonObject.optJSONObject("outputContext");
                JSONArray array = new JSONArray();
                array.put(contextJsonObject);
                return array.toString();
            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return "";
    }

    public static String parserHintFromAIResultJSON(String resultJson) {
        String hint = "";
        if (resultJson != null) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject resultJsonObject = aiJsonObject.optJSONObject("result");
                    if (resultJsonObject != null) {
                        hint = resultJsonObject.optString("hint");
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return hint;
    }

    public static String parserMP3UrlFromAIResultJSON(String resultJson) {
        String audioUrl = "";
        if (resultJson != null) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject resultJsonObject = aiJsonObject.optJSONObject("result");
                    if (resultJsonObject != null) {
                        JSONObject dataJsonObject = resultJsonObject.optJSONObject("data");
                        if (dataJsonObject != null) {
                            audioUrl = dataJsonObject.optString("audio");
                        }
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return audioUrl;
    }

    public static boolean isExitPlayer(String resultJson) {
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject semanticJsonObject = aiJsonObject.optJSONObject("semantic");
                    if (semanticJsonObject != null) {
                        if ("Media".equals(semanticJsonObject.optString("service")) &&
                                "Exit".equals(semanticJsonObject.optString("action"))) {
                            return true;
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return false;
    }

    public static boolean isStartPlayer(String resultJson) {
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject semanticJsonObject = aiJsonObject.optJSONObject("semantic");
                    if (semanticJsonObject != null) {
                        if ("Media".equals(semanticJsonObject.optString("service")) &&
                                "Play".equals(semanticJsonObject.optString("action"))) {
                            return true;
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return false;
    }
}
