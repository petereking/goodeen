package com.goodeen.pagination.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;

@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor<T> implements Interceptor {
  private static String pagePattern = "";
  private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY =
      new DefaultObjectWrapperFactory();
  private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

  private static MetaObject forObject(Object object) {
    return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
        REFLECTOR_FACTORY);
  }

  public Object intercept(Invocation invocation) throws Throwable {
    final Object[] args = invocation.getArgs();
    MappedStatement mappedStatement = (MappedStatement) args[0];
    if (mappedStatement.getId().matches(pagePattern)) {
      Integer total = 0;
      List<T> content = Collections.EMPTY_LIST;
      MetaObject msObject = forObject(args[0]);
      Class<?> resultType = (Class<?>) msObject.getValue("resultMaps[0].type");
      msObject.setValue("resultMaps[0].type", Integer.class);
      Pageable page = (Pageable) ((ParamMap) args[1]).get("pageable");
      if (mappedStatement.getSqlSource() instanceof DynamicSqlSource) {
        MetaObject nodeObject = forObject(msObject.getValue("sqlSource.rootSqlNode"));
        List<SqlNode> nodeList = (List<SqlNode>) nodeObject.getValue("contents");
        try {
          nodeList.add(0, new TextSqlNode("select count(1) from ("));
          nodeList.add(new TextSqlNode(") getcount"));
          total = (Integer) ((List) invocation.proceed()).get(0);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          msObject.setValue("resultMaps[0].type", resultType);
          nodeList.remove(0);
          nodeList.remove(nodeList.size() - 1);
        }
        try {
          StringBuilder condition = new StringBuilder(" limit ${page.offset}, ${page.pageSize}");
          if (page.getSort() != null) {
            condition.insert(0, "order by " + page.getSort().toString().replace(":", ""));
          }
          nodeList.add(new TextSqlNode(condition.toString()));
          content = (List<T>) invocation.proceed();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          nodeList.remove(nodeList.size() - 1);
        }
      } else {
        String sql = (String) msObject.getValue("sqlSource.sqlSource.sql");
        try {
          msObject.setValue("sqlSource.sqlSource.sql",
              "select count(1) from (" + sql + ") getcount");
          total = (Integer) ((List) invocation.proceed()).get(0);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          msObject.setValue("sqlSource.sqlSource.sql", sql);
          msObject.setValue("resultMaps[0].type", resultType);
        }
        SqlSource sqlSource = (SqlSource) msObject.getValue("sqlSource.sqlSource");
        List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
        parameterMappings.addAll(sqlSource.getBoundSql(args[1]).getParameterMappings());
        msObject.setValue("sqlSource.sqlSource.parameterMappings", parameterMappings);
        StringBuilder condition = new StringBuilder(sql);
        if (page.getSort() != null) {
          condition.append(" order by " + page.getSort().toString().replace(":", ""));
        }
        condition.append(" limit " + page.getOffset() + "," + page.getPageSize());
        msObject.setValue("sqlSource.sqlSource.sql", condition.toString());
        try {
          content = (List<T>) invocation.proceed();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          msObject.setValue("sqlSource.sqlSource.sql", sql);
        }
      }
      forObject(page).setValue("size", total);
      return content;
    }
    return invocation.proceed();
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties p) {
    pagePattern = p.getProperty("pagePattern");
  }
}
