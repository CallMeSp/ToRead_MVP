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
        final String uuu=url;
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
                        /*catalogue catalogue_item=new catalogue();
                        catalogue_item.settitle(title);
                        catalogue_item.seturl(ur);
                        catalogue_list.add(catalogue_item);*/
                        name_list.add(title);
                        url_list.add(ur);
                    }
                    presenter.updateUI(name_list,url_list);
                    Log.e("0","thread ends");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
