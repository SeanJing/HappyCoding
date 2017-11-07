## Selection Sort
quadratic time, irrelevant to input
```java
public class SelectionSort {
    public static void sort(Comparable[] a) {
        for(int i = 0; i < a.length; i++) {
            int min = i;
            for(int j = i+1;j < a.length; j++) {
                if(a[j].compareTo(a[min]) < 0) min = j;
            }
            swap(a, i, min);
        }
    }

    private void swap(Comparable[] a, int p, int q) {
        Comparable temp = a[p];
        a[p] = a[q];
        a[q] = temp;
    }
}
```

## Insertion Sort
1. best case: already sorted / worst case: reverse sorted
2. partially sorted array - inversions
```java
public class InsertionSort {
    public void static sort(Comparable[] a) {
        for(int i = 0; i < a.length; i++) {
            for(int j = i; j > 0; j--) {
                if(a[j].compareTo(a[j-1]) < 0) swap(a, j, j-1);
                else break;
            }
        }
    }
}
```

## Shellsort
h-sorting: Insertion sort with stride length h
```java
public class Shellsort{
    public static void sort(Comparable[] a) {
        int h = 1;
        while(h < a.length/3) h = h*3+1;
        while(h >= 1) {
            for(int i = h; i < a.length; i++) {
                for(int j = i; j >= h; j-=h) {
                    if(a[j].compareTo(a[j-h])) swap(a,j,j-h)
                    else break;
                }
            h/=3;
            }
        }
    }
}
```

## Merge Sort
1. NlogN time, extra N memory
2. optimal in time
3. stable
```java
public class Mergesort {

    /* in-place merge using an auxiliary array */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        //assertion isSorted(a, lo, mid);
        //assertion isSorted(a, mid+1, hi);
        /* copy */
        for(int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        /* merge */
        int i = lo, j = mid+1;
        for(int k = lo; k <= hi; k++) {
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }
    /* recursive sort using merge above */
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if(hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
    /* top-bottom sort */
    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
    }
    /* bottom-top sort */
    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        int N = a.length;
        for(int sz = 1; sz < N; sz+=sz)
            for(int lo = 0; lo < N-sz; lo+= sz+sz)
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }
}
```

Practical improvements:
1. Use insertion sort for small subarrays
2. Stop when already sorted
3. Eliminate the copy to auxiliary array

## Quicksort
### Partition
1. average performance: ~1.39NlnN
2. in-place, not stable

```java
public class QuickSort {

  private static int partition(Comparable[] a, int lo, int hi) {
      int i = lo, j = hi+1;
      while(true) {
          while(a[++i].compareTo(a[lo]) < 0)
              if(i == hi) break;
          while(a[lo].compareTo(a[--j]) < 0)
              if(j == lo) break;
          if(i >= j) break;
          swap(a, i, j);
      }
      swap(a, lo, j);
      return j;
  }

  public static void sort(Comparable[] a) {
      StdRandom.shuffle(a); // shuffle for guaranteed performance
      sort(a, 0 , a.length - 1);
  }

  private static void sort(Comparable[] a, int lo, int hi) {
      if(lo >= hi) return;
      int j = partition(a, lo, hi);
      sort(a, lo, j-1);
      sort(a, j+1, hi);
  }
}

```

Practical improvements:
1. Use insertion sort for small subarrays
2. median of sample(3)

### Quick-Select
  Given an array of N items, find the kth largest  
  Quicksort like partition, linear performance in average  
```java

public static Comparable select(Comparable[] a, int k) {
    StdRandom.shuffle(a);
    int lo = 0, hi = a.length-1;
    while(lo < hi) {
        int j = partition(a, lo, hi);
        if(j < k) lo = j+1;
        else if (j > k) hi = j-1;
        else return a[k];
    }
    return a[k];
}

```

### 3-way Partition: Duplicate keys
```java

public static void sort(Comparable[] a, int lo, int hi) {
    if(lo>=hi) return;
    int lt = lo, gt = hi;
    int i = lo;
    Comparable v = a[lo];
    while(i <= gt) {
        int cmp = a[i].compareTo(v);
        if(cmp < 0) swap(a, lt++, i++);
        else if(cmp > 0) swap(a, i, gt--);
        else i++;
    }
    sort(a, lo, lt-1);
    sort(a, gt+1, hi);
}
```

## Summary

|               |inplace|stable|worst |average|best |
|-----|---|---|---|---|---|---|---|---|---|---|
|Selection sort |   Y   |  N   |N^2/2 |N^2/2   | N^2/2|
|Insertion sort |   Y   |  Y   |N^2/2 | N^2/4  | N    |  
|Shell sort     |   Y   |  N   |?     |  ?     | N    |
|Merge sort     |   N   |  Y   | NlgN | NlgN   |  NlgN|  
|Quick sort     |   Y   |  N   | N^2/2| 2NlnN  |  NlgN|   
|3-way Quicksort|   Y   |  N   | N^2/2| 2NlnN  |  N   |   
