package com.sp.areader.presenter;

import com.sp.areader.view.IBookShelf;
import com.sp.areader.view.fragment.BookShelf;

/**
 * Created by my on 2016/12/10.
 */

public class BookshelfPresenter {
    private IBookShelf iBookShelf;
    public BookshelfPresenter(IBookShelf bookShelf){
        this.iBookShelf=bookShelf;
    }

}
