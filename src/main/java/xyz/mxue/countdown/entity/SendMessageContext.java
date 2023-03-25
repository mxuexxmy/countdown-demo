package xyz.mxue.countdown.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mxuexxmy
 * @date 2023/3/26 0:47
 * @version 1.0
 */
public class SendMessageContext<T> {

    /**
     * 消息标记
     */
    @NotBlank(message = "消息标记不能为空")
    private String mark;

    /**
     * 消息内容
     */
    @NotNull(message = "消息内容不能为空")
    private T content;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public SendMessageContext() {
    }
    public SendMessageContext(String mark) {
        this.mark = mark;
    }

    public SendMessageContext(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SendMessageContext{" +
                "mark='" + mark + '\'' +
                ", content=" + content +
                '}';
    }
}
