package nfl.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MatchReportImplNfl implements MatchReport {

	public static MatchReportImplNfl createMatchReport(DomNode dn, WebClient webClient) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		System.out.println("Starting match report");
		
		DomNode link = dn.getChildNodes().get(1);
		
		//#menu=highlights&tab=recap&recap=fullstory
		
		HtmlAnchor anchor = (HtmlAnchor)link;
		
		System.out.println(anchor.getHrefAttribute());
		
		String fullLink = anchor.getHrefAttribute() + "#menu=highlights&tab=recap&recap=fullstory";
		
		System.out.println(fullLink);
		
		HtmlPage page = null;
		
		
		try {
			page = webClient.getPage(fullLink);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(page != null){
				System.out.println("ok");
			} else {
				System.out.println("nok");
			}
			//e.printStackTrace();
		}
		
		DomNodeList<DomNode> allLinks = page.querySelectorAll("a.tab-link");
		
		HtmlElement node = (HtmlElement) allLinks.get(0);
		
		
		
		HtmlElement fullStory = page.getHtmlElementById("fullstory");
		
		List<HtmlPage> childPages = new ArrayList<>();
		
		Iterable<HtmlElement> children = fullStory.getChildElements().iterator().next().getChildElements();
		
		Iterator<HtmlElement> iter = children.iterator();
		
		while(iter.hasNext()){
			HtmlElement next = iter.next();
			childPages.add((HtmlPage) next.click());
			System.out.println("child :" + next.asXml());
			childPages.add((HtmlPage) node.click());
		}
		
		Thread.sleep(8000);
		
		Iterator<HtmlPage> iter2 = childPages.iterator();
		
		while(iter2.hasNext()){
			HtmlPage next = iter2.next();
			DomNode article = next.querySelector("div.articleText");
			
			//System.out.println("Getting article:" + next.asXml() );
			
			if (article == null){
				System.out.println("no article:" + next.getWebResponse().getWebRequest().getUrl());
			} else {
				System.out.println("success");
			}
		}
		
		DomNode article = page.querySelector("div.articleText");
		
		//System.out.println("Getting article:" + next.asXml() );
		
		if (article == null){
			System.out.println("orig no article:" + page.getWebResponse().getWebRequest().getUrl());
			
			
		} else {
			System.out.println(" orig success");
		}
		
	
		
		//System.out.println("try " + page2.asText());
		
		return null;
	}

}
