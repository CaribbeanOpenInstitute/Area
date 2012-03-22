/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.chartdemo.demo.chart;

import java.util.ArrayList;
import java.util.List;

import jm.org.data.area.AreaData;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

import static jm.org.data.area.AreaConstants.WORLD_SEARCH;
import static org.achartengine.chartdemo.demo.chart.ChartConstants.*;
/**
 * Average temperature demo chart.
 */
public class AreaChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Average temperature";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature in 4 Greek islands (line chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public GraphicalView execute(Context context, String indicator, String[] countries) {
	AreaData dataService = new AreaData(context);
	dataService.genericSearch(WORLD_SEARCH, "AG.LND.AGRI.ZS", new String[]{"Jamaica", "Kenya","Barbados"});
	double[][] data;
	int ind_id, y_max = 0, pos1, pos2;
	String indicator_name, x_axis;
	
	ind_id = dataService.getIndicatorID(indicator);
	indicator_name = dataService.getIndicatorName(indicator);
	
	pos1 = indicator_name.indexOf("(");
	pos2 = indicator_name.indexOf(")");
	x_axis = indicator_name.substring(pos1, pos2);
    //String[] titles = new String[] { "Jamaica", "Barbadoes" };
    List<double[]> x 	  = new ArrayList<double[]>();
    List<double[]> values = new ArrayList<double[]>();
    int[] colors = new int[countries.length];
    PointStyle[] styles = new PointStyle[countries.length];
    String test = "", test1 = "";
    for (int i = 0; i < countries.length; i++) {
    	data = dataService.getIndicatorList(ind_id, countries[i], 1);
    	Log.e("Charts","data length"+ data[0].length);
    	for (int a = 0; a < data[0].length; a++){
    		test = test + ", " + data[0][a];
    		test1 = test1 + ", " + data[1][a];
    	}
    	Log.e("Charts","data dates - "+ test);
    	Log.e("Charts","data values- "+ test1);
    	x.add(data[0]);
    	values.add(data[1]);
    	y_max = getMax(data[1]);
    	colors[i] = COLOURS[i];
    	styles[i] = STYLEZ[0];
    }
    
    y_max = y_max * 2;
   
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    
    setChartSettings(renderer, indicator_name, "Year", x_axis, 1990, 2012, 0, y_max,
        Color.LTGRAY, Color.LTGRAY);
    renderer.setXLabels(13);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.LEFT);
    renderer.setBackgroundColor(Color.LTGRAY);
    renderer.setApplyBackgroundColor(true);
    renderer.setZoomButtonsVisible(true);
    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
    renderer.setBackgroundColor(Color.WHITE);
    GraphicalView intent = ChartFactory.getLineChartView(context, buildDataset(countries, x, values),
        renderer);
    
    return intent;
  }

  private int getMax(double[] array){
	double max = 0;
	
	for(int n = 0; n < array.length; n++){
		if (max <= array[n]){
			max = array[n];
		}
	}
	
	return (int) max;
  }
}
