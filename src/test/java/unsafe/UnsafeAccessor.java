package unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xiangchao on 2020/4/19.
 */
public class UnsafeAccessor {
    public static void main(String[] args) throws NoSuchFieldException {
        Unsafe unsafe = UnsafeAccessor.getUnsafe();
        Field id = Student.class.getDeclaredField("id");
        Field name = Student.class.getDeclaredField("name");
// 获得成员变量的偏移量
        long idOffset = UnsafeAccessor.unsafe.objectFieldOffset(id);
        long nameOffset = UnsafeAccessor.unsafe.objectFieldOffset(name);
        Student student = new Student();
// 使用 cas 方法替换成员变量的值
        UnsafeAccessor.unsafe.compareAndSwapInt(student, idOffset, 0, 20); // 返回 true
        UnsafeAccessor.unsafe.compareAndSwapObject(student, nameOffset, null, "张三"); // 返回 true
        System.out.println(student);
    }
    static Unsafe unsafe;
    static {
        try {
            //访问私有成员变量用getDeclaredField
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }
    static Unsafe getUnsafe() {
        return unsafe;
    }
}

@Data
class Student {
    volatile int id;
    volatile String name;
}
