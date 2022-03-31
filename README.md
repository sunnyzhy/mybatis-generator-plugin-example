# 自定义mybatis-generator插件的调用示例

## 前言
由于 spring-boot:2.4.0 以上版本不兼容 maven-resources-plugin:3.2.0，所以示例中使用的是 spring-boot:2.3.6.RELEASE

## 使用方法
1. 在工程里添加自定义的插件依赖
   ```xml
   <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.6</version>
        <configuration>
            <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
            <overwrite>true</overwrite>
            <verbose>true</verbose>
        </configuration>
        <dependencies>
            <!-- 省略其他依赖 -->
            
            <!-- 添加自定义插件依赖 -->
            <dependency>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-plugin</artifactId>
                <version>0.0.1</version>
            </dependency>
        </dependencies>
    </plugin>
   ```
2. 在工程的配置文件 generatorConfig.xml 里添加自定义插件
   ```xml
   <generatorConfiguration>
       <properties resource="application.yml"/>

       <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
           <property name="beginningDelimiter" value="`"/>
           <property name="endingDelimiter" value="`"/>
          
           <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
               <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
           </plugin>
           <!-- 自定义的 mybatis-generator-swagger2 插件 -->
           <plugin type="org.mybatis.generator.plugin.Swagger2Plugin"/>

           <!-- 自定义的 mybatis-generator-lombok 插件 -->
           <plugin type="org.mybatis.generator.plugin.LombokPlugin"/>

           <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                           connectionURL="jdbc:mysql://localhost:3306/database?useSSL=false"
                           userId="root"
                           password="root">
               <!-- 设置 useInformationSchema 的值为 true，否则，IntrospectedTable 取到的表 comment 为空字符串 -->
               <property name="useInformationSchema" value="true" />
           </jdbcConnection>

           <javaModelGenerator targetPackage="com.zhy.model" targetProject="src/main/java"/>

           <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources"/>

           <javaClientGenerator targetPackage="com.zhy.mapper" targetProject="src/main/java"
                                type="XMLMAPPER"/>

           <table tableName="table" delimitAllColumns="true">
               <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
           </table>

       </context>
   </generatorConfiguration>
   ```
   
   - 关键字加 ```````，注意以下三点:
      ```xml
      <property name="beginningDelimiter" value="`"/>
      <property name="endingDelimiter" value="`"/>

      delimitAllColumns="true"
      ```
3. 配置自定义插件之后生成的代码
   ```java
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
   ```
4. 没有配置自定义插件之前生成的代码
   ```java
   @Table(name = "mbg_test")
   public class MbgTest {
       /**
        * 主键
        */
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer id;
   
       /**
        * 姓名
        */
       private String name;
   
       /**
        * 年龄
        */
       private Integer age;
   
       /**
        * 获取主键
        *
        * @return id - 主键
        */
       public Integer getId() {
           return id;
       }
   
       /**
        * 设置主键
        *
        * @param id 主键
        */
       public void setId(Integer id) {
           this.id = id;
       }
   
       /**
        * 获取姓名
        *
        * @return name - 姓名
        */
       public String getName() {
           return name;
       }
   
       /**
        * 设置姓名
        *
        * @param name 姓名
        */
       public void setName(String name) {
           this.name = name;
       }
   
       /**
        * 获取年龄
        *
        * @return age - 年龄
        */
       public Integer getAge() {
           return age;
       }
   
       /**
        * 设置年龄
        *
        * @param age 年龄
        */
       public void setAge(Integer age) {
           this.age = age;
       }
   }
   ```
## 使用 swagger2-3.0.0 需要注意的事项
1. 添加 swagger2 依赖
   
   1.1. 排除 springfox-boot-starter 自带的 swagger-annotations 和 swagger-models 依赖
   
   1.2. 添加 1.6.2 版本的 swagger-annotations 和 swagger-models 依赖
   
   ```xml
   <dependency>
   	<groupId>io.springfox</groupId>
   	<artifactId>springfox-boot-starter</artifactId>
   	<version>3.0.0</version>
   	<exclusions>
   		<exclusion>
   			<groupId>io.swagger</groupId>
   			<artifactId>swagger-annotations</artifactId>
   		</exclusion>
   		<exclusion>
   			<groupId>io.swagger</groupId>
   			<artifactId>swagger-models</artifactId>
   		</exclusion>
   	</exclusions>
   </dependency>
   <dependency>
   	<groupId>io.swagger</groupId>
   	<artifactId>swagger-annotations</artifactId>
   	<version>1.6.2</version>
   </dependency>
   <dependency>
   	<groupId>io.swagger</groupId>
   	<artifactId>swagger-models</artifactId>
   	<version>1.6.2</version>
   </dependency>
   ```
2. 使用 @EnableSwagger2 注解标注 swagger2 的配置类
3. 在 @ApiImplicitParam 注解里，使用 dataTypeClass 细化 dataType:
   ```java
   @ApiImplicitParams({
   		@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", dataTypeClass = Integer.class, paramType = "path")
   })
   ```
4. 浏览 swagger2 在线文档
   ```bash
   http://localhost:8100/swagger-ui/index.html
   ```
