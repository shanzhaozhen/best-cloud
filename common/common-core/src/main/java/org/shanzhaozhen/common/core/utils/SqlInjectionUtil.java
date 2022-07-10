package org.shanzhaozhen.common.core.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * sql注入处理工具类
 */
@Slf4j
public class SqlInjectionUtil {

	private final static String XSS_STR = "and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|user()";

    /**show tables*/
	private final static String SHOW_TABLES = "show\\s+tables";

	/**
	 * 检查字段是否存在SQL注入风险
	 * @param column 字段名
	 */
	private static void filterContent(String column) {
		if (column == null || "".equals(column)) {
			return;
		}
		// 统一转为小写
		column = column.toLowerCase();
		//SQL注入检测存在绕过风险
		column = column.replaceAll("/\\*.*\\*/","");

		String[] xssArr = XSS_STR.split("\\|");
		for (String s : xssArr) {
			if (column.contains(s)) {
				log.error("请注意，存在SQL注入关键词---> {}", s);
				log.error("请注意，值可能存在SQL注入风险!---> {}", column);
				throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + column);
			}
		}
		if(Pattern.matches(SHOW_TABLES, column)){
			throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + column);
		}
	}

	/**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * @param columns 字段名数组
	 */
	public static void filterContent(String[] columns) {
		for (String column : columns) {
			filterContent(column);
		}
	}

	/**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * @param orderItem 字段名实体
	 */
	public static void filterContent(OrderItem orderItem) {
		filterContent(orderItem.getColumn());
	}

	/**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * @param orderItemList 字段名实体数组
	 */
	public static void filterContent(List<OrderItem> orderItemList) {
		for (OrderItem orderItem : orderItemList) {
			filterContent(orderItem);
		}
	}

	/**
	 * 检查字段是否在实体类中
	 * @param columns 字段列表
	 * @param clazz 实体类
	 */
	public static void checkColumnsInEntity(String[] columns, Class<?> clazz) {
		List<Field> allFields = TableInfoHelper.getAllFields(clazz);
		List<String> fieldName = allFields.stream().map(Field::getName).collect(Collectors.toList());

		for (String column: columns) {
			if (!fieldName.contains(column)) {
				log.error("请注意，存在SQL注入关键词---> {}", column);
				log.error("请注意，值可能存在SQL注入风险!---> {}", column);
				throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + column);
			}
		}
	}

	public static void checkColumnsInEntity(String column, Class<?> clazz) {
		checkColumnsInEntity(new String[]{column}, clazz);

	}

	public static void checkColumnsInEntity(OrderItem orderItem, Class<?> clazz) {
		checkColumnsInEntity(orderItem.getColumn(), clazz);
	}

	public static void checkColumnsInEntity(List<OrderItem> orderItemList, Class<?> clazz) {
		String[] columns = orderItemList.stream().map(OrderItem::getColumn).toArray(String[] :: new);
		checkColumnsInEntity(columns, clazz);
	}

}
