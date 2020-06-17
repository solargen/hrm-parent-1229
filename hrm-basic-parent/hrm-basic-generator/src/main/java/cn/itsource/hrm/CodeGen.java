package cn.itsource.hrm;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-17
 */
public class CodeGen {


    private static Properties properties;

    static{

        properties = new Properties();
        try {
            properties.load(CodeGen.class.getClassLoader().getResourceAsStream("/mybatis-plus-generator-course.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath+properties.getProperty("serviceOutputDir");
        gc.setOutputDir(outputDir);
        gc.setAuthor(properties.getProperty("author"));
        gc.setBaseResultMap(true);
        gc.setOpen(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(properties.getProperty("jdbc.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(properties.getProperty("jdbc.driver"));
        dsc.setUsername(properties.getProperty("jdbc.username"));
        dsc.setPassword(properties.getProperty("jdbc.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(properties.getProperty("package.parent"));
        pc.setEntity("domain");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();

        //domain自定义生成路径
        String templatePath = "/templates/entity.java.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + properties.getProperty("interfaceOutputDir")
                        + "/cn/itsource/hrm/domain/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        //query自定义生成路径
        templatePath = "/templates/query.java.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + properties.getProperty("interfaceOutputDir")
                        + "/cn/itsource/hrm/query/" + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
            }
        });

        //mapper.xml自定义生成路径
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + properties.getProperty("serviceOutputDir") +"/src/main/resources/cn/itsource/hrm/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(null);
        templateConfig.setXml(null);
        templateConfig.setController("template/mycontroller.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(properties.getProperty("tables").split(","));
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();


    }

}
