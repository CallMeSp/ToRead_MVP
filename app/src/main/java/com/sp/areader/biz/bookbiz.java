package com.sp.areader.biz;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sp.areader.bean.book;
import com.sp.areader.presenter.searchPresenter;
import com.sp.areader.view.ImainActivity;
import com.sp.areader.view.IsearchActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by my on 2016/11/22.
 */
public class bookbiz implements Ibookbiz{     //根据搜索的书目名字拿到书的列表
    ArrayList<book> books=new ArrayList<book>();
    searchPresenter presenter;
    public bookbiz(searchPresenter presenter){
        this.presenter=presenter;
    }
    @Override
    public void showbookslist(final String searchname){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    books.clear();
                    Document doc = Jsoup.connect("http://so.37zw.com/cse/search?q=" + searchname + "&click=1&s=2041213923836881982&nsid=")
                            .get();
                    Elements items=doc.select("div.game-legend-a");
                    for (Element Item : items) {
                        Log.e("0","Item:"+Item);
                        String title=Item.select("h3").text();
                        String detail=Item.select("p.result-game-item-desc").text();
                        String ur=Item.select("div.game-legend-a").attr("onclick");
                        ur=ur.substring(17, ur.length() - 1);
                        String writer=Item.select("p.result-game-item-info-tag").first().text();
                        String IMG=Item.select("img").attr("src");
                        book mybook=new book();
                        mybook.setBook_name(title);
                        mybook.setBook_writter(writer);
                        mybook.setBook_details(detail);
                        mybook.setBook_cover(IMG);
                        mybook.setContenturl(ur);
                        books.add(mybook);
                    }
                    presenter.updatelist(books);
                } catch (IOException e){

                    e.printStackTrace();
                }
            }
        }).start();*/
        showbookListbyRxjava(searchname);
    }
    public void showbookListbyRxjava(String bookname){
        Observable.just(bookname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, ArrayList<book>>() {
                    @Override
                    public ArrayList<book> apply(String s) throws Exception {
                        books.clear();
                        Document doc = Jsoup.connect("http://so.37zw.com/cse/search?q=" + s + "&click=1&s=2041213923836881982&nsid=")
                                .get();
                        Elements items=doc.select("div.game-legend-a");
                        for (Element Item : items) {
                            Log.e("0","Item:"+Item);
                            String title=Item.select("h3").text();
                            String detail=Item.select("p.result-game-item-desc").text();
                            String ur=Item.select("div.game-legend-a").attr("onclick");
                            ur=ur.substring(17, ur.length() - 1);
                            String writer=Item.select("p.result-game-item-info-tag").first().text();
                            String IMG=Item.select("img").attr("src");
                            book mybook=new book();
                            mybook.setBook_name(title);
                            mybook.setBook_writter(writer);
                            mybook.setBook_details(detail);
                            mybook.setBook_cover(IMG);
                            mybook.setContenturl(ur);
                            books.add(mybook);
                        }
                        return books;
                    }
                })
                .subscribe(new DisposableObserver<ArrayList<book>>() {
                    @Override
                    public void onNext(ArrayList<book> value) {
                        presenter.updatelist(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("bookbiz","onerror");
                    }
                    @Override
                    public void onComplete() {
                        Log.e("bookbiz","onComplete");
                    }
                });
    }
}
