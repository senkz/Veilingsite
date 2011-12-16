package com.veilingsite.client.widgets;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.veilingsite.shared.MapUtil;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.ServerServiceAsync;

public class StatBidWidget extends VerticalPanel {	
	private VerticalPanel chartPlace1 = new VerticalPanel();
	private VerticalPanel chartPlace2 = new VerticalPanel();
	private VerticalPanel chartPlace3 = new VerticalPanel();
	private VerticalPanel chartPlace4 = new VerticalPanel();
	
	public StatBidWidget() {
		
		add(chartPlace1);
		add(chartPlace2);
		add(chartPlace3);
		add(chartPlace4);
		
		
	    Runnable onLoadCallback = new Runnable() {
	      public void run() {
	        createDayChart();
	        createDayOfWeekChart();
	      }
	    };
	    VisualizationUtils.loadVisualizationApi(onLoadCallback, AreaChart.PACKAGE);
	    

	    Runnable onLoadCallback2 = new Runnable() {
	      public void run() {
	        createHighscoreTable();
	      }
	    };
	    VisualizationUtils.loadVisualizationApi(onLoadCallback2, Table.PACKAGE);
	    
        createHighestBidTable();
	}

	private void createDayChart() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Map<String, Integer>> callback = new AsyncCallback<Map<String, Integer>>() {		
			@Override
			public void onFailure(Throwable caught) {}
			
			@Override
			public void onSuccess(Map<String, Integer> result) {
				Map<String, Integer> sresult = MapUtil.sortByKey(result);
				
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "Day");
				data.addColumn(ColumnType.NUMBER, "Amount of bids placed");
				
				for (Entry<String, Integer> entry : sresult.entrySet()) {			
					data.addRows(1);		
				    data.setValue(data.getNumberOfRows()-1, 0, entry.getKey());
				    data.setValue(data.getNumberOfRows()-1, 1, entry.getValue());
				}
				
				Options lOpt = Options.create();
				lOpt.setWidth(940);
				lOpt.setHeight(240);
				lOpt.setLegend(LegendPosition.NONE);
				AreaChart dayChart = new AreaChart(data, lOpt);
				dayChart.draw(data);
			    
				VerticalPanel vp = new VerticalPanel();
			    vp.add(new Label("Amount of bids placed per day:"));
			    vp.add(dayChart);
			    
			    addChart(vp, 1);
			}
		};
		myService.getDayStatistics(callback);
	}

	private void createHighscoreTable() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Map<String, Integer>> callback = new AsyncCallback<Map<String, Integer>>() {		
			@Override
			public void onFailure(Throwable caught) {}
			
			@Override
			public void onSuccess(Map<String, Integer> result) {
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "Auction");
				data.addColumn(ColumnType.NUMBER, "Amount of bids placed");

				for (Entry<String, Integer> entry : result.entrySet()) {			
					data.addRows(1);
				    data.setValue(data.getNumberOfRows()-1, 0, entry.getKey());
				    data.setValue(data.getNumberOfRows()-1, 1, entry.getValue());
				}
				
				Table highscoreTable = new Table(data, null);
				highscoreTable.draw(data);
				VerticalPanel vp = new VerticalPanel();
			    vp.add(new Label("The most amount of bids where placed on the following auction:"));
			    vp.add(highscoreTable);
			    
			    addChart(vp, 2);
			}
		};
		myService.getBestAuctions(callback);
	}
	
	private void createDayOfWeekChart() {
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Map<Integer, Integer>> callback = new AsyncCallback<Map<Integer, Integer>>() {		
			@Override
			public void onFailure(Throwable caught) {}
			
			@Override
			public void onSuccess(Map<Integer, Integer> result) {
				
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "Day");
				data.addColumn(ColumnType.NUMBER, "Amount of bids placed");
				
				for (Entry<Integer, Integer> entry : result.entrySet()) {			
					data.addRows(1);
					String s = null;
					switch(entry.getKey()) {
					default:
					case 1:
						s = "Monday";
						break;
					case 2:
						s = "Tuesday";
						break;
					case 3:
						s = "Wednesday";
						break;
					case 4:
						s = "Thursday";
						break;
					case 5:
						s = "Friday";
						break;
					case 6:
						s = "Saturday";
						break;
					case 7:
						s = "Sunday";
						break;
						
					}
				    data.setValue(data.getNumberOfRows()-1, 0, s);
				    data.setValue(data.getNumberOfRows()-1, 1, entry.getValue());
				}
				
				Options lOpt = Options.create();
				lOpt.setWidth(300);
				lOpt.setHeight(240);
				PieChart dayOfWeekChart = new PieChart(data, lOpt);
				dayOfWeekChart.draw(data);
				VerticalPanel vp = new VerticalPanel();
			    vp.add(new Label("Amount of bids sorted by week day:"));
			    vp.add(dayOfWeekChart);
			    
			    addChart(vp, 3);
			}
		};
		myService.getDayOfWeekStatistics(callback);
	}
	
	private void createHighestBidTable() {		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2010);
		cal.add(Calendar.MONTH, -4);
		Date d = cal.getTime();
		
		System.out.println("Dit is een date:");
		System.out.println(d.toString());

		String s = DateTimeFormat.getFormat("yyyy-MM-dd").format(d);
		
		ServerServiceAsync myService = (ServerServiceAsync) GWT.create(ServerService.class);
		AsyncCallback<Double> callback = new AsyncCallback<Double>() {		
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
			}
			
			@Override
			public void onSuccess(Double result) {
				VerticalPanel vp = new VerticalPanel();
			    vp.add(new Label("The highest bid placed: "+result));
			    addChart(vp, 4);
			}
		};
		myService.getHighestBid(s,callback);
	}
	
	private void addChart(Widget w, int i) {
		switch(i) {
		case 1:
			chartPlace1.clear();
			chartPlace1.add(w);
			break;
		case 2:
			chartPlace2.clear();
			chartPlace2.add(w);
			break;
		case 3:
			chartPlace3.clear();
			chartPlace3.add(w);
			break;
		case 4:
			chartPlace4.clear();
			chartPlace4.add(w);
			break;
		}
	}
}
