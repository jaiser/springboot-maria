package simbot.jaiser.service;

import simbot.jaiser.entity.AutoReplyD;

public interface AutoReplyService {

    /**
     * 根据对象id以及key查询回复信息
     * @param autoReplyD
     * @return
     */
    public AutoReplyD selectOneByReplyIdAndKey(String replyId, String chatKey);

    /**
     * 更新信息
     * @param autoReplyD
     * @return
     */
    public Boolean updateInfo(AutoReplyD autoReplyD);


    /**
     * 保存信息
     * @param autoReplyD
     * @return
     */
    public Boolean saveInfo(AutoReplyD autoReplyD);
}
