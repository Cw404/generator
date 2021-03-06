/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.ITemplate;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器属性配置
 *
 * @author nieqiurong 2020/10/11.
 * @since 3.5.0
 */
public class Controller implements ITemplate {

    private Controller() {
    }

    /**
     * 生成 <code>@RestController</code> 控制器
     * <pre>
     *      <code>@Controller</code> -> <code>@RestController</code>
     * </pre>
     */
    private boolean restStyle = false;
    /**
     * 驼峰转连字符
     * <pre>
     *      <code>@RequestMapping("/managerUserActionHistory")</code> -> <code>@RequestMapping("/manager-user-action-history")</code>
     * </pre>
     */
    private boolean hyphenStyle = false;

    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superClass;

    /**
     * 转换输出控制器文件名称
     *
     * @since 3.5.0
     */
    private ConverterFileName converterFileName = (entityName -> entityName + ConstVal.CONTROLLER);

    public boolean isRestStyle() {
        return restStyle;
    }

    public boolean isHyphenStyle() {
        return hyphenStyle;
    }

    public String getSuperClass() {
        return superClass;
    }

    public ConverterFileName getConverterFileName() {
        return converterFileName;
    }

    @Override
    @NotNull
    public Map<String, Object> renderData(@NotNull TableInfo tableInfo) {
        Map<String, Object> data = new HashMap<>();
        if (this.hyphenStyle) {
            data.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }
        data.put("controllerMappingHyphenStyle", this.hyphenStyle);
        data.put("restControllerStyle", this.restStyle);
        data.put("superControllerClassPackage", StringUtils.isBlank(superClass) ? null : superClass);
        data.put("superControllerClass", ClassUtils.getSimpleName(this.superClass));
        return data;
    }

    public static class Builder extends BaseBuilder {

        private final Controller controller = new Controller();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 父类控制器
         *
         * @param clazz 父类控制器
         * @return this
         */
        public Builder superClass(@NotNull Class<?> clazz) {
            return superClass(clazz.getName());
        }

        /**
         * 父类控制器
         *
         * @param superClass 父类控制器类名
         * @return this
         */
        public Builder superClass(String superClass) {
            this.controller.superClass = superClass;
            return this;
        }

        /**
         * 是否驼峰转连字符
         *
         * @return this
         */
        public Builder hyphenStyle(boolean hyphenStyle) {
            this.controller.hyphenStyle = hyphenStyle;
            return this;
        }

        /**
         * 生成@RestController控制器
         *
         * @param restStyle 是否生成@RestController控制器
         * @return this
         */
        public Builder restStyle(boolean restStyle) {
            this.controller.restStyle = restStyle;
            return this;
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertFileName(@NotNull ConverterFileName converter) {
            this.controller.converterFileName = converter;
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatFileName(String format) {
            return convertFileName((entityName) -> String.format(format, entityName));
        }

        public Controller get() {
            return this.controller;
        }
    }
}
