package org.mybatis.generator.plugin.example.controller;

import io.swagger.annotations.*;
import org.mybatis.generator.plugin.example.mapper.MbgTestMapper;
import org.mybatis.generator.plugin.example.model.MbgTest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhy
 * @date 2021/4/1 10:14
 */
@Api(tags = {"自定义mybatis-generator插件的调用示例"})
@RestController
public class ExampleController {
    private final MbgTestMapper testMapper;

    public ExampleController(MbgTestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @ApiOperation(value = "查询指定的记录", notes = "查询指定的记录")
    @GetMapping()
    public List<MbgTest> select(@ApiParam(name = "entity", value = "实体对象", required = true) MbgTest entity) {
        List<MbgTest> list = testMapper.select(entity);
        return list;
    }

    @ApiOperation(value = "查询所有的记录", notes = "查询所有的记录")
    @GetMapping(value = "/all")
    public List<MbgTest> selectAll() {
        List<MbgTest> list = testMapper.selectAll();
        return list;
    }

    @ApiOperation(value = "添加记录", notes = "添加记录")
    @PostMapping
    public MbgTest insert(@ApiParam(name = "entity", value = "实体对象", required = true) @RequestBody MbgTest entity) {
        testMapper.insertSelective(entity);
        return entity;
    }

    @ApiOperation(value = "修改记录", notes = "修改记录")
    @PutMapping
    public MbgTest update(@ApiParam(name = "entity", value = "实体对象", required = true) @RequestBody MbgTest entity) {
        testMapper.updateByPrimaryKey(entity);
        return entity;
    }

    @ApiOperation(value = "删除记录", notes = "删除记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", dataTypeClass = Integer.class, paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        testMapper.deleteByPrimaryKey(id);
    }
}
