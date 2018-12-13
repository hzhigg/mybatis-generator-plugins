package com.zgg.plugins;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * 
 * @author hzhigg
 * 2018年12月13日
 */
public class FindListPlugin extends PluginAdapter {
	
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		 return true;
	}

	
	
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		Method ibsmethod = new Method();
		ibsmethod.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType ibsreturnType = FullyQualifiedJavaType.getIntInstance();
		ibsmethod.setReturnType(ibsreturnType);
		ibsmethod.setName("findList");
		FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType paramListType;
		if (introspectedTable.getRules().generateBaseRecordClass()) {
			paramListType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		} else {
			if (!introspectedTable.getRules().generatePrimaryKeyClass()) {
				throw new RuntimeException(Messages.getString("RuntimeError.12"));
			}

			paramListType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
		}

		paramType.addTypeArgument(paramListType);
		ibsmethod.addParameter(new Parameter(paramType, "records"));
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(ibsmethod);
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();

		//创建Select查询
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "findList"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addElement(new TextElement("select * from "+ introspectedTable.getFullyQualifiedTableNameAtRuntime()+" where 1=1"));

        for (IntrospectedColumn introspectedColumn : columns) {
            String columnName = introspectedColumn.getActualColumnName();
              XmlElement iftest = new XmlElement("if");
              iftest.addAttribute(new Attribute("test",introspectedColumn.getJavaProperty() + "!=null"));
              iftest.addElement(new TextElement(" and "+columnName + "=#{"+introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "}"));
              select.addElement(iftest);   
          }
        
        
        XmlElement parentElement = document.getRootElement();
        parentElement.addElement(select);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	
	
	
	
	
	
}
