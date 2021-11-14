package org.shanzhaozhen.common.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BaseSearchForm",description = "基础分页列表查询前端传入参数")
public class BaseSearchForm<T> {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 生成mybatis的分页实体
     * @param
     * @return
     */
    public Page<T> getPage() {
        Page<T> page = new Page<>(this.current, this.size);
        page.setOrders(this.getOrders());
        return page;
    }

}
