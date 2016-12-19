package com.sp.areader.biz;

import android.util.Log;

import com.sp.areader.bean.book;
import com.sp.areader.presenter.InflatePresnter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by my on 2016/12/11.
 */

public class InflateBookType {
    ArrayList<book> books=new ArrayList<book>();
    String NumofPages;
    InflatePresnter presenter;
    public InflateBookType(InflatePresnter presenter){
        this.presenter=presenter;
    }
    public void showbookslist(final String url){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("inBiz",url);
                    books.clear();
                    Document doc = Jsoup.connect(url)
                            .get();
                    Elements items=doc.select("div.item");
                    //Log.e("inBiz",items.size()+"");
                    for (Element Item : items) {
                        //Log.e("0","Item:"+Item);
                        String title=Item.select("img").attr("alt");
                        String detail=Item.select("dd").text();
                        String ur=Item.select("a").attr("href");
                        String writer=Item.select("span").text();
                        String IMG=Item.select("img").attr("src");
                        //Log.e("book","title="+title+"writer="+writer+"detail="+detail+"ur="+ur+"img="+IMG);
                        book mybook=new book();
                        mybook.setBook_name(title);
                        mybook.setBook_writter(writer);
                        mybook.setBook_details(detail);
                        mybook.setBook_cover(IMG);
                        mybook.setContenturl(ur);
                        books.add(mybook);
                    }
                    presenter.updatelist(books);
                    Elements i=doc.select("a.last");
                    for (Element Item : i) {
                        NumofPages=Item.select("a.last").text();
                        Log.e("a",NumofPages);
                    }
                    presenter.postNUM(NumofPages);
                } catch (IOException e){

                    e.printStackTrace();
                }
            }
        }).start();*/
        getByRxJava(url);
    }
    public void getByRxJava(final String url){
        Observable.just(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, ArrayList<book>>() {
                    @Override
                    public ArrayList<book> apply(String s) throws Exception {
                        books.clear();
                        Document doc = Jsoup.connect(url).get();
                        Elements items=doc.select("div.item");
                        for (Element Item : items) {
                            String title=Item.select("img").attr("alt");
                            String detail=Item.select("dd").text();
                            String ur=Item.select("a").attr("href");
                            String writer=Item.select("span").text();
                            String IMG=Item.select("img").attr("src");
                            book mybook=new book();
                            mybook.setBook_name(title);
                            mybook.setBook_writter(writer);
                            mybook.setBook_details(detail);
                            mybook.setBook_cover(IMG);
                            mybook.setContenturl(ur);
                            books.add(mybook);
                        }
                        Elements i=doc.select("a.last");
                        for (Element Item : i) {
                            NumofPages=Item.select("a.last").text();
                            Log.e("a",NumofPages);
                        }
                        return books;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ArrayList<book>>() {
                    @Override
                    public void onNext(ArrayList<book> value) {
                        Log.e("onNext","!!!!!!");
                        presenter.updatelist(value);
                        presenter.postNUM(NumofPages);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error","!!!!!!");
                    }
                    @Override
                    public void onComplete() {
                        Log.e("complete","!!!!!!");
                    }
                });
    }
}
