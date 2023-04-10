package deque;
import java.util.Comparator;
import java.util.Objects;

public class MaxArrayDeque<T> extends ArrayDeque<T>   {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c)
    {
       comparator=c;
    }
    public T max(Comparator<T> c){
        if(size()==0)
        {
            return null;
        }
        int maxindex=0;
        for (int i = 1; i < size(); i++) {
            if(c.compare(get(maxindex),get(i))<0)
            {
                    maxindex=i;
            }
        }
        return get(maxindex);
    }
    public T max()
    {
        return max(comparator);
    }
    }




