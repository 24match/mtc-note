# Java集合

## Collection和Collections区别

Collection是一个集合接口，提供了对集合对象进行基本操作的通用操作方法。是List，Set等的父接口。

Collection是一个包装类，含有多种集合操作的静态多态方法，不能实例化，类似于工具类，服务于Java的Collection框架。

## Set和List的区别？

List,Set都是继承自Collection接口。都是用来存储一组相同类型的元素的。

List特点：元素有放入的顺序，有序列表的集合

Set特点：元素无放入顺序，保证无重复元素的集合

## ArrayList和LinkedList和Vector的区别

ArrayList是一个可改变大小的数组，数组的大小会随着元素的增加而动态增长，所以对不同的操作会有不同的效率

LinkedList是一个双链表，在添加和删除元素时具有比ArrayList更好的性能，但在get与set方面都弱于ArrayList

Vector和ArrayList类似，但属于强同步类，如果程序是线程安全的，那么ArrayList是一个好的选择

Vector和ArrayList在新增更多元素的时候会请求更大的空间。Vector每次请求其大小的双倍空间，而ArrayList每次增长50%

在默认情况下，ArrayList的初始容量非常小，分配一个比较大的初始值属于最佳实践，这样可以减少调整大小的开销。

### 区别

> 同步处理：Vector同步，ArrayList非同步，Vector缺省情况下长度会增长为原来<kbd>1</kbd>倍，ArrayList是<kbd>0.5</kbd>倍
> ArrayList：
> > int newCapacity = oldCapacity + (oldCapacity >> 1);
>
> Vector：
> > int newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity);

### List

List是最基础的一种集合, 他是一种有序集合