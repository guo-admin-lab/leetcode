import java.util.ArrayList;
import java.util.List;

public class Test295 {

    public static void main(String[] args) {
        MedianFinder295 medianFinder295 = new MedianFinder295();
        medianFinder295.addNum(1);
        medianFinder295.addNum(2);
        System.out.println(medianFinder295.findMedian());
        medianFinder295.addNum(3);
        System.out.println(medianFinder295.findMedian());
    }

}

class MedianFinder295 {

    List<Double> nums;

    public MedianFinder295() {
        nums = new ArrayList<>();
    }

    public void addNum(int num) {
        if (nums.size() == 0) {
            nums.add((double) num);
            return;
        }
        for(int i=0; i<nums.size(); i++) {
            if (num <= nums.get(i)) {
                nums.add(i, (double) num);
                return;
            }
        }
        nums.add((double) num);
    }

    public double findMedian() {
        int size = nums.size();
        if (size % 2 == 0) {
            return (nums.get(size/2) + nums.get(size/2-1)) / 2;
        } else {
            return nums.get(size/2);
        }
    }
}
