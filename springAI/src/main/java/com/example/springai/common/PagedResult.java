package com.example.springai.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PagedResult<T> implements List<T> {

    private List<T> data;

    private long total;

    private int page;

    private int pageSize;

    @Override
    public int size() {
        if (data != null) {
            return data.size();
        }

        return 0;
    }

    @Override
    public boolean isEmpty() {
        if (data != null) {
            return data.isEmpty();
        }

        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (data != null) {
            data.contains(o);
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        if (data != null) {
            return data.iterator();
        }

        return null;
    }

    @Override
    public Object[] toArray() {
        if (data != null) {
            return data.toArray();
        }

        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (data != null) {
            return data.toArray(a);
        }

        return null;
    }

    @Override
    public boolean add(T t) {
        if (data != null) {
            return data.add(t);
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (data != null) {
            data.remove(o);
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (data != null) {
            return data.containsAll(c);
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (data != null) {
            return data.addAll(c);
        }

        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (data != null) {
            return data.addAll(index, c);
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (data != null) {
            data.retainAll(c);
        }

        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (data != null) {
            return data.retainAll(c);
        }

        return false;
    }

    @Override
    public void clear() {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    public T get(int index) {
        if (data != null) {
            data.get(index);
        }

        return null;
    }

    @Override
    public T set(int index, T element) {
        if (data != null) {
            return data.set(index, element);
        }

        return null;
    }

    @Override
    public void add(int index, T element) {
        if (data != null) {
            data.add(index, element);
        }
    }

    @Override
    public T remove(int index) {
        if (data != null) {
            return data.remove(index);
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        if (data != null) {
            return data.indexOf(o);
        }

        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (data != null) {
            return data.lastIndexOf(o);
        }

        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        if (data != null) {
            return data.listIterator();
        }

        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (data != null) {
            return data.listIterator(index);
        }

        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (data != null) {
            return data.subList(fromIndex, toIndex);
        }

        return null;
    }

    public PagedResult() {

    }

    public PagedResult(long offset, int limit, long total, List<T> data) {
        this.setPage((int) (offset / limit + 1));
        this.setPageSize(limit);
        this.setTotal(total);
        this.setData(data);
    }

    public PagedResult(int page, int pageSize, long total, List<T> data) {
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setTotal(total);
        this.setData(data);
    }

}
