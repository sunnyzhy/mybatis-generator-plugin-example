package org.mybatis.generator.plugin.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="MbgTest", description="mybatis-generator插件测试表")
@Table(name = "mbg_test")
@Data
public class MbgTest {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private Integer age;
}