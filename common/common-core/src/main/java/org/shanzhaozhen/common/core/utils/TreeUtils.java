package org.shanzhaozhen.common.core.utils;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {

    /**
     * 生成树状结构
     * @param nodeList
     * @return
     */
    public static <T> List<T> builtTree(List<T> nodeList, Class<T> className) {
        List<T> tree = null;
        try {
            tree = builtTree(nodeList, className, "id", "pid", "children", "priority");
//            sortTree(tree, className, "priority");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Assert.notNull(tree, "生成树结构失败");
        return tree;
    }

    /**
     * 生成树状结构
     * @param nodeList
     * @return
     */
    public static <T> List<T> builtTree(List<T> nodeList, Class<T> className, String idName, String pidName, String childrenName, String priorityName) throws IllegalAccessException {
        if (CollectionUtils.isEmpty(nodeList)) return new ArrayList<>();

        Field idField = ReflectionUtils.findField(className, idName);
        Field pidField = ReflectionUtils.findField(className, pidName);
        Field childrenField = ReflectionUtils.findField(className, childrenName);
        Field priorityField = null;

        if (StringUtils.hasText(priorityName)) {
            priorityField = ReflectionUtils.findField(className, priorityName);
        }

        Assert.isTrue(idField != null && pidField != null && childrenField != null, "树转换失败，对象没有对应的树结构特征");

        idField.setAccessible(true);
        pidField.setAccessible(true);
        childrenField.setAccessible(true);

        Map<Object, List<T>> pidMap = new HashMap<>();

        for (T node : nodeList) {
            Object pid = pidField.get(node);
            if (pid == null) {
                pid = 0;
            }
            List<T> pidList = pidMap.get(pid);
            if (CollectionUtils.isEmpty(pidList)) {
                pidList = new ArrayList<>();
            }
            pidList.add(node);
            pidMap.put(pid, pidList);
        }

        if (!pidMap.isEmpty()) {
            for (T node : nodeList) {
                List<T> children = pidMap.get(idField.get(node));
                if (!CollectionUtils.isEmpty(children)) {
                    sortTree(children, priorityField);
                    childrenField.set(node, children);
                }
            }
        }

        sortTree(pidMap.get(0), priorityField);
        return pidMap.get(0);
    }

//    /**
//     * 生成树状结构
//     * @param nodeList
//     * @return
//     */
//    public static <T> List<T> builtTree(List<T> nodeList, Class<T> className) {
//        List<T> tree = null;
//        try {
//            tree = builtTree(nodeList, className, "id", "pid", "children", "priority");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        Assert.notNull(tree, "生成树结构失败");
//        return tree;
//    }
//
//    /**
//     * 生成树状结构
//     * @param nodeList
//     * @return
//     */
//    public static <T> List<T> builtTree(List<T> nodeList, Class<T> className, String idName, String pidName, String childrenName, String priorityName) throws IllegalAccessException {
//        List<T> rootList = new ArrayList<>();
//        List<T> noRootList = new ArrayList<>();
//
//        Field idField = ReflectionUtils.findField(className, idName);
//        Field pidField = ReflectionUtils.findField(className, pidName);
//        Field childrenField = ReflectionUtils.findField(className, childrenName);
//        Field priorityField = null;
//
//        if (StringUtils.hasText(priorityName)) {
//            priorityField = ReflectionUtils.findField(className, priorityName);
//        }
//
//        Assert.isTrue(idField != null && pidField != null && childrenField != null, "树转换失败，对象没有对应的树结构特征");
//
//        idField.setAccessible(true);
//        pidField.setAccessible(true);
//        childrenField.setAccessible(true);
//
//        for (T node : nodeList) {
//            Long pid = (Long) pidField.get(node);
//            if (pid == null || pid <= 0) {
//                rootList.add(node);
//            } else {
//                noRootList.add(node);
//            }
//        }
//
//        getNodeChildren(noRootList, nodeList, idField, pidField, childrenField, priorityField);
//
//        sortTree(rootList, priorityField);
//
//        return rootList;
//    }
//
//    /**
//     * 对子节点进行递归查找
//     * @param noRootList
//     * @param children
//     * @return
//     */
//    public static <T> List<T> getNodeChildren(List<T> noRootList, List<T> children, Field idField, Field pidField, Field childrenField, Field priorityField) throws IllegalAccessException {
//        for (T child : children) {
//            List<T> grandsons = new ArrayList<>();
//            for (T noRoot : noRootList) {
//                Object id = idField.get(child);
//                Object pid = pidField.get(noRoot);
//                if (id != null && id.equals(pid)) {
//                    grandsons.add(noRoot);
//                }
//            }
//            if (grandsons.size() > 0) {
//                childrenField.set(child, getNodeChildren(noRootList, grandsons, idField, pidField, childrenField, priorityField));
//            }
//        }
//
//        sortTree(children, priorityField);
//
//        return children;
//    }


    /**
     * 排序树
     * @param list
     * @param priorityName
     * @param <T>
     * @return
     */
    public static <T> void sortTree(List<T> list, Class<T> className, String priorityName) {
        Field priorityField = ReflectionUtils.findField(className, priorityName);
        sortTree(list, priorityField);
    }

    /**
     * 排序树
     * @param list
     * @param priorityField
     * @param <T>
     * @return
     */
    public static <T> void sortTree(List<T> list, Field priorityField) {
        if (priorityField != null) {
            priorityField.setAccessible(true);
            list.sort((o1, o2) -> {
                try {
                    Integer priority1 = (Integer) priorityField.get(o1);
                    Integer priority2 = (Integer) priorityField.get(o2);

                    if (priority1 != null && priority2 != null) return priority1.compareTo(priority2);
                    if (priority1 == null && priority2 == null) return 0;
                    if (priority1 != null) return 1;
                    return -1;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            });
        }
    }


//    /**
//     * 生成树状结构
//     * @param nodeList
//     * @return
//     */
//    public static List<TreeModel> builtTree(List<? extends TreeModel> nodeList) {
//        List<TreeModel> rootList = new ArrayList<>();
//        List<TreeModel> noRootList = new ArrayList<>();
//
//        for (TreeModel node : nodeList) {
//            if (node.getPid() == null || node.getPid() <= 0) {
//                rootList.add(node);
//            } else {
//                noRootList.add(node);
//            }
//        }
//
//        getNodeChildren(noRootList, nodeList);
//
//        rootList.sort((Comparator.comparing(TreeModel::getPriority, Comparator.nullsLast(Comparator.naturalOrder()))));
//
//        return rootList;
//    }
//
//    /**
//     * 对子节点进行递归查找
//     * @param noRootList
//     * @param children
//     * @return
//     */
//    public static List<? extends TreeModel> getNodeChildren(List<? extends TreeModel> noRootList, List<? extends TreeModel> children) {
//        for (TreeModel child : children) {
//            List<TreeModel> grandsons = new ArrayList<>();
//            for (TreeModel noRoot : noRootList) {
//                if (child.getId().equals(noRoot.getPid())) {
//                    grandsons.add(noRoot);
//                }
//            }
//            if (grandsons.size() > 0) {
//                child.setChildren(getNodeChildren(noRootList, grandsons));
//            }
//        }
//        children.sort((Comparator.comparing(TreeModel::getPriority, Comparator.nullsLast(Comparator.naturalOrder()))));
//
//        return children;
//    }


}
