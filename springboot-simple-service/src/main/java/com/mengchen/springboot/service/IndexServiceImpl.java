package com.mengchen.springboot.service;

import com.mengchen.springboot.dao.mapper.SimpleMapper;
import com.mengchen.springboot.model.Simple;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private SimpleMapper simpleMapper;

    @Override
    public void getIndex() {
        Simple simple = simpleMapper.selectByPrimaryKey(1);
        System.out.println(simple);
    }
}
