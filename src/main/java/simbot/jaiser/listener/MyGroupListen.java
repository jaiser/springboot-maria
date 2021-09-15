package simbot.jaiser.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.*;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simbot.jaiser.entity.AutoReplyD;
import simbot.jaiser.mapper.AutoReplyMapper;
import simbot.jaiser.service.AutoReplyService;

/**
 * 群消息监听的示例类。
 * 所有需要被管理的类都需要标注 {@link Service} 注解。
 *
 * 由于当前是处于springboot环境下，因此强烈建议类上的注释使用：
 * <ul>
 *     <li>{@link org.springframework.stereotype.Component}</li>
 *     <li>{@link Service}</li>
 * </ul>
 * 等注解来代替simbot的 {@link Beans}。
 *
 * 同样的，依赖注入也请使用 {@link org.springframework.beans.factory.annotation.Autowired} 等Springboot相关的注解。
 *
 * @author ForteScarlet
 */
@Service
public class MyGroupListen {

    @Autowired
    private AutoReplyService autoReplyService;

    /** log */
    private static final Logger logger = LoggerFactory.getLogger(MyGroupListen.class);


    /**
     * 此监听函数代表，收到消息的时候，将消息的各种信息打印出来。
     *
     * 此处使用的是模板注解 {@link OnGroup}, 其代表监听一个群消息。
     *
     * 由于你监听的是一个群消息，因此你可以通过 {@link GroupMsg} 作为参数来接收群消息内容。
     */
    @OnGroup
    public ReplyAble onGroupMsg(GroupMsg groupMsg) {
        // 打印此次消息中的 纯文本消息内容。
        // 纯文本消息中，不会包含任何特殊消息（例如图片、表情等）。
//        System.out.println(groupMsg.getText());

        // 打印此次消息中的 消息内容。
        // 消息内容会包含所有的消息内容，也包括特殊消息。特殊消息使用CAT码进行表示。
        // 需要注意的是，绝大多数情况下，getMsg() 的效率低于甚至远低于 getText()
//        System.out.println(groupMsg.getMsg());

        // 获取此次消息中的 消息主体。
        // messageContent代表消息主体，其中通过可以获得 msg, 以及特殊消息列表。
        // 特殊消息列表为 List<Neko>, 其中，Neko是CAT码的封装类型。

        MessageContent msgContent = groupMsg.getMsgContent();
        // 打印消息主体
//        System.out.println(msgContent);
        // 打印消息主体中的所有图片的链接（如果有的话）
//        List<Neko> imageCats = msgContent.getCats("image");
//        System.out.println("img counts: " + imageCats.size());
//        for (Neko image : imageCats) {
//            System.out.println("Img url: " + image.get("url"));
//        }


        // 获取发消息的人。
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        // 打印发消息者的账号与昵称。
        //本人账号：1007923707
//        System.out.println(accountInfo.getAccountCode());
        //本人账号名：X.
//        System.out.println(accountInfo.getAccountNickname());


        // 获取群信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 打印群号与名称
        //群号：959863837
//        System.out.println(groupInfo.getGroupCode());
        //群名称机器人
//        System.out.println(groupInfo.getGroupName());

        AutoReplyD autoReplyD = null;
        String[] msgList = groupMsg.getText().split(" ");
        if (("学习").equals(msgList[0])) {
            logger.info("学习对象id：《{}》，回复key：《{}》", groupInfo.getGroupCode(), groupMsg.getText());
            autoReplyD = autoReplyService.selectOneByReplyIdAndKey(groupInfo.getGroupCode(), msgList[1]);
            if (autoReplyD != null) {
                return Reply.reply("小Q已经存在回复，请更新或查询！", true);
            }

            AutoReplyD vo = new AutoReplyD();
            vo.setReplyId(groupInfo.getGroupCode());
            vo.setReplyName(groupInfo.getGroupName());

            vo.setInsertUserId(accountInfo.getAccountCode());
            vo.setInsertUserName(accountInfo.getAccountNickname());
//            for (int i = 0; i < msgList.length; i++) {
//                if (i == 1) {
                    vo.setChatKey(msgList[1]);
//                }
//                if (i > 1) {
                    vo.setChatValue(msgList[2]);
//                    if (i != msgList.length - 1) {
//                        vo.setChatValue(vo.getChatValue());
//                    }
//                }
//            }
            if (autoReplyService.saveInfo(vo)) {
                return Reply.reply("小Q学习成功！", true);
            }else {
                return Reply.reply("小Q学习失败，请联系管理员！", true);
            }
        }else if (("更新").equals(msgList[0])) {
            logger.info("更新对象id：《{}》，回复key：《{}》", groupInfo.getGroupCode(), groupMsg.getText());
            autoReplyD = autoReplyService.selectOneByReplyIdAndKey(groupInfo.getGroupCode(), msgList[1]);
            if (autoReplyD == null) {
                return Reply.reply("小Q不存在该回复，请学习！", true);
            }
            AutoReplyD vo = new AutoReplyD();
            vo.setId(autoReplyD.getId());
            vo.setReplyId(groupInfo.getGroupCode());
            vo.setReplyName(groupInfo.getGroupName());

            vo.setInsertTime(null);
            vo.setUpdateUserId(accountInfo.getAccountCode());
            vo.setUpdateUserName(accountInfo.getAccountNickname());
//            for (int i = 0; i < msgList.length; i++) {
//                if (i == 1) {
                    vo.setChatKey(msgList[1]);
//                }
//                if (i > 1) {
                    vo.setChatValue( msgList[2]);
//                    if (i != msgList.length - 1) {
//                        vo.setChatValue(vo.getChatValue());
//                    }
//                }
//            }

            if (autoReplyService.updateInfo(vo)) {
                return Reply.reply("小Q更新成功！", true);
            }else {
                return Reply.reply("小Q更新失败，请联系管理员！", true);
            }
        }else {
            autoReplyD = autoReplyService.selectOneByReplyIdAndKey(groupInfo.getGroupCode(), groupMsg.getText());
            if (autoReplyD != null) {
                if (groupInfo.getGroupCode().equals(autoReplyD.getReplyId())) {
                    // 参数1：回复的消息 参数2：是否at当事人
                    return Reply.reply(autoReplyD.getChatValue(), true);
                }
            }
        }
        return null;


    }








}
