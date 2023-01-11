## 若依系统中的树结构用的不是很顺手, 中间的值映射不知道为什么总是会null, 暂时还没有找到解决方案, 结合网上方案重写了一个树结构, 做一点记录

> 需要生成一个文件的树状结构

```java
/**
 * 树状结构工具类
 */
public class TreeUtils {

    private List<HhDwgDir> dirCommon;

    public List<Object> dirList(List<HhDwgDir> dirs) {
        this.dirCommon = dirs;
        List<Object> list = new ArrayList<>();
        for (HhDwgDir dir : dirs) {
            Map<String, Object> map = new LinkedHashMap<>();
            // 判断根节点是否为空
            if (dir.getParentId() == 0) {
                setTreeData(map, dir);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 子集查找遍历, 直到没有子节点为止
     *
     * @param dirId
     * @return
     */
    private List<Object> dirChild(Long dirId) {
        List<Object> list = new ArrayList<>();
        for (HhDwgDir dir : dirCommon) {
            Map<String, Object> map = new LinkedHashMap<>();
            // 当前的父ID与文件ID相同时, 证明存在子节点
            if (dir.getParentId().equals(dirId)) {
                setTreeData(map, dir);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 树赋值
     *
     * @param map
     * @param dir
     */
    private void setTreeData(Map<String, Object> map, HhDwgDir dir) {
        map.put("id", dir.getDirId());
        map.put("label", dir.getDirName());
        // 当父ID为0时, 子节点存在的children不继续处理
        if (dir.getParentId() == 0) {
            map.put("children", dirChild(dir.getDirId()));
        }
    }
}
```

### 控制器调用

```java
public class Tree {
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(HhDwgDir hhDwgDir) {

        TreeUtils      dirTree = new TreeUtils();
        List<HhDwgDir> list    = hhDwgDirService.selectHhDwgDirList(hhDwgDir);

        // 用于拷贝的list
        List<HhDwgDir> copyList = new ArrayList<>();

        setDirData(list, copyList);
        List<Object> dirList = dirTree.dirList(copyList);

        return AjaxResult.success(dirList);
    }

    private void setDirData(List<HhDwgDir> list, List<HhDwgDir> copyList) {
        for (HhDwgDir dir : list) {
            HhDwgDir dwgDir = new HhDwgDir();
            dwgDir.setDirId(dir.getDirId());
            dwgDir.setDirName(dir.getDirName());
            dwgDir.setParentId(dir.getParentId());
            copyList.add(dwgDir);
        }
    }
}
```

### 最终生成结果(json)

```json
{
  "msg": "操作成功",
  "code": 200,
  "data": [
    {
      "id": 200,
      "label": "xx",
      "children": [
        {
          "id": 202,
          "label": "xx"
        },
        {
          "id": 203,
          "label": "xx"
        }
      ]
    },
    {
      "id": 208,
      "label": "TEST",
      "children": [
        {
          "id": 209,
          "label": "1122"
        }
      ]
    }
  ]
}
```
