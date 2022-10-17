package com.tpl.ssm.anno.service;

import com.tpl.ssm.anno.entity.TestEntity;

import java.util.List;

public interface TestService {

    /**
     * 测试集
     *
     * @param cond 查询条件
     * @return 测试集
     */
    public List<TestEntity> listTests(TestEntity cond);

    /**
     * 单一测试实体
     *
     * @param cond 查询条件
     * @return 测试实体
     */
    public TestEntity singleTest(TestEntity cond);

    /**
     * 新增测试记录
     *
     * @param cond 查询条件
     * @return 受影响的条数
     */
    public boolean saveTest(TestEntity cond);

    /**
     * @param cond 查询条件
     * @return 受影响的条数
     */
    public boolean modifyTest(TestEntity cond);

    /**
     * 删除一条测试记录
     *
     * @param cond 查询条件
     * @return 受影响的条数
     */
    public boolean removeTest(TestEntity cond);

    /**
     * 删除多条测试记录
     *
     * @param testIds 测试实体主键集
     * @return 受影响的条数
     */
    public boolean removeTests(List<Long> testIds);

}
