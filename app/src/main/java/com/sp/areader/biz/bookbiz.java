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

/**
 * Created by my on 2016/11/22.
 */
public class bookbiz implements Ibookbiz{//根据搜索的书目名字拿到书的列表
    ArrayList<book> books=new ArrayList<book>();
    searchPresenter presenter;
    public bookbiz(searchPresenter presenter){
        this.presenter=presenter;
    }
    @Override
    public void showbookslist(final String searchname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    books.clear();
                    Log.e("0", "search starts");
                    Document doc = Jsoup.connect("http://so.37zw.com/cse/search?q=" + searchname + "&click=1&s=2041213923836881982&nsid=")
                            .get();
                    // Log.e("0","doc:"+doc);
                    Elements items=doc.select("div.game-legend-a");
                    //Log.e("0","items:"+items);
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
                        //search_bookvover_list.add(bitmap);
                        Log.e("0", "tt:" + title + "  detail:" + detail + "      ur:" + ur + "    writer:" + writer+"    img:"+IMG);
                    }
                    presenter.updatelist(books);
                    Log.e("0", "thread ends");
                } catch (IOException e){

                    e.printStackTrace();
                }
            }
        }).start();
    }
}
