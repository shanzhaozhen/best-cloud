package org.shanzhaozhen.uaa.config.datasource;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 自定义分页主键
 * 此处主要修改了前端传入的驼峰字段进行排序，后端将其转换成下划线拼接字段
 */
public class CustomPaginationInnerInterceptor extends PaginationInnerInterceptor {

    public CustomPaginationInnerInterceptor(DbType dbType) {
        super(dbType);
    }

    public CustomPaginationInnerInterceptor(IDialect dialect) {
        super(dialect);
    }

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    protected IDialect findIDialect(Executor executor) {
        return super.findIDialect(executor);
    }

    @Override
    protected MappedStatement buildCountMappedStatement(MappedStatement ms, String countId) {
        return super.buildCountMappedStatement(ms, countId);
    }

    @Override
    protected MappedStatement buildAutoCountMappedStatement(MappedStatement ms) {
        return super.buildAutoCountMappedStatement(ms);
    }

    @Override
    protected String autoCountSql(IPage<?> page, String sql) {
        return super.autoCountSql(page, sql);
    }

    @Override
    protected String lowLevelCountSql(String originalSql) {
        return super.lowLevelCountSql(originalSql);
    }

    @Override
    public String concatOrderBy(String originalSql, List<OrderItem> orderList) {
        return super.concatOrderBy(originalSql, orderList);
    }

    @Override
    protected List<OrderByElement> addOrderByElements(List<OrderItem> orderList, List<OrderByElement> orderByElements) {
//        return super.addOrderByElements(orderList, orderByElements);

        List<OrderByElement> additionalOrderBy = orderList.stream()
                .filter(item -> StringUtils.isNotBlank(item.getColumn()))
                .map(item -> {
                    OrderByElement element = new OrderByElement();
//                    element.setExpression(new Column(item.getColumn()));
                    element.setExpression(new Column(StringUtils.camelToUnderline(item.getColumn())));
                    element.setAsc(item.isAsc());
                    element.setAscDescPresent(true);
                    return element;
                }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(orderByElements)) {
            return additionalOrderBy;
        }
        // github pull/3550 优化排序，比如：默认 order by id 前端传了name排序，设置为 order by name,id
        additionalOrderBy.addAll(orderByElements);
        return additionalOrderBy;

    }

    @Override
    protected boolean continuePage(IPage<?> page) {
        return super.continuePage(page);
    }

    @Override
    protected void handlerLimit(IPage<?> page, Long limit) {
        super.handlerLimit(page, limit);
    }

    @Override
    protected void handlerOverflow(IPage<?> page) {
        super.handlerOverflow(page);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    @Override
    public Log getLogger() {
        return super.getLogger();
    }

    @Override
    public boolean isOverflow() {
        return super.isOverflow();
    }

    @Override
    public Long getMaxLimit() {
        return super.getMaxLimit();
    }

    @Override
    public DbType getDbType() {
        return super.getDbType();
    }

    @Override
    public IDialect getDialect() {
        return super.getDialect();
    }

    @Override
    public boolean isOptimizeJoin() {
        return super.isOptimizeJoin();
    }

    @Override
    public void setOverflow(boolean overflow) {
        super.setOverflow(overflow);
    }

    @Override
    public void setMaxLimit(Long maxLimit) {
        super.setMaxLimit(maxLimit);
    }

    @Override
    public void setDbType(DbType dbType) {
        super.setDbType(dbType);
    }

    @Override
    public void setDialect(IDialect dialect) {
        super.setDialect(dialect);
    }

    @Override
    public void setOptimizeJoin(boolean optimizeJoin) {
        super.setOptimizeJoin(optimizeJoin);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected boolean canEqual(Object other) {
        return super.canEqual(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public CustomPaginationInnerInterceptor() {
        super();
    }

    @Override
    public boolean willDoUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        return super.willDoUpdate(executor, ms, parameter);
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        super.beforeUpdate(executor, ms, parameter);
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        super.beforePrepare(sh, connection, transactionTimeout);
    }

    @Override
    public void beforeGetBoundSql(StatementHandler sh) {
        super.beforeGetBoundSql(sh);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }



}
