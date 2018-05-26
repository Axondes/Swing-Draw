package com.company.swingdraw;

import java.util.*;

//@autho Moh-aw

public class SizedStack<T> extends Stack<T> {

    private final int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public Object push(Object object) {
        while (this.size() > maxSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }
}
