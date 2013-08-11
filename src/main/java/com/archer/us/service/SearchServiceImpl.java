package com.archer.us.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

	@Override
	public void searchEmail(String url) {
		// TODO Auto-generated method stub
		 try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			Elements eList = doc.select(":contains(a)");
			
			 for (Element link : links) {
				 System.out.println(link.attr("abs:href"));
				 
		        }
			 
			 for(Element e:eList){
				 System.out.println( e.text());
			 }
			 
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 private  void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }

	
    private  String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
