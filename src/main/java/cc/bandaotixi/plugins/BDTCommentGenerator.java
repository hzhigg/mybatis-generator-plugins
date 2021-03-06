package cc.bandaotixi.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class BDTCommentGenerator extends DefaultCommentGenerator {
	public BDTCommentGenerator() {
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
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
		sb.append(",");
		sb.append(introspectedTable.getFullyQualifiedTable());
		method.addJavaDocLine(sb.toString());

		List<Parameter> parameterList = method.getParameters();
		if (!parameterList.isEmpty()) {
			method.addJavaDocLine(" *");
			if ("or".equals(method_name)) {
				sb.append(" 增加或者的查询条件,用于构建或者查询");
			}
		} else if ("or".equals(method_name)) {
			sb.append(" 创建一个新的或者查询条件");
		}

		for (Parameter parameter : parameterList) {
			sb.setLength(0);
			sb.append(" * @param ");
			String paramterName = parameter.getName();
			sb.append(paramterName);
			if ("orderByClause".equals(paramterName)) {
				sb.append(" 排序字段");
			} else if ("distinct".equals(paramterName)) {
				sb.append(" 是否过滤重复数据");
			} else if ("criteria".equals(paramterName)) {
				sb.append(" 过滤条件实例");
			}
			method.addJavaDocLine(sb.toString());
		}

		method.addJavaDocLine(" */");
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		String shortName = innerClass.getType().getShortName();
		innerClass.addJavaDocLine("/**");

		innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getRemarks());
		innerClass.addJavaDocLine(" * " + shortName);
		innerClass.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());

		innerClass.addJavaDocLine(" */");
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		String shortName = innerClass.getType().getShortName();
		innerClass.addJavaDocLine("/**");
		innerClass.addJavaDocLine(" * 类注释");
		innerClass.addJavaDocLine(" * " + shortName);
		innerClass.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());

		innerClass.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuffer sb = new StringBuffer();
		field.addJavaDocLine("/**");
		if (introspectedColumn.getRemarks() != null)
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
		sb.append(" * 表字段 : ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		field.addJavaDocLine(sb.toString());

		field.addJavaDocLine(" */");
	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();

		method.addJavaDocLine("/**");

		sb.append(" * 获取 ");
		sb.append(introspectedColumn.getRemarks()).append(" 字段:");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" *");

		sb.setLength(0);
		sb.append(" * @return ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		sb.append(", ");
		sb.append(introspectedColumn.getRemarks());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" */");
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();

		method.addJavaDocLine("/**");

		sb.append(" * 设置 ");
		sb.append(introspectedColumn.getRemarks()).append(" 字段:");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" *");

		Parameter parm = (Parameter) method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		sb.append(" the value for ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		sb.append(", ");
		sb.append(introspectedColumn.getRemarks());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" */");
	}

	public void addComment(XmlElement xmlElement) {
	}
}
