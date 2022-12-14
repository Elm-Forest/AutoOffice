package com.ctgu.oaweb.controller.message;

import com.ctgu.oacommon.entity.Message;
import com.ctgu.oacommon.utils.UserThreadHolder;
import com.ctgu.oacommon.vo.MessageConditionVO;
import com.ctgu.oacommon.vo.MessageVO;
import com.ctgu.oacommon.vo.PageResult;
import com.ctgu.oacommon.vo.Result;
import com.ctgu.oamessage.service.core.MessageServiceCore;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 李嘉超
 * @version 1.0
 * @date 2022/8/17 8:43
 */
@RestController
@RequestMapping(value = "/message")
@Api(tags = "消息管理")
public class MessageController {

    @Autowired
    private MessageServiceCore messageServiceCore;

    //用户查看所有自己编写的但是没有发送的消息
    @GetMapping("/anrmessagebs")
    public Result<PageResult<Message>> getAllNotReleaseMessageBySendUserId(Integer current, Integer size) {
        Long id = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllNotReleaseMessageBySendUserId(id, current, size);
    }

    //用户查看所有自己编写的已发送的消息
    @GetMapping("/ahrmessagebs")
    public Result<PageResult<Message>> getAllHaveReleasedMessageBySendUserId(Integer current, Integer size) {
        Long id = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllHaveReleasedMessageBySendUserId(id, current, size);
    }

    //用户查看所有自己已收到并且可查看的消息，因为要考虑到过期消息
    @GetMapping("/anoahrmessageba")
    public Result<PageResult<Message>> getAllNotOverdueAndHaveReleasedMessageByAcceptUserId(Integer current, Integer size) {
        Long id = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllNotOverdueAndHaveReleasedMessageByAcceptUserId(id, current, size);
    }

    //用户保存消息，仅仅是保存，不发送，就是把消息的状态写成0，未发送
    //这个时候虽然也会设置过期时间但是并不会生效，过期时间只会对已发送的消息生效
    @GetMapping("/inrmessage")
    public Result<Boolean> insertMessageNotRelease(MessageVO messageVO) {
        return messageServiceCore.insertMessageNotRelease(messageVO);
    }

    //用户发送消息，这个是发送消息，设置发送状态为1，已发送
    //这个时候设置过期时间会生效，会在下一次请求（@GetMapping("/anoahrmessageba")）的时候来判断消息是否过期
    //在来决定是否可以查看消息
    @GetMapping("/ihrmessage")
    public Result<Boolean> insertMessageHaveRelease(MessageVO messageVO) {
        return messageServiceCore.insertMessageHaveRelease(messageVO);
    }

    //删除对应的消息
    @DeleteMapping("/dmessage")
    public Result<Boolean> deleteMessageById(Long id) {
        return messageServiceCore.deleteMessageById(id);
    }

    //修改对应的消息
    @RequestMapping("/umessage")
    public Result<Boolean> updateMessageNotRelease(MessageVO messageVO) {
        return messageServiceCore.updateMessageNotRelease(messageVO);
    }

    //用户根据条件来查询所有自己已发送的消息
    @GetMapping("/ahrmessagebc")
    public Result<PageResult<Message>> getAllHaveReleasedMessageByCondition(MessageConditionVO messageConditionVO) {
        Long currentUserId = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllHaveReleasedMessageByCondition(currentUserId, messageConditionVO);
    }

    //用户根据条件来查询所有自己未发送的消息,(这个没有时间概念)
    @GetMapping("/anrmessagebc")
    public Result<PageResult<Message>> getAllNotReleasedMessageByCondition(MessageConditionVO messageConditionVO) {
        Long currentUserId = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllNotReleasedMessageByCondition(currentUserId, messageConditionVO);
    }

    //用户获取根据条件获取所有自己收到的并且可以看到的消息（消息过期）
    @GetMapping("/anoahrmessage")
    public Result<PageResult<Message>> getAllNotOverdueAndHaveReleasedMessage(MessageConditionVO messageConditionVO) {
        Long currentUserId = UserThreadHolder.getCurrentUser().getId();
        return messageServiceCore.getAllNotOverdueAndHaveReleasedMessage(currentUserId, messageConditionVO);
    }


}
