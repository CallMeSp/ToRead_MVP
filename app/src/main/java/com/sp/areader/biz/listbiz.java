package com.sp.areader.biz;

import android.os.Message;
import android.util.Log;

import com.sp.areader.bean.catalogue;
import com.sp.areader.presenter.listPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by my on 2016/11/27.
 */
public class listbiz implements Ilistbiz {
    listPresenter presenter;
    ArrayList<catalogue> catalogue_list=new ArrayList<catalogue>();
    ArrayList<String> name_list=new ArrayList<String>(),url_list=new ArrayList<String>();
    public listbiz(listPresenter presenter){
        this.presenter=presenter;
    }
    @Override
    public void showCatalogue(final String url){
        /*final String uuu=url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    catalogue_list.clear();
                    Log.e("0", "thread starts");
                    Document doc = Jsoup.connect(uuu).get();
                    Elements items=doc.select("dd");
                    for (Element Item : items) {
                        String title=Item.select("dd").text();
                        String ur=Item.select("a").attr("href");
                        ur=uuu+ur;
                        name_list.add(title);
                        url_list.add(ur);
                    }

                    presenter.updateUI(name_list,url_list);
                    Log.e("0","thread ends");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();*/
        showCatalogueByRxjava(url);
    }
    public void showCatalogueByRxjava(final String url){
        Log.e("rx2","???????");
        Observable.just(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, Map>() {
                    @Override
                    public Map apply(String s) throws Exception {
                        Map tosave=new IdentityHashMap();
                        catalogue_list.clear();
                        Log.e("0", "thread starts");
                        Document doc = Jsoup.connect(url).get();
                        Elements items=doc.select("dd");
                        for (Element Item : items) {
                            String title=Item.select("dd").text();
                            String ur=Item.select("a").attr("href");
                            ur=url+ur;
                            name_list.add(title);
                            url_list.add(ur);
                        }
                        tosave.put("title",name_list);
                        tosave.put("url",url_list);
                        return tosave;
                    }
                })
                .subscribe(new DisposableObserver<Map>() {
                    @Override
                    public void onNext(Map value) {
                        presenter.updateUI((ArrayList)value.get("title"),(ArrayList)value.get("url"));
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
