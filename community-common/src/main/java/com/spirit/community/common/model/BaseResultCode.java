package com.spirit.community.common.model;

/**
 * @Title: BaseResponseCode
 * @ProjectName trip-platform
 * @Description: TODO
 * @Author Chaoyi.Guo
 * @Date 2018/10/24 13:38
 */
public enum BaseResultCode {
    /**
     * 操作成功
     */
    SUCCESS("Z000", "操作成功"),
    /**
     * 字段校验失败
     */
    FAILED_VALIDATION("Z001", "字段校验失败"),
    /**
     * 数据检查失败
     */
    FAILED_INSPECTION("Z002", "数据检查失败"),
    /**
     * 逻辑判断失败
     */
    FAILED_LOGIC("Z003", "逻辑判断失败"),
    /**
     * 系统错误,英文编码在各微服务配置
     * A - 数据中心；B - 规则引擎；C - 流程引擎；D - 消息中心；E - API网关；F - 行程微服务；G - 机票微服务；
     * H - 订单微服务；I - 工单微服务；J - 库存微服务；K - 财务微服务；L - 酒店微服务；M - 高铁微服务；N - 快车微服务
     * O - 保险微服务；Z - 系统错误
     */
    FAILED_SYSTEM("004", "系统错误");

    private String code;

    private String msg;


    private BaseResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
