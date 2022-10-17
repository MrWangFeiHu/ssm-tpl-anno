package com.tpl.ssm.anno.service.impl;

import com.tpl.ssm.anno.dao.TestDAO;
import com.tpl.ssm.anno.entity.TestEntity;
import com.tpl.ssm.anno.service.TestService;
import com.tpl.ssm.anno.util.MajorKeyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestDAO testDAO;

    public TestServiceImpl(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @Override
    public List<TestEntity> listTests(TestEntity cond) {
        return testDAO.listTests(cond);
    }

    @Override
    public TestEntity singleTest(TestEntity cond) {
        return testDAO.singleTest(cond);
    }

    @Override
    public boolean saveTest(TestEntity cond) {
        cond.setId(MajorKeyUtil.idSeq());
        return testDAO.insertTest(cond) > 0;
    }

    @Override
    public boolean modifyTest(TestEntity cond) {
        return testDAO.updateTest(cond) > 0;
    }

    @Override
    public boolean removeTest(TestEntity cond) {
        return testDAO.deleteTest(cond) > 0;
    }

    @Override
    public boolean removeTests(List<Long> testIds) {
        return testDAO.deleteTests(testIds) > 0;
    }
}
