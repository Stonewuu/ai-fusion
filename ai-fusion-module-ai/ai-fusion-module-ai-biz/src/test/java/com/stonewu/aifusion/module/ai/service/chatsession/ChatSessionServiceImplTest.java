package com.stonewu.aifusion.module.ai.service.chatsession;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.stonewu.aifusion.framework.test.core.ut.BaseDbUnitTest;

import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatSessionDO;
import com.stonewu.aifusion.module.ai.dal.mysql.chatsession.ChatSessionMapper;
import com.stonewu.aifusion.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants.*;
import static com.stonewu.aifusion.framework.test.core.util.AssertUtils.*;
import static com.stonewu.aifusion.framework.test.core.util.RandomUtils.*;
import static com.stonewu.aifusion.framework.common.util.date.LocalDateTimeUtils.*;
import static com.stonewu.aifusion.framework.common.util.object.ObjectUtils.*;
import static com.stonewu.aifusion.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link ChatSessionServiceImpl} 的单元测试类
 *
 * @author AiFusion
 */
@Import(ChatSessionServiceImpl.class)
public class ChatSessionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ChatSessionServiceImpl chatSessionService;

    @Resource
    private ChatSessionMapper chatSessionMapper;

    @Test
    public void testCreateChatSession_success() {
        // 准备参数
        ChatSessionSaveReqVO createReqVO = randomPojo(ChatSessionSaveReqVO.class).setId(null);

        // 调用
        Long chatSessionId = chatSessionService.createChatSession(createReqVO);
        // 断言
        assertNotNull(chatSessionId);
        // 校验记录的属性是否正确
        ChatSessionDO chatSession = chatSessionMapper.selectById(chatSessionId);
        assertPojoEquals(createReqVO, chatSession, "id");
    }

    @Test
    public void testUpdateChatSession_success() {
        // mock 数据
        ChatSessionDO dbChatSession = randomPojo(ChatSessionDO.class);
        chatSessionMapper.insert(dbChatSession);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ChatSessionSaveReqVO updateReqVO = randomPojo(ChatSessionSaveReqVO.class, o -> {
            o.setId(dbChatSession.getId()); // 设置更新的 ID
        });

        // 调用
        chatSessionService.updateChatSession(updateReqVO);
        // 校验是否更新正确
        ChatSessionDO chatSession = chatSessionMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, chatSession);
    }

    @Test
    public void testUpdateChatSession_notExists() {
        // 准备参数
        ChatSessionSaveReqVO updateReqVO = randomPojo(ChatSessionSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> chatSessionService.updateChatSession(updateReqVO), CHAT_SESSION_NOT_EXISTS);
    }

    @Test
    public void testDeleteChatSession_success() {
        // mock 数据
        ChatSessionDO dbChatSession = randomPojo(ChatSessionDO.class);
        chatSessionMapper.insert(dbChatSession);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbChatSession.getId();

        // 调用
        chatSessionService.deleteChatSession(id);
       // 校验数据不存在了
       assertNull(chatSessionMapper.selectById(id));
    }

    @Test
    public void testDeleteChatSession_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> chatSessionService.deleteChatSession(id), CHAT_SESSION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetChatSessionPage() {
       // mock 数据
       ChatSessionDO dbChatSession = randomPojo(ChatSessionDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setAssistantId(null);
           o.setCreateTime(null);
       });
       chatSessionMapper.insert(dbChatSession);
       // 测试 userId 不匹配
       chatSessionMapper.insert(cloneIgnoreId(dbChatSession, o -> o.setUserId(null)));
       // 测试 assistantId 不匹配
       chatSessionMapper.insert(cloneIgnoreId(dbChatSession, o -> o.setAssistantId(null)));
       // 测试 createTime 不匹配
       chatSessionMapper.insert(cloneIgnoreId(dbChatSession, o -> o.setCreateTime(null)));
       // 准备参数
       ChatSessionPageReqVO reqVO = new ChatSessionPageReqVO();
       reqVO.setUserId(null);
       reqVO.setAssistantId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ChatSessionDO> pageResult = chatSessionService.getChatSessionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbChatSession, pageResult.getList().get(0));
    }

}