import com.sun.org.glassfish.gmbal.Description;

import java.util.Random;

/**
 * ps: 如果想看小规模排序结果直接拷贝 ———— int[] a = {1,9,6,8,2,4,10,7,5,9};
 * @date: 2021-3-17 19:04
 * @author: Znw · Smile
 * @Description:快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        // 造15w数据
        int threshold = 150000;
        int[] a1 = new int[threshold];
        int[] a2 = new int[threshold];
        Random random = new Random();
        for(int i=0;i<a1.length;i++){
            int num = (random.nextInt(threshold));
            a1[i] = num;
            a2[i] = num;
        }

        // 计算第一种快排的总耗时
        long begin = System.currentTimeMillis();
        quickSort(a1, 0, a1.length - 1);
        long end = System.currentTimeMillis();
        long timeCost = end - begin;
        System.out.println(timeCost);

        // 计算第二种快排的总耗时
        long begin2 = System.currentTimeMillis();
        quickSort2(a2, 0, a2.length - 1);
        long end2 = System.currentTimeMillis();
        long timeCost2 = end2 - begin2;
        System.out.println(timeCost2);

    }

    /*快速排序有两种模式，但核心思想都是找中枢点，并且以中枢点为中心，
    按照规则（左小又大or左大右小）将数据排列，再左右分别反复递归。*/

    /**
     * 这个是游标分别从两头开始的模式 (出自王道考研) ，好处稍微简化了代码，
     * 但是效率没有我下面写的第二种方法快 (可能原因在while判断上)
     *
     * @Param: [array待排序数组, head头下标, tail尾下标]
     * @Return: void
     * @Author: Znw · Smile
     * @CreateDate: 2021-3-17 19:18
     */
    private static void quickSort(int[] a, int head, int tail) {

        int low = head;
        int high = tail;
        int pivot = a[low];
        if (low < high) {

            while (low < high) {
                // 通过low和high的不断的分别赋值，最终将左右(左小右大or左大右小)全部按定义对照中枢点填满了
                // 此时永远只会有一个数是重复的，那就是中枢点的值
                // 在这个重复的值最终被定位在中枢点下标后，结束外层while循环
                // 接着将先前定义的中枢点值赋给这个中枢点下标位置,将重负值替换，也就是 a[low] = pivot
                // 这样就成功完成了一次排序，接下通过外层判断，以中枢点为中心，左右分开，分别再次进行同样的排序操作
                // 直至最后两边都分别只剩两个数据,且成功排序完毕为止，此时所有数据全部顺序排列完毕。
                while (low < high && pivot <= a[high]) {
                    high--;
                }
                a[low] = a[high];
                while (low < high && pivot >= a[low]) {
                    low++;
                }
                a[high] = a[low];
            }
            a[low] = pivot;

            // 当最后为三位（含中枢点），截取的左边两位排完后就满足if条件了，这时左边顺序也全部排完
            // low为不断向右遍历的游标，直至和另一个游标重合，此时中枢点确定，才停止，所以low-1的位置为下一次左边递归的范围。
            if (low > head + 1) {
                quickSort(a, head, low - 1);
            }

            // 当最后为三位（含中枢点），截取的右边两位排完后就满足if条件了，这时右边顺序也全部排完
            // high为不断向左遍历的游标，直至和另一个游标重合，此时中枢点确定，才停止，所以high+1的位置为下一次右边递归的范围。
            if (high < tail - 1) {
                quickSort(a, high + 1, tail);
            }
        }

    }


    /**
     * 这个是游标都从一头开始的模式 (出自极客时间-王争专栏) ，好处是效率比上面高，可读性更高
     * 快排 —— 主方法
     * @Param: [array待排序数组, head头下标, tail尾下标]
     * @Return: void
     * @Author: Znw · Smile
     * @CreateDate: 2021-3-17 19:22
     */
    private static void quickSort2(int[] array, int head, int tail) {
        if (head >= tail) {
            return;
        }

        // 排序细节
        int pivot = getPivot(array, head, tail);

        quickSort2(array, head, pivot - 1);
        quickSort2(array, pivot + 1, tail);
    }

    /**
     * 快排 —— 排序细节方法
     * @Param: [array, head, tail]
     * @Return: int
     * @Author: Znw · Smile
     * @CreateDate: 2021-3-17 19:22
     */
    private static int getPivot(int[] array, int head, int tail) {
        int pivotValue = array[tail];
        int low = head;
        while (head < tail) {
            if (array[head] <= pivotValue) {
                int tempValue = array[head];
                array[head] = array[low];
                array[low] = tempValue;
                low++;
            }

            head++;
        }

        array[tail] = array[low];
        array[low] = pivotValue;

        return low;

    }

}




