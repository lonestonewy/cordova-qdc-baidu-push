package com.qdc.plugins.baidu;

import java.util.List;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushMessageReceiver;
import __PACKAGE_NAME__;

/**
 * 百度云推送Service
 *
 * @author NCIT
 *
 */
public class BaiduPushReceiver extends PushMessageReceiver {

	/** LOG TAG */
    private static final String LOG_TAG = BaiduPushReceiver.class.getSimpleName();

    /** 回调类型 */
    private enum CB_TYPE {
    	onbind,
    	onunbind,
    	onsettags,
    	ondeltags,
    	onlisttags,
    	onmessage,
    	onnotificationclicked,
    	onnotificationarrived
    };

    /**
     * 百度云推送绑定回调
     */
    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {
        Log.d(LOG_TAG, "BaiduPushReceiver#onBind");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = new JSONObject();
            setStringData(data, "appId", appId);
            setStringData(data, "userId", userId);
            setStringData(data, "channelId", channelId);

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onbind);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    /**
     * 百度云推送解除绑定回调
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
    	Log.d(LOG_TAG, "BaiduPushReceiver#onUnbind");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = new JSONObject();
            data.put("errorCode", errorCode);
            setStringData(data, "requestId", requestId);

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onunbind);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    /**
     * 百度云推送TAG绑定回调
     */
    @Override
    public void onSetTags(Context context, int errorCode, List<String> sucessTags, List<String> failTags, String requestId) {
    	Log.d(LOG_TAG, "BaiduPushReceiver#onSetTags");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = new JSONObject();
            data.put("errorCode", errorCode);
            setStringData(data, "requestId", requestId);
            if (sucessTags != null && sucessTags.size() > 0) {
            	JSONArray sucessTagArr = new JSONArray();
	            for (String successTag : sucessTags) {
	            	sucessTagArr.put(successTag);
	            }
	            data.put("sucessTags", sucessTagArr);
            }
            if (failTags != null && failTags.size() > 0) {
            	JSONArray failTagArr = new JSONArray();
	            for (String failTag : failTags) {
	            	failTagArr.put(failTag);
	            }
	            data.put("failTags", failTagArr);
            }

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onsettags);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    /**
     * 百度云推送解除TAG绑定回调
     */
    @Override
    public void onDelTags(Context context, int errorCode, List<String> sucessTags, List<String> failTags, String requestId) {
    	Log.d(LOG_TAG, "BaiduPushReceiver#onDelTags");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = new JSONObject();
            data.put("errorCode", errorCode);
            setStringData(data, "requestId", requestId);
            if (sucessTags != null && sucessTags.size() > 0) {
            	JSONArray sucessTagArr = new JSONArray();
	            for (String successTag : sucessTags) {
	            	sucessTagArr.put(successTag);
	            }
	            data.put("sucessTags", sucessTagArr);
            }
            if (failTags != null && failTags.size() > 0) {
            	JSONArray failTagArr = new JSONArray();
	            for (String failTag : failTags) {
	            	failTagArr.put(failTag);
	            }
	            data.put("failTags", failTagArr);
            }

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.ondeltags);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

    }

    /**
     * 百度云推送LISTTAG绑定回调
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {
    	Log.d(LOG_TAG, "BaiduPushReceiver#onListTags");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = new JSONObject();
            data.put("errorCode", errorCode);
            setStringData(data, "requestId", requestId);
            if (tags != null && tags.size() > 0) {
            	JSONArray tagArr = new JSONArray();
	            for (String tag : tags) {
	            	tagArr.put(tag);
	            }
	            data.put("tags", tagArr);
            }

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onlisttags);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

    }

    /**
     * 百度云推送透传消息回调
     */
    @Override
    public void onMessage(Context context, String message, String customContentString) {
        Log.d(LOG_TAG, "BaiduPushReceiver#onMessage");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = null;
            if (customContentString != null && !"".equals(customContentString)) {
                data = new JSONObject(customContentString);
            } else {
            	data = new JSONObject();
            }
            setStringData(data, "message", message);

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onmessage);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

    }

    /**
     * 百度云推送通知点击回调
     */
    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        Log.d(LOG_TAG, "BaiduPushReceiver#onNotificationClicked");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = null;
            if (customContentString != null && !"".equals(customContentString)) {
                data = new JSONObject(customContentString);
            } else {
            	data = new JSONObject();
            }
            setStringData(data, "title", title);
            setStringData(data, "description", description);

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onnotificationclicked);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(context, MainActivity.class);
        intent.putExtra(PushConstants.EXTRA_NOTIFICATION_TITLE, title);
        intent.putExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT, customContentString);
        context.startActivity(intent);
    }

    /**
     * 百度云推送通知接收回调
     */
    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        Log.d(LOG_TAG, "BaiduPushReceiver#onNotificationArrived");

        try {
            JSONObject jsonObject = new JSONObject();

            JSONObject data = null;
            if (customContentString != null && !"".equals(customContentString)) {
                data = new JSONObject(customContentString);
            } else {
            	data = new JSONObject();
            }
            setStringData(data, "title", title);
            setStringData(data, "description", description);

            jsonObject.put("data", data);
            jsonObject.put("type", CB_TYPE.onnotificationarrived);

            sendPushData(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    /**
     * 接收推送内容并返回给前端JS
     *
     * @param jsonObject JSON对象
     */
    private void sendPushData(JSONObject jsonObject) {
        Log.d(LOG_TAG, "BaiduPushReceiver#sendPushData: " + (jsonObject != null ? jsonObject.toString() : "null"));

        if (BaiduPush.pushCallbackContext != null) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, jsonObject);
            result.setKeepCallback(true);
            BaiduPush.pushCallbackContext.sendPluginResult(result);
        }
    }

    /**
     * 设定字符串类型JSON对象，如值为空时不设定
     *
     * @param jsonObject JSON对象
     * @param name 关键字
     * @param value 值
     * @throws JSONException JSON异常
     */
    private void setStringData(JSONObject jsonObject, String name, String value) throws JSONException {
    	if (value != null && !"".equals(value)) {
    		jsonObject.put(name, value);
    	}
    }
}
