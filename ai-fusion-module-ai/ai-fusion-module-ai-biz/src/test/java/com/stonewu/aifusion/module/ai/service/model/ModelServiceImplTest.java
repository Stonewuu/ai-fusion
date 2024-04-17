package com.stonewu.aifusion.module.ai.service.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.stonewu.aifusion.framework.test.core.ut.BaseDbUnitTest;

import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.dal.mysql.model.ModelMapper;
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
 * {@link ModelServiceImpl} 的单元测试类
 *
 * @author AiFusion
 */
@Import(ModelServiceImpl.class)
public class ModelServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ModelServiceImpl modelService;

    @Resource
    private ModelMapper modelMapper;

    @Test
    public void testCreateModel_success() {
        // 准备参数
        ModelSaveReqVO createReqVO = randomPojo(ModelSaveReqVO.class).setId(null);

        // 调用
        Long modelId = modelService.createModel(createReqVO);
        // 断言
        assertNotNull(modelId);
        // 校验记录的属性是否正确
        ModelDO model = modelMapper.selectById(modelId);
        assertPojoEquals(createReqVO, model, "id");
    }

    @Test
    public void testUpdateModel_success() {
        // mock 数据
        ModelDO dbModel = randomPojo(ModelDO.class);
        modelMapper.insert(dbModel);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ModelSaveReqVO updateReqVO = randomPojo(ModelSaveReqVO.class, o -> {
            o.setId(dbModel.getId()); // 设置更新的 ID
        });

        // 调用
        modelService.updateModel(updateReqVO);
        // 校验是否更新正确
        ModelDO model = modelMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, model);
    }

    @Test
    public void testUpdateModel_notExists() {
        // 准备参数
        ModelSaveReqVO updateReqVO = randomPojo(ModelSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> modelService.updateModel(updateReqVO), MODEL_NOT_EXISTS);
    }

    @Test
    public void testDeleteModel_success() {
        // mock 数据
        ModelDO dbModel = randomPojo(ModelDO.class);
        modelMapper.insert(dbModel);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbModel.getId();

        // 调用
        modelService.deleteModel(id);
       // 校验数据不存在了
       assertNull(modelMapper.selectById(id));
    }

    @Test
    public void testDeleteModel_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> modelService.deleteModel(id), MODEL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetModelPage() {
       // mock 数据
       ModelDO dbModel = randomPojo(ModelDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setModelName(null);
           o.setModelType(null);
           o.setCreateTime(null);
       });
       modelMapper.insert(dbModel);
       // 测试 name 不匹配
       modelMapper.insert(cloneIgnoreId(dbModel, o -> o.setName(null)));
       // 测试 modelName 不匹配
       modelMapper.insert(cloneIgnoreId(dbModel, o -> o.setModelName(null)));
       // 测试 modelType 不匹配
       modelMapper.insert(cloneIgnoreId(dbModel, o -> o.setModelType(null)));
       // 测试 createTime 不匹配
       modelMapper.insert(cloneIgnoreId(dbModel, o -> o.setCreateTime(null)));
       // 准备参数
       ModelPageReqVO reqVO = new ModelPageReqVO();
       reqVO.setName(null);
       reqVO.setModelName(null);
       reqVO.setModelType(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ModelDO> pageResult = modelService.getModelPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbModel, pageResult.getList().get(0));
    }

}