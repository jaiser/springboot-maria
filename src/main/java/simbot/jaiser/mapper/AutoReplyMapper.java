package simbot.jaiser.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import simbot.jaiser.entity.*;

@Repository
public interface AutoReplyMapper extends BaseMapper<AutoReplyD> {

    @Select("SELECT * FROM `auto_reply_d` WHERE REPLY_ID = #{replyId} AND CHAT_KEY = #{chatKey}")
    AutoReplyD selectOneByReplyIdAndKey(@Param("replyId")String replyId, @Param("chatKey")String chatKey);

}
