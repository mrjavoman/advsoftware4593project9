package com.cybertrons.orfa;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph{

	public Intent getIntent(Context context) {
		
		
		TimeSeries series = new TimeSeries("Words Correct"); 
		for( int i = 0; i < StatsActivity.correctList.size(); i++)
		{
			series.add(i+1, StatsActivity.correctList.get(i));
		}
		
		
		TimeSeries series2 = new TimeSeries("Words Incorrect"); 
		for( int i = 0; i < StatsActivity.incorrectList.size(); i++)
		{
			series2.add(i+1, StatsActivity.incorrectList.get(i));
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(series2);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		XYSeriesRenderer renderer2 = new XYSeriesRenderer(); // This will be used to customize line 2
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		
		// Customization time for line 1!
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		// Customization time for line 2!
		renderer2.setColor(Color.YELLOW);
		renderer2.setPointStyle(PointStyle.DIAMOND);
		renderer2.setFillPoints(true);
		
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Student Progress");
		return intent;
		
	}

}