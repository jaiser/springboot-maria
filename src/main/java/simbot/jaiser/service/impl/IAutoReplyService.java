package simbot.jaiser.service.impl;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simbot.jaiser.entity.AutoReplyD;
import simbot.jaiser.listener.MyGroupListen;
import simbot.jaiser.mapper.AutoReplyMapper;
import simbot.jaiser.service.AutoReplyService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class IAutoReplyService implements AutoReplyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AutoReplyMapper autoReplyMapper;

    public AutoReplyD selectOneByReplyIdAndKey(String replyId, String chatKey) {
        if (StringUtils.checkValNull(replyId) || StringUtils.checkValNull(chatKey)) {
            logger.error("回复人员id或回复key为空，无法查询回复信息,查询对象为：《replyId:{},chatKey:{}》", replyId, chatKey);
            return null;
        }
        return autoReplyMapper.selectOneByReplyIdAndKey(replyId, chatKey);
    }

    @Override
    public Boolean updateInfo(AutoReplyD autoReplyD) {
        autoReplyD.setUpdateTime(dateFormat.format(new Date()));
        return autoReplyMapper.updateById(autoReplyD) > 0;
    }

    @Override
    public Boolean saveInfo(AutoReplyD autoReplyD) {
        autoReplyD.setInsertTime(dateFormat.format(new Date()));
        return autoReplyMapper.insert(autoReplyD) > 0;
    }

}
