/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * The Class DefaultCommentGenerator.
 *
 * @author Jeff Butler
 */
public class DefaultCommentGenerator implements CommentGenerator {

    /** The properties. */
    private Properties properties;
    
    /** The suppress date. */
    private boolean suppressDate;
    
    /** The suppress all comments. */
    private boolean suppressAllComments;

    /** The addition of table remark's comments.
     * If suppressAllComments is true, this option is ignored*/
    private boolean addRemarkComments;
    
    private SimpleDateFormat dateFormat;

    /**
     * Instantiates a new default comment generator.
     */
    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    /* 
     * Java文件加注释
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	compilationUnit.addFileCommentLine("/*");
    	compilationUnit.addFileCommentLine("* "+compilationUnit.getType().getShortName()+".java");
    	compilationUnit.addFileCommentLine("* Copyright(C) 2017-2020 贯众健康公司");
    	compilationUnit.addFileCommentLine("* @date "+sdf.format(new Date())+"");
    	compilationUnit.addFileCommentLine("*/");

        // add no file level comments by default
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     *
     * @param xmlElement
     *            the xml element
     */
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addRootComment(org.mybatis.generator.api.dom.xml.XmlElement)
     */
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addConfigurationProperties(java.util.Properties)
     */
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }


    /**
     * 类注释
     */
    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    	innerClass.addJavaDocLine("/**");
    	innerClass.addJavaDocLine(" * @Title "+introspectedTable.getFullyQualifiedTable()+"表的实体类");
    	innerClass.addJavaDocLine(" * @Description "+introspectedTable.getFullyQualifiedTable().getRemarks());
    	innerClass.addJavaDocLine(" * @version 1.0");
    	innerClass.addJavaDocLine(" * @Author fendo");
    	innerClass.addJavaDocLine(" * @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	innerClass.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addTopLevelClassComment(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addEnumComment(org.mybatis.generator.api.dom.java.InnerEnum, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addEnumComment(InnerEnum innerEnum,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum
                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        String remarks = "";
        if(introspectedColumn.getRemarks() != null) {
        	remarks = introspectedColumn.getRemarks();
        }
        
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * @Fields "+field.getName()+" "+remarks);
        field.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGeneralMethodComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); 
        sb.append(" * ");
        if (method.isConstructor()) {
            sb.append(" 构造查询条件");
        }
        String method_name = method.getName();
        if ("setOrderByClause".equals(method_name)) {
            sb.append(" 设置排序字段");
        } else if ("setDistinct".equals(method_name)) {
            sb.append(" 设置过滤重复数据");
        } else if ("getOredCriteria".equals(method_name)) {
            sb.append(" 获取当前的查询条件实例");
        } else if ("isDistinct".equals(method_name)) {
            sb.append(" 是否过滤重复数据");
        } else if ("getOrderByClause".equals(method_name)) {
            sb.append(" 获取排序字段");
        } else if ("createCriteria".equals(method_name)) {
            sb.append(" 创建一个查询条件");
        } else if ("createCriteriaInternal".equals(method_name)) {
            sb.append(" 内部构建查询条件对象");
        } else if ("clear".equals(method_name)) {
            sb.append(" 清除查询条件");
        } else if ("countByExample".equals(method_name)) {
            sb.append(" 根据指定的条件获取数据库记录数");
        } else if ("deleteByExample".equals(method_name)) {
            sb.append(" 根据指定的条件删除数据库符合条件的记录");
        } else if ("deleteByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            sb.append(" 新写入数据库记录");
        } else if ("insertSelective".equals(method_name)) {
            sb.append(" 动态字段,写入数据库记录");
        } else if ("selectByExample".equals(method_name)) {
            sb.append(" 根据指定的条件查询符合条件的数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            sb.append(" 根据指定主键获取一条数据库记录");
        } else if ("updateByExampleSelective".equals(method_name)) {
            sb.append(" 动态根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByExample".equals(method_name)) {
            sb.append(" 根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKeySelective".equals(method_name)) {
            sb.append(" 动态字段,根据主键来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            sb.append(" 根据主键来更新符合条件的数据库记录");
        }else if("findList".equals(method_name)){
			sb.append(" 带参数查询列表");
		}else if("insertBatchSelective".equals(method_name)){
			sb.append(" 插入集合");
		}else if("updateBatchByPrimaryKeySelective".equals(method_name)){
			sb.append(" 集合更新");
		}else if("deleteList".equals(method_name)){
			sb.append(" 删除集合(物理删除)");
		}else if("updateIsValidList".equals(method_name)){
			sb.append(" 批量更新启用状态(软删除)");
		}else if("updateIsValid".equals(method_name)){
			sb.append(" 单个更新启用状态(软删除)");
		}else if("updateVersionAutoIncrease".equals(method_name)){
			sb.append(" 版本号自增+1");
		}
        sb.append(":");
        sb.append(introspectedTable.getFullyQualifiedTable());
        method.addJavaDocLine(sb.toString());

        final List<Parameter> parameterList = method.getParameters();
        if (!parameterList.isEmpty()) {
            method.addJavaDocLine(" *");
            if ("or".equals(method_name)) {
                sb.append(" 增加或者的查询条件,用于构建或者查询");
            }
        } else {
            if ("or".equals(method_name)) {
                sb.append(" 创建一个新的或者查询条件");
            }
        }
        String paramterName;
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param "); //$NON-NLS-1$
            paramterName = parameter.getName();
            sb.append(paramterName);
            if ("orderByClause".equals(paramterName)) {
                sb.append(" 排序字段"); //$NON-NLS-1$
            } else if ("distinct".equals(paramterName)) {
                sb.append(" 是否过滤重复数据");
            } else if ("criteria".equals(paramterName)) {
                sb.append(" 过滤条件实例");
            }
            method.addJavaDocLine(sb.toString());
        }

        method.addJavaDocLine(" */"); 
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * 获取 "); //$NON-NLS-1$
        sb.append(introspectedColumn.getRemarks()).append(" 字段:");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" *"); //$NON-NLS-1$
        sb.setLength(0);
        sb.append(" * @return "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(", ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addSetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * 设置 ");  //$NON-NLS-1$
        sb.append(introspectedColumn.getRemarks()).append(" 字段:");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" *"); //$NON-NLS-1$
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param "); //$NON-NLS-1$
        sb.append(parm.getName());
        sb.append(" the value for "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(", ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * 类注释
     */
    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
    	innerClass.addJavaDocLine("/**");
    	innerClass.addJavaDocLine(" * @Title "+introspectedTable.getFullyQualifiedTable()+"表的实体类");
    	innerClass.addJavaDocLine(" * @Description "+introspectedTable.getFullyQualifiedTable().getRemarks());
    	innerClass.addJavaDocLine(" * @version 1.0");
    	innerClass.addJavaDocLine(" * @Author fendo");
    	innerClass.addJavaDocLine(" * @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	innerClass.addJavaDocLine(" */");
    }
}
