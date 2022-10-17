package com.tpl.ssm.anno.dao;

import com.tpl.ssm.anno.entity.TestEntity;

import java.util.List;

/**
 * 测试DAO
 * <p>
 * 1.查询测试记录列表 - listTests
 * 2.查询单个测试记录 - singleTest
 * 3.新增测试记录 - insertTest
 * 4.修改测试记录 - updateTest
 * 5.删除测试记录 - deleteTest
 * 6.根据主键集删除测试记录 - deleteTests
 */
public interface TestDAO {

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
    public int insertTest(TestEntity cond);

    /**
     * @param cond 查询条件
     * @return 受影响的条数
     */
    public int updateTest(TestEntity cond);

    /**
     * 删除一条测试记录
     *
     * @param cond 查询条件
     * @return 受影响的条数
     */
    public int deleteTest(TestEntity cond);

    /**
     * 删除多条测试记录
     *
     * @param testIds 测试实体主键集
     * @return 受影响的条数
     */
    public int deleteTests(List<Long> testIds);

}
