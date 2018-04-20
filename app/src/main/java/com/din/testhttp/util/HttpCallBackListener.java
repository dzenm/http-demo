package com.din.testhttp.util;

/**
 * Created by dinzhenyan on 2018/3/31.
 */

public interface HttpCallBackListener {

    /**
     *  服务器成功后调用
     * @param response
     * 返回的数据
     */
    void onFinish(String response);


    /**
     *  网络操作错误时调用
     * @param e
     * 返回错误信息
     */
    void onError(Exception e);
}
