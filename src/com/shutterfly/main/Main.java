package com.shutterfly.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import com.shutterfly.model.Customer;
import com.shutterfly.model.Events;
import com.shutterfly.model.Image;
import com.shutterfly.model.Order;
import com.shutterfly.model.SiteVisit;

 
 

public class Main {
   
	public static final String INPUT_FILE="C:\\Users\\Ankit\\Downloads\\GitRepositories\\Shutterfly\\input\\input.txt";
	public static final String OUTPUT_FILE="C:\\Users\\Ankit\\Downloads\\GitRepositories\\Shutterfly\\output\\output.txt";
	public static List<Events> eventList= new ArrayList<>();
	public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss.SS");
	
	
	public static void main(String[] args) throws IOException,ParseException {
		List<Customer> topCustomersList = new ArrayList<Customer>();
		String everything = "";
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
            
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();   
			} 
		finally 
		{
		    br.close();
		}
		    ingest(everything,eventList);    
		    topCustomersList=topXSimpleLTVCustomers(5,eventList);   
		    
			File fout=null;
			FileOutputStream fos=null;
			OutputStreamWriter out=null;
			
			try {
				fout = new File(OUTPUT_FILE);
				fos = new FileOutputStream(fout);
				out = new OutputStreamWriter(fos);
				BufferedWriter oFile = new BufferedWriter(out);
				oFile.write("CustomerName "+"     "+" Customer LTV");
				oFile.write("\r\n");
				for (Customer cust:topCustomersList) {
					oFile.write(cust.getLastName() +" "+ cust.getCustomerLTV());
					oFile.write("\r\n");
				}
				oFile.flush();
				oFile.close();
			} catch (IOException e) {	
				e.printStackTrace();
			}
	}

	
	private static void ingest(String everything, List<Events> eventList) {
		try {
		 JsonReader jsonReader = Json.createReader(new StringReader(everything));
		 JsonArray array = jsonReader.readArray();
		 
	    for(int i=0; i<array.size(); i++){ 
	    	JsonObject temp = array.getJsonObject(i);
	             	
	        if(temp.getString("type").equals("CUSTOMER"))
	        {
	        	Customer customer = new Customer();
	        	customer.setType(temp.getString("type"));
	        	customer.setVerb(temp.getString("verb"));
	        	customer.setKey(temp.getString("key"));
	        	customer.setEventTime(temp.getString("event_time"));
	        	customer.setLastName(temp.getString("last_name"));
	        	customer.setAdrCity(temp.getString("adr_city"));
	        	customer.setAdrState(temp.getString("adr_state"));
	        	eventList.add(customer); 
	        }
	        
	        else if (temp.getString("type").equals("SITE_VISIT"))
	        {
	        	SiteVisit siteVisit = new SiteVisit();
	        	siteVisit.setType(temp.getString("type"));
	        	siteVisit.setVerb(temp.getString("verb"));
	        	siteVisit.setKey(temp.getString("key"));
	        	siteVisit.setEventTime(temp.getString("event_time"));
	        	siteVisit.setCustomerId(temp.getString("customer_id"));
	        	siteVisit.setEventTime(temp.getString("event_time"));
	        	eventList.add(siteVisit);	
	        }
	                
	        else if(temp.getString("type").equals("IMAGE"))
	        {
	        	Image image = new Image();
	        	image.setType(temp.getString("type"));
	        	image.setVerb(temp.getString("verb"));
	        	image.setKey(temp.getString("key"));
	        	image.setEventTime(temp.getString("event_time"));
	        	image.setCustomerId(temp.getString("customer_id"));
	        	image.setCameraMake(temp.getString("camera_make"));
	        	image.setCameraModel(temp.getString("camera_model"));
	        	eventList.add(image);	
	        }
	        
	        else if (temp.getString("type").equals("ORDER"))
	        {
	        	Order order = new Order();
	        	order.setType(temp.getString("type"));
	        	order.setVerb(temp.getString("verb"));
	        	order.setKey(temp.getString("key"));
	        	order.setEventTime(temp.getString("event_time"));
	        	order.setCustomerId(temp.getString("customer_id"));
	        	order.setTotalAmount(temp.getString("total_amount"));
	        	eventList.add(order);
	        }     
	    }
		} catch (JsonException e) {
			e.printStackTrace();
		}
	}
	
	
	private static List<Customer> topXSimpleLTVCustomers(int x, List<Events> eventList) {
		double customerExpendituresPerVisit;
		double totalCustomerExpenditure;
		
		List<Customer> topxCustomers = new ArrayList<Customer>();
		List<SiteVisit> siteVisitByCustomer = new ArrayList<SiteVisit>();
		List<Customer> customerList = new ArrayList<Customer>();
	    List<SiteVisit> siteVisitList = new ArrayList<SiteVisit>();
	    List<Image> imageList = new ArrayList<Image>();
	    List<Order> orderList = new ArrayList<Order>();
	    
	    try{	    	
	    for(int i =0;i<eventList.size();i++)
	    {
	    	if(eventList.get(i) instanceof Customer)
	    	{
	    		customerList.add((Customer) eventList.get(i));	
	    	}
	    	
	    	else if(eventList.get(i) instanceof SiteVisit)
	    	{
	    		siteVisitList.add((SiteVisit) eventList.get(i));
	    	}
	    	
	    	else if(eventList.get(i) instanceof Order)
	    	{
	    		orderList.add((Order) eventList.get(i));
	    	}
	    	
	    	else if(eventList.get(i) instanceof Image)
	    	{
	    		imageList.add((Image) eventList.get(i));
	    	}		    	
	    }
	       	
	    for(Customer c:customerList)
	    {
	    	Date dateMin = null;
	    	Date dateMax = null ;
	    	float diff;
	    	double totalVisits=1;
	    	String customerID = c.getKey();
	    	
			for(SiteVisit sv:siteVisitList)
			{
				if(sv.getCustomerId().equals(customerID))
				{
					siteVisitByCustomer.add(sv);				
				}	
			}
			
			 
			Collections.sort(siteVisitByCustomer, new Comparator<SiteVisit>(){
				   public int compare(SiteVisit o1, SiteVisit o2){
					   Date date1=null;
					   Date date2=null;
					    try {
					    	date1 = formatter.parse(o1.getEventTime());
							date2 = formatter.parse(o2.getEventTime());
						} catch (ParseException e) {
							e.printStackTrace();
						}     
						return date1.compareTo(date2);
				   }
				});
	    	
	    	if(!siteVisitByCustomer.isEmpty())
	    	{
		   	  dateMin = formatter.parse(siteVisitByCustomer.get(0).getEventTime());
			  dateMax = formatter.parse(siteVisitByCustomer.get(siteVisitByCustomer.size()-1).getEventTime());
	    	}
	    	
	    	if(dateMin==null || dateMax==null)
	    	{
	    		diff=0;
	    	}
	    	else
	    	{
	    		diff = dateMax.getTime() - dateMin.getTime();
	    		totalVisits=siteVisitByCustomer.size();
	    	}
	    		
			float weeks;
			if(diff!=0)
			{
				weeks = (diff / (7 * 24 * 60 * 60 * 1000 ));
				
				if(weeks< 0)
				{
					weeks=1;
				}
			}
			else
			{
				weeks=1;
				
			}
			
			double numberOfSiteVisitsPerWeek = totalVisits/weeks;
					
	    	double ordersTotalAmount = 0;
			
			for(Order o:orderList)
			{
				if(o.getCustomerId().equals(customerID))
				{
					ordersTotalAmount = ordersTotalAmount+ Double.parseDouble(o.getTotalAmount().substring(0,o.getTotalAmount().length()-4));			
				}
				
			}
			
			totalCustomerExpenditure =ordersTotalAmount;
	    	
	    	customerExpendituresPerVisit = totalCustomerExpenditure / totalVisits;
	    	
	    	Double customerLTV = 52*(customerExpendituresPerVisit*numberOfSiteVisitsPerWeek)*10; 
	    	c.setCustomerLTV(customerLTV);
	   		topxCustomers.add(c);
	      }
	    
	      Collections.sort(topxCustomers,new Comparator<Customer>() {
	    	    @Override
	    	    public int compare(Customer a, Customer b) {
	    	        return b.getCustomerLTV().compareTo(a.getCustomerLTV());
	    	    }
	    	});
	    
	    }  catch (ParseException e) {
			e.printStackTrace();
		}
	    return topxCustomers.subList(0, x);
	}

}